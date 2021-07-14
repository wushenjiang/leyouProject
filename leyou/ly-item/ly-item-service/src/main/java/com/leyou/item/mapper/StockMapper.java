package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * @ClassName: StockMapper
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/6  21:09
 */
public interface StockMapper extends BaseMapper<Stock> {
    /**
     * 减少库存
     * @param id
     * @param num
     * @return
     */
    @Update("UPDATE tb_stock SET stock = stock - #{num} WHERE sku_id = #{id} AND stock >= #{num}")
    int decreaseStock(@Param("id") Long id,@Param("num") Integer num);
}
