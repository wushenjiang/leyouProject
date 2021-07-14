package com.leyou.page.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: BrandClient
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:40
 */
@FeignClient("item-service")
public interface BrandClient  extends BrandApi {
}
