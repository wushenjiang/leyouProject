package com.leyou.order.utils;

import com.github.wxpay.sdk.WXPay;
import static com.github.wxpay.sdk.WXPayConstants.*;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.order.config.PayConfig;
import com.leyou.order.enums.OrderStatusEnum;
import com.leyou.order.enums.PayState;
import com.leyou.order.mapper.OrderMapper;
import com.leyou.order.mapper.OrderStatusMapper;
import com.leyou.order.pojo.Order;
import com.leyou.order.pojo.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: PayHelper
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/17  19:54
 */
@Component
@Slf4j
public class PayHelper {

    @Autowired
    private WXPay wxPay;

    @Autowired
    private PayConfig config;
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusMapper statusMapper;
    public String createOrder(Long orderId,Long totalPay,String desc){
        try {
            Map<String, String> data = new HashMap<>();
            // 商品描述
            data.put("body", desc);
            // 订单号
            data.put("out_trade_no", orderId.toString());
            //金额，单位是分
            data.put("total_fee", totalPay.toString());
            //调用微信支付的终端IP（乐优商城的IP）
            data.put("spbill_create_ip", "127.0.0.1");
            //回调地址，付款成功后的接口
            data.put("notify_url",config.getNotifyUrl());
            // 交易类型为扫码支付
            data.put("trade_type", "NATIVE");
            // 利用wxPay工具，完成下单
            Map<String, String> result = wxPay.unifiedOrder(data);
            // 判断通信和业务标识
            isSuccess(result);
            // 下单成功,获取支付链接
            String url = result.get("code_url");
            return url;
        } catch (Exception e) {
            log.error("创建预交易订单异常", e);
            return null;
        }
    }

    public void isSuccess(Map<String, String> result) {
        // 判断通信标识
        String returnCode = result.get("return_code");
        if(FAIL.equals(returnCode)){
            // 通信失败
            log.error("[微信下单] 微信下单通信失败，失败原因:{}",result.get("return_msg"));
            throw new LyException(ExceptionEnum.WX_PAY_ORDER_FAIL);
        }
        // 判断业务标识
        String resultCode = result.get("result_code");
        if(FAIL.equals(resultCode)){
            // 通信失败
            log.error("[微信下单] 微信下单业务失败，错误码:{},错误原因:{}",result.get("err_code"),result.get("err_code_des"));
            throw new LyException(ExceptionEnum.WX_PAY_ORDER_FAIL);
        }
    }

    public void isValidSign(Map<String, String> data) {
        // 重新生成签名
        try {
            String sign1 = WXPayUtil.generateSignature(data, config.getKey(), SignType.HMACSHA256);
            String sign2 = WXPayUtil.generateSignature(data, config.getKey(), SignType.MD5);
            // 与传来的签名进行比较
            String sign = data.get("sign");
            if (!StringUtils.equals(sign,sign1) && StringUtils.equals(sign,sign2)) {
                // 签名有误,抛出异常
                throw new LyException(ExceptionEnum.INVALID_SIGN_ERROR);
            }
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.INVALID_SIGN_ERROR);
        }



    }

    public PayState queryPayState(Long orderId) {

        try {
            // 组织请求参数
            Map<String, String> request = new HashMap<>();
            // 订单号
            request.put("out_trade_no", orderId.toString());
            // 查询状态
            Map<String, String> result = wxPay.orderQuery(request);
            // 校验状态
            isSuccess(result);
            // 校验签名
            isValidSign(result);

            // 校验金额
            String totalFeeStr = result.get("total_fee");
            String tradeNo = result.get("out_trade_no");
            if(StringUtils.isEmpty(totalFeeStr) || StringUtils.isEmpty(tradeNo)){
                throw new LyException(ExceptionEnum.INVALID_ORDER_PARAM);
            }
            // 3.1获取结果中的金额
            Long totalFee = Long.valueOf(totalFeeStr);
            // 3.2获取订单金额
            Order order = orderMapper.selectByPrimaryKey(orderId);
            if(totalFee != /*order.getActualPay()*/ 1){
                // 金额不符
                throw new LyException(ExceptionEnum.INVALID_ORDER_PARAM);
            }
            String state = result.get("trade_state");
            if(SUCCESS.equals(state)){
                // 支付成功
                // 4.修改订单状态
                OrderStatus status = new OrderStatus();
                status.setStatus(OrderStatusEnum.PAYED.value());
                status.setOrderId(orderId);
                status.setPaymentTime(new Date());
                int count = statusMapper.updateByPrimaryKeySelective(status);
                if (count != 1) {
                    throw new LyException(ExceptionEnum.UPDATE_ORDER_STATUS_ERROR);
                }
                // 返回成功
                return PayState.SUCCESS;
            }
            if("NOTPAY".equals(state) || "USERPAYING".equals(state)){
                return PayState.NOT_PAY;
            }
            return PayState.FAIL;

        } catch (Exception e) {
            return PayState.NOT_PAY;
        }
    }
}
