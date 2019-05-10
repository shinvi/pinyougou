package com.pinyougou.sellergoods.impl.dao;

import com.pinyougou.sellergoods.api.pojo.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邱长海
 */
@Mapper
public interface BrandMapper {

    int countById(Long id);

    List<Brand> selectAll();

    List<Brand> selectByName(@Param("name") String name);

    int insert(Brand brand);

    int updateById(Brand brand);

    int deleteById(Long id);
}
