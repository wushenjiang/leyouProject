package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName: GoodsClient
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:28
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
