package com.pinyougou.sellergoods.api.pojo;

import com.pinyougou.web.common.annotation.Valid;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 邱长海
 */
@Valid
public class TypeTemplate implements Serializable {
    private Long id;
    @NotBlank
    private String name;
    private String brandIds;
    private String specIds;
    private String customAttributeItems;

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

    public String getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(String brandIds) {
        this.brandIds = brandIds;
    }

    public String getSpecIds() {
        return specIds;
    }

    public void setSpecIds(String specIds) {
        this.specIds = specIds;
    }

    public String getCustomAttributeItems() {
        return customAttributeItems;
    }

    public void setCustomAttributeItems(String customAttributeItems) {
        this.customAttributeItems = customAttributeItems;
    }
}
