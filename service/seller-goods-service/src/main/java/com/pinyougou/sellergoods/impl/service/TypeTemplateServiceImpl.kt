package com.pinyougou.sellergoods.impl.service

import com.alibaba.dubbo.common.utils.StringUtils
import com.alibaba.dubbo.config.annotation.Service
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import com.pinyougou.sellergoods.api.interfaces.TypeTemplateService
import com.pinyougou.sellergoods.api.pojo.TypeTemplate
import com.pinyougou.sellergoods.impl.dao.TypeTemplateMapper
import com.pinyougou.web.common.exception.ResponseException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional

/**
 * @author 邱长海
 */
@Service(version = "1.0")
@Transactional(rollbackForClassName = ["java.lang.Exception"])
open class TypeTemplateServiceImpl : TypeTemplateService {

    @Autowired
    private lateinit var typeTemplateMapper: TypeTemplateMapper

    override fun addTypeTemplate(temp: TypeTemplate): TypeTemplate {
        if (typeTemplateMapper.insert(temp) <= 0) {
            throw ResponseException("添加类型模板失败")
        }
        return temp
    }

    override fun updateTypeTemplate(temp: TypeTemplate): TypeTemplate {
        if (typeTemplateMapper.updateById(temp) <= 0) {
            throw ResponseException("更新类型模板失败")
        }
        return temp
    }

    override fun getTypeTemplatesByPageNName(name: String?, pageNum: Int, pageSize: Int): PageInfo<TypeTemplate> {
        PageHelper.startPage<TypeTemplate>(pageNum, pageSize)
        return if (StringUtils.isBlank(name)) {
            PageInfo.of(typeTemplateMapper.selectAll())
        } else {
            PageInfo.of(typeTemplateMapper.selectByName("%$name%"))
        }
    }

    override fun deleteByIds(ids: Array<Long>): List<Long> {
        val deleteIds = mutableListOf<Long>()
        for (id in ids) {
            if (typeTemplateMapper.deleteById(id) > 0) {
                deleteIds.add(id)
            }
        }
        if (deleteIds.isEmpty()) {
            throw ResponseException("删除类型模板失败")
        }
        return deleteIds
    }
}
