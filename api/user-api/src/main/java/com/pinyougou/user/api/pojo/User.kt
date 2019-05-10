package com.pinyougou.user.api.pojo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.pinyougou.web.common.annotation.Email
import com.pinyougou.web.common.annotation.Phone
import com.pinyougou.web.common.annotation.Valid
import java.io.Serializable
import java.math.BigDecimal
import java.util.*

/**
 * @author 邱长海
 */
@Valid
data class User(var id: Long?, var username: String?, @field:JsonIgnore var password: String?, @field:Phone var phone: String?, @field:Email var email: String?,
                var sourceType: Int?, var nickName: String?, var name: String?, var status: Int?, var headPic: String?,
                var qq: String?, var accountBalance: BigDecimal?, var isMobileCheck: Int?, var isEmailCheck: Int?, var sex: Int?,
                var userLevel: Int?, var points: Int?, var experienceValue: Int?, var birthday: Date?, var lastLoginTime: Date?) : Serializable {
    constructor() : this(null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null,
            null, null, null, null, null)
}