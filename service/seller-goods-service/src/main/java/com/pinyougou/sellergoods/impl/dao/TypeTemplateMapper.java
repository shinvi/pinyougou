package com.pinyougou.sellergoods.impl.dao;

import com.pinyougou.sellergoods.api.pojo.TypeTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邱长海
 */
@Mapper
public interface TypeTemplateMapper {
    int countById(Long id);

    List<TypeTemplate> selectByName(@Param("name") String name);

    List<TypeTemplate> selectAll();

    int insert(TypeTemplate template);

    int updateById(TypeTemplate template);

    int deleteById(Long id);
}
