package com.leyou.order.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: GoodsClient
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/17  17:05
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
