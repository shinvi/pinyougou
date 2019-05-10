package com.pinyougou.sellergoods.api.pojo.vo

import com.pinyougou.sellergoods.api.pojo.SpecificationOption
import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @author 邱长海
 */
data class SpecificationVo(var id: Long = 0, @NotBlank var specName: String = "", var options: List<SpecificationOption> = mutableListOf()) : Serializable