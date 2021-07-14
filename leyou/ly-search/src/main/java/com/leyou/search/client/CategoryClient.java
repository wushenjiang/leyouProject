package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import com.leyou.item.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName: CategoryClient
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:23
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {


}
