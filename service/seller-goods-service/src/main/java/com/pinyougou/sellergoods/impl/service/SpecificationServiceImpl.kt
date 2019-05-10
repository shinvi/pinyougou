package com.pinyougou.sellergoods.impl.service

import com.alibaba.dubbo.common.utils.StringUtils
import com.alibaba.dubbo.config.annotation.Service
import com.github.pagehelper.Page
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.pinyougou.sellergoods.api.interfaces.SpecificationService
import com.pinyougou.sellergoods.api.pojo.vo.SpecificationVo
import com.pinyougou.sellergoods.impl.dao.SpecificationMapper
import com.pinyougou.sellergoods.impl.dao.SpecificationOptionMapper
import com.pinyougou.web.common.exception.ResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

/**
 * @author 邱长海
 */
@Service(version = "1.0")
@Transactional(rollbackForClassName = ["java.lang.Exception"])
open class SpecificationServiceImpl : SpecificationService {

    @Autowired
    private lateinit var specMapper: SpecificationMapper

    @Autowired
    private lateinit var specOptionMapper: SpecificationOptionMapper

    override fun addSpecification(spec: SpecificationVo): SpecificationVo {
        if (specMapper.insert(spec) <= 0) {
            throw ResponseException("添加规格失败")
        }
        updateSpecOption(spec)
        return spec
    }

    override fun getSpecificationsByNameNPageWithoutChild(name: String?, pageNum: Int, pageSize: Int): PageInfo<SpecificationVo> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return if (StringUtils.isBlank(name)) {
            PageInfo.of(specMapper.selectAllWithoutChild())
        } else {
            PageInfo.of(specMapper.selectByName("%$name%"))
        }
    }

    override fun getSpecificationsByNameNPage(name: String?, pageNum: Int, pageSize: Int): PageInfo<SpecificationVo> {
        val page = Page<Any>(pageNum, pageSize)
        page.total = specMapper.countAll()
        if (page.total <= 0) {
            return PageInfo()
        }
        val pageInfo = if (StringUtils.isBlank(name)) {
            PageInfo.of(specMapper.selectByPage(page.startRow, page.pageSize))
        } else {
            page.total = specMapper.countByName("%$name%")
            PageInfo.of(specMapper.selectByNameNPage("%$name%", page.startRow, page.pageSize))
        }
        pageInfo.total = page.total
        pageInfo.pageNum = page.pageNum
        pageInfo.pageSize = page.pageSize
        pageInfo.size = pageInfo.list.size
        pageInfo.pages = Math.floor(pageInfo.total.toDouble() / pageInfo.pageSize).toInt()
        pageInfo.isHasNextPage = pageInfo.pageNum < pageInfo.pages
        return pageInfo
    }

    override fun updateById(spec: SpecificationVo): SpecificationVo {
        if (specMapper.updateById(spec) <= 0) {
            throw ResponseException("更新规格失败")
        }
        updateSpecOption(spec)
        return spec
    }

    override fun deleteByIds(ids: Array<Long>): List<Long> {
        val deleteIds = mutableListOf<Long>()
        for (id in ids) {
            specOptionMapper.deleteBySid(id)
            if (specMapper.deleteById(id) > 0) {
                deleteIds.add(id)
            }
        }
        if (deleteIds.isEmpty()) {
            throw ResponseException("删除规格失败")
        }
        return deleteIds
    }

    /**
     * 更新规格参数表和规格与参数的一对多中间表
     */
    private fun updateSpecOption(spec: SpecificationVo) {
        val count = specOptionMapper.countBySid(spec.id)
        if (specOptionMapper.deleteBySid(spec.id) != count) {
            throw ResponseException("添加规格失败")
        }
        for (option in spec.options) {
            var selectOption = specOptionMapper.selectByOptionName(option.optionName)
            if (selectOption == null) {
                if (specOptionMapper.insert(option) <= 0) {
                    throw ResponseException("添加规格失败")
                }
                selectOption = option
            }
            if (specOptionMapper.insertSpecOption(spec.id, selectOption.id, option.orders) < 0) {
                throw ResponseException("添加规格失败")
            }
        }
    }
}