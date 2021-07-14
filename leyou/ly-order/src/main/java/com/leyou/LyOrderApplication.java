package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: LyOrderApplication
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/16  21:38
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.leyou.order.mapper")
public class LyOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyOrderApplication.class,args);
    }
}
