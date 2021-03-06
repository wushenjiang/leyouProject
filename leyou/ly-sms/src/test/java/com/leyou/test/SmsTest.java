package com.leyou.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SmsTest
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/15  13:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public  void testSend() throws InterruptedException {
        Map<String,String> msg = new HashMap<>();
        msg.put("phone","15383119674");
        msg.put("code","2254");
        amqpTemplate.convertAndSend("ly.sms.exchange","sms.verify.code",msg);

        Thread.sleep(10000L);
    }
}
