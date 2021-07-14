package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: LyItemApplication
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/1  23:48
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.leyou.item.mapper")
public class LyItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LyItemApplication.class,args);
    }
}
