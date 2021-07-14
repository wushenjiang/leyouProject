package com.leyou.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @ClassName: IdWorkerProperties
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/17  16:45
 */
@Data
@ConfigurationProperties(prefix = "ly.worker")
public class IdWorkerProperties {
    // 当前机器id
    private long workerId;
    // 序列号
    private long dataCenterId;
}
