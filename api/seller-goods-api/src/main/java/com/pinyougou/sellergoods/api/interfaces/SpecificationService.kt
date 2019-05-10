package com.pinyougou.sellergoods.api.interfaces

import com.github.pagehelper.PageInfo
import com.pinyougou.sellergoods.api.pojo.vo.SpecificationVo

/**
 * @author 邱长海
 */
interface SpecificationService {
    fun addSpecification(spec: SpecificationVo): SpecificationVo

    fun getSpecificationsByNameNPage(name: String?, pageNum: Int, pageSize: Int): PageInfo<SpecificationVo>

    fun getSpecificationsByNameNPageWithoutChild(name: String?, pageNum: Int, pageSize: Int): PageInfo<SpecificationVo>

    fun updateById(spec: SpecificationVo): SpecificationVo

    fun deleteByIds(ids: Array<Long>): List<Long>
}