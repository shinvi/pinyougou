package com.pinyougou.user.api.pojo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

/**
 * @author 邱长海
 */
class JwtUser(val user: User) : UserDetails, Serializable {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }
    //是否启用
    override fun isEnabled() = true

    override fun getUsername() = user.username

    override fun isCredentialsNonExpired() = true

    override fun getPassword() = user.password

    //账户是否未过期
    override fun isAccountNonExpired() = true

    //账户是否未被锁
    override fun isAccountNonLocked() = true
}