package com.leyou.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName: SmsProperties
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/15  12:46
 */
@ConfigurationProperties(prefix = "ly.sms")
@Data
public class SmsProperties {
    String accessKeyId;

    String accessKeySecret;

    String signName;

    String verifyCodeTemplate;
}
