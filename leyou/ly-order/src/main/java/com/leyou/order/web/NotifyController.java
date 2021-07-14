package com.leyou.order.web;

import com.leyou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: NotifyController
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/17  20:37
 */
@RestController
@RequestMapping("notify")
@Slf4j
public class NotifyController {
    @Autowired
    private OrderService orderService;
    /**
     * 微信支付成功回调
     * @param result
     * @return
     */
    @PostMapping(value = "pay",produces = MediaType.APPLICATION_XML_VALUE)
    public Map<String,String> hello(@RequestBody Map<String,String> result){
        // 处理回调
        orderService.handleNotify(result);
        log.info("[支付回调] 接收微信支付回调，结果:{}",result);
        // 返回成功
        Map<String,String> msg = new HashMap<>();
        msg.put("return_code","SUCCESS");
        msg.put("return_msg","OK");
        return msg;
    }
}
