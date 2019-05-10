package com.pinyougou.sellergoods.api.interfaces

import com.github.pagehelper.PageInfo
import com.pinyougou.sellergoods.api.pojo.TypeTemplate

/**
 * @author 邱长海
 */
interface TypeTemplateService {
    fun addTypeTemplate(temp: TypeTemplate): TypeTemplate

    fun updateTypeTemplate(temp: TypeTemplate): TypeTemplate

    fun getTypeTemplatesByPageNName(name: String?, pageNum: Int, pageSize: Int): PageInfo<TypeTemplate>

    fun deleteByIds(ids: Array<Long>): List<Long>
}