package com.pinyougou.sellergoods.api.pojo

import java.io.Serializable
import javax.validation.constraints.NotBlank

/**
 * @author 邱长海
 */
data class SpecificationOption(var id: Long = 0, @NotBlank var optionName: String = "", var orders: Int = 0) : Serializable