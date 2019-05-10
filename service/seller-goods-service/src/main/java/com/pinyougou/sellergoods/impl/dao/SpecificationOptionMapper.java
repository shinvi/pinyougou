package com.pinyougou.sellergoods.impl.dao;

import com.pinyougou.sellergoods.api.pojo.SpecificationOption;
import org.apache.ibatis.annotations.*;

/**
 * @author 邱长海
 */
@Mapper
public interface SpecificationOptionMapper {
    int countById(Long id);

    SpecificationOption selectByOptionName(@Param("optionName") String optionName);

    SpecificationOption selectById(Long id);

    int insert(SpecificationOption option);


    @Insert("insert into tb_specification_specoption(sid,oid,orders) values(#{sid},#{oid},#{orders})")
    int insertSpecOption(@Param("sid") Long sid, @Param("oid") Long oid, @Param("orders") Integer orders);

    @Delete("delete from tb_specification_specoption where sid = #{sid}")
    int deleteBySid(Long sid);

    @Select("select count(1) from tb_specification_specoption where sid = #{sid}")
    int countBySid(Long sid);
}
