package com.pinyougou.user.impl.dao;

import com.pinyougou.user.api.pojo.SmsCode;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 邱长海
 */
@Mapper
public interface SmsCodeMapper {

    int insert(SmsCode code);

    SmsCode selectByMobile(String mobile);

    int update(SmsCode code);
}
