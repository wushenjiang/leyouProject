package com.leyou.search.client;

import com.leyou.item.pojo.Category;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: CategoryClientTest
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/12  15:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest{
    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void testQueryCategoryByIds() {
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        Assert.assertEquals(3,categories.size());
        for (Category category : categories) {
            System.out.println("category = " + category);
        }
    }
}