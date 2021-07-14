package com.leyou.page.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: CategoryClient
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:23
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {


}
