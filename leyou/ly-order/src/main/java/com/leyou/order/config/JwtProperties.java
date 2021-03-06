package com.leyou.order.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@Slf4j
@Data
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {


    private String pubKeyPath;// 公钥

    private PublicKey publicKey; // 公钥

    private String  cookieName;


    /**
     * @PostContruct：在构造方法执行之后执行该方法
     */
    @PostConstruct
    public void init() throws Exception {
            // 获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
    }
    
    // getter setter ...
}