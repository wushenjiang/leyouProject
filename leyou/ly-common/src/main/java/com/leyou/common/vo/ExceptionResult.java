package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * @ClassName: ExceptionResult
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/2  0:46
 */
@Data
public class ExceptionResult {

    private Integer status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
