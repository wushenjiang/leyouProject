package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SpecificationService
 * @Description: TODO
 * @author: LiuGe
 * @date: 2020/7/4  23:57
 */
@Service
public class SpecificationService {
    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;
    public List<SpecGroup> queryGroupByCid(Long cid) {
        // 查询条件
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        // 查询
        List<SpecGroup> list = groupMapper.select(group);
        if(CollectionUtils.isEmpty(list)){
            // 没查到
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = paramMapper.select(param);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }

    public void saveGroup(SpecGroup specGroup) {
        int count = groupMapper.insert(specGroup);
        if(count > 1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_SAVE_ERROR);
        }
    }

    public void updateGroup(SpecGroup specGroup) {
        int count = groupMapper.updateByPrimaryKey(specGroup);
        if(count > 1){
            throw  new LyException(ExceptionEnum.SPEC_GROUP_UPDATE_ERROR);
        }
    }

    public void deleteGroup(Long id) {
        int count = groupMapper.deleteByPrimaryKey(id);
        if(count > 1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_DELETE_ERROR);
        }
    }

    public void saveParam(SpecParam specParam) {
        int count = paramMapper.insert(specParam);
        if(count > 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_SAVE_ERROR);
        }
    }

    public void updateParam(SpecParam specParam) {
        int count = paramMapper.updateByPrimaryKey(specParam);
        if(count > 1){
            throw new LyException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
        }
    }

    public void deleteParam(Long id) {
        int count = paramMapper.deleteByPrimaryKey(id);
        if (count > 1) {
            throw new LyException(ExceptionEnum.SPEC_PARAM_DELETE_ERROR);
        }
    }

    public List<SpecGroup> queryListByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);
        // 查询当前分类下的参数
        List<SpecParam> specParams = queryParamList(null, cid, null);
        // 先把规格参数变为map,map的key是规格组id,map的值是组下的所有参数
        Map<Long,List<SpecParam>> map = new HashMap<>();
        for (SpecParam param : specParams) {
            if(!map.containsKey(param.getGroupId())){
                // 组id在map中不存在,新增一个list
                map.put(param.getGroupId(),new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);
        }
        // 填充param到group中
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return specGroups;
    }
}
