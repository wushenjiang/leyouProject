package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName: SpecificationController
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/4  23:57
 */
@RestController
@RequestMapping("spec")
@Slf4j
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询规格组
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }

    /**
     * 查询参数集合
     * @param gid 组id
     * @param cid 分类id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching
    ) {
        return ResponseEntity.ok(specificationService.queryParamList(gid,cid,searching));
    }

    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(SpecGroup specGroup) {
        specificationService.saveGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("group")
    public ResponseEntity<Void> updateGroup(SpecGroup specGroup) {
        specificationService.updateGroup(specGroup);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id) {
        specificationService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("param")
    public ResponseEntity<Void> saveParam(SpecParam specParam) {
        specificationService.saveParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("param")
    public ResponseEntity<Void> updateParam(SpecParam specParam) {
        specificationService.updateParam(specParam);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable("id") Long id) {
        specificationService.deleteParam(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 根据分类查询规格组及组内参数
     * @param cid
     * @return
     */
    @GetMapping("group")
    ResponseEntity<List<SpecGroup>> queryListByCid(@RequestParam("cid") Long cid){
        return ResponseEntity.ok(specificationService.queryListByCid(cid));
    }
}

