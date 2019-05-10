package com.pinyougou.sellergoods.impl.dao;

import com.pinyougou.sellergoods.api.pojo.vo.SpecificationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author 邱长海
 */
@Mapper
public interface SpecificationMapper {
    int countById(Long id);

    long countByName(@Param("name") String name);

    long countAll();

    List<SpecificationVo> selectByPage(@Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    List<SpecificationVo> selectByNameNPage(@Param("name") String name, @Param("pageStart") Integer pageStart, @Param("pageSize") Integer pageSize);

    List<SpecificationVo> selectByName(@Param("name") String name);

    List<SpecificationVo> selectAllWithoutChild();

    int insert(SpecificationVo vo);

    int updateById(SpecificationVo vo);

    int deleteById(Long id);
}
