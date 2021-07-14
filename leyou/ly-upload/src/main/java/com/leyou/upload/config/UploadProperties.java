package com.leyou.upload.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName: UploadProperties
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/3  23:10
 */
@Data
@ConfigurationProperties(prefix = "ly.upload")
public class UploadProperties {

    private String baseUrl;
    private List<String> allowTypes;
}
