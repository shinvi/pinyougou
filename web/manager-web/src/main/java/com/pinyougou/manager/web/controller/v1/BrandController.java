package com.pinyougou.manager.web.controller.v1;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.sellergoods.api.interfaces.BrandService;
import com.pinyougou.sellergoods.api.pojo.Brand;
import com.pinyougou.web.common.entity.ServerResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


/**
 * @author 邱长海
 */
@RestController
@RequestMapping(path = "v1/brand")
public class BrandController {

    @Reference(version = "1.0")
    private BrandService brandService;

    @GetMapping
    public ServerResponse<PageInfo<Brand>> getBrandsByPage(@RequestParam(defaultValue = "1", required = false) int pageNum,
                                                           @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                           String name) {
        return ServerResponse.success(brandService.getBrandsByNameNPage(name, pageNum, pageSize));
    }

    @PostMapping
    public ServerResponse<Brand> addBrand(@RequestBody Brand brand) {
        return ServerResponse.success(brandService.addBrand(brand));
    }

    @PutMapping
    public ServerResponse<Brand> updateBrandById(@RequestBody Brand brand) {
        return ServerResponse.success(brandService.updateBrandById(brand));
    }

    @DeleteMapping
    public ServerResponse<List<Long>> deleteBrandsById(@NotBlank Long[] ids) {
        return ServerResponse.success(brandService.deleteByIds(ids));
    }
}
