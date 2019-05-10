package com.pinyougou.sellergoods.api.pojo;

import com.pinyougou.web.common.annotation.Valid;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 品牌,对应数据库tb_brand表
 *
 * @author 邱长海
 */
@Valid
public class Brand implements Serializable {
    @NotBlank
    private Long id = 0L;
    /**
     * 品牌名
     */
    @NotBlank
    private String name;
    /**
     * 首字母
     */
    private String firstChar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }
}
