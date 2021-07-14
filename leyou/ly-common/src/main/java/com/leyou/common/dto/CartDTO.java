package com.leyou.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: CartDTO
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/16  22:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    /**
     * 商品skuId
     */
    private Long skuId;
    /**
     * 购买数量
     */
    private Integer num;
}
