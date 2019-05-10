package com.pinyougou.user.impl.dao;

import com.pinyougou.user.api.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 邱长海
 */
@Mapper
public interface UserMapper {
    User selectByUsername(@Param("username") String username);

    User selectByUsernameOrPhone(@Param("usernameOrPhone") String usernameOrPhone);

    int countByUsername(@Param("username") String username);

    int insert(User user);
}
