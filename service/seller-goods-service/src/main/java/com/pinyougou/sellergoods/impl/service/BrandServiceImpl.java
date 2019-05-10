package com.pinyougou.sellergoods.impl.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.promeg.pinyinhelper.Pinyin;
import com.pinyougou.sellergoods.api.interfaces.BrandService;
import com.pinyougou.sellergoods.api.pojo.Brand;
import com.pinyougou.sellergoods.impl.dao.BrandMapper;
import com.pinyougou.web.common.exception.ResponseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 邱长海
 */
@Service(version = "1.0", interfaceClass = BrandService.class)
@Transactional(rollbackFor = Exception.class)
public class BrandServiceImpl implements BrandService {

    @Resource
    private BrandMapper brandMapper;

    @Override
    public PageInfo<Brand> getBrandsByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(brandMapper.selectAll());
    }

    @Override
    public PageInfo<Brand> getBrandsByNameNPage(String name, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isBlank(name)) {
            return new PageInfo<>(brandMapper.selectAll());
        } else {
            return new PageInfo<>(brandMapper.selectByName("%" + name + "%"));
        }
    }

    @Override
    public Brand addBrand(Brand brand) {
        brand.setFirstChar(String.valueOf(Pinyin.toPinyin(brand.getName().charAt(0)).charAt(0)));
        if (brandMapper.insert(brand) <= 0) {
            throw new ResponseException("添加品牌失败");
        }
        return brand;
    }

    @Override
    public Brand updateBrandById(Brand brand) {
        if (brandMapper.countById(brand.getId()) <= 0) {
            throw new ResponseException("id不存在");
        }
        brand.setFirstChar(String.valueOf(Pinyin.toPinyin(brand.getName().charAt(0)).charAt(0)));
        if (brandMapper.updateById(brand) <= 0) {
            throw new ResponseException("更新品牌失败");
        }
        return brand;
    }

    @Override
    public List<Long> deleteByIds(Long... ids) {
        List<Long> deletedIds = new ArrayList<>();
        for (Long id : ids) {
            if (brandMapper.deleteById(id) > 0) {
                deletedIds.add(id);
            }
        }
        if (deletedIds.isEmpty()) {
            throw new ResponseException("删除品牌失败");
        }
        return deletedIds;
    }
}
