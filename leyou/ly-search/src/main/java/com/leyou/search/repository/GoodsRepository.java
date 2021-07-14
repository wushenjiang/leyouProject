package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName: GoodsRepository
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:42
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
