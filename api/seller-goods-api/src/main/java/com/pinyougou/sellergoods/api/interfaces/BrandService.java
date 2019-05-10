package com.pinyougou.sellergoods.api.interfaces;

import com.github.pagehelper.PageInfo;
import com.pinyougou.sellergoods.api.pojo.Brand;
import com.pinyougou.web.common.exception.ResponseException;

import java.util.List;


/**
 * @author 邱长海
 */
public interface BrandService {

    PageInfo<Brand> getBrandsByPage(int pageNum, int pageSize);

    PageInfo<Brand> getBrandsByNameNPage(String name, int pageNum, int pageSize);

    Brand addBrand(Brand brand);

    Brand updateBrandById(Brand brand);

    List<Long> deleteByIds(Long... ids);
}
