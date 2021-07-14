package com.leyou.page.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: GoodsClient
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:28
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
