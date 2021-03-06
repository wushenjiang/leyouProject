package com.leyou.sms.mq;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtils;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @ClassName: ItemListener
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/14  21:48
 */
@Component
@EnableConfigurationProperties(SmsProperties.class)
@Slf4j
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private SmsProperties prop;

    /**
     * 发送短信验证码
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.verify.code.queue", durable = "true"),
            exchange = @Exchange(name = "ly.sms.exchange", type = ExchangeTypes.TOPIC),
            key = {"sms.verify.code"}
    ))
    public void listenInsertOrUpdate(Map<String, String> msg) {
        if (CollectionUtils.isEmpty(msg)) {
            return;
        }
        String phone = msg.remove("phone");
        if (StringUtils.isBlank(phone)) {
            return;
        }
        Gson gson = new Gson();
        // 处理消息,发送短信
        smsUtils.sendSms(phone, prop.getSignName(), prop.getVerifyCodeTemplate(), gson.toJson(msg));

    }

}
