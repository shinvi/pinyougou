package com.pinyougou.common.util

/**
 * @author 邱长海
 */
fun Any.isIn(vararg other: Any): Boolean = ObjectUtils.`in`(this, other)