package com.leyou.item.pojo;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: SpecParam
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/5  0:10
 */
@Table(name = "tb_spec_param")
@Data
public class SpecParam {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long cid;

    private Long groupId;

    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;
}
