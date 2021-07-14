package com.leyou.search.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: SpecificationClient
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:40
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
