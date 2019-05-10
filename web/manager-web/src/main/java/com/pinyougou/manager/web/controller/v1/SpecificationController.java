package com.pinyougou.manager.web.controller.v1;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.sellergoods.api.interfaces.SpecificationService;
import com.pinyougou.sellergoods.api.pojo.vo.SpecificationVo;
import com.pinyougou.web.common.entity.ServerResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 邱长海
 */
@RestController
@RequestMapping("v1/specification")
public class SpecificationController {
    @Reference(version = "1.0")
    private SpecificationService specService;

    @GetMapping
    public ServerResponse<PageInfo<SpecificationVo>> getSpecificationsByNameNPage(String name,
                                                                                  @RequestParam(defaultValue = "1", required = false) Integer pageNum,
                                                                                  @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                                                                  @RequestParam(defaultValue = "true", required = false) Boolean requestChild) {
        if (requestChild) {
            return ServerResponse.success(specService.getSpecificationsByNameNPage(name, pageNum, pageSize));
        } else {
            return ServerResponse.success(specService.getSpecificationsByNameNPageWithoutChild(name, pageNum, pageSize));
        }
    }


    @PostMapping
    public ServerResponse<SpecificationVo> addSpecification(@RequestBody SpecificationVo spec) {
        return ServerResponse.success(specService.addSpecification(spec));
    }

    @PutMapping
    public ServerResponse<SpecificationVo> updateSpecification(@RequestBody SpecificationVo spec) {
        return ServerResponse.success(specService.updateById(spec));
    }

    @DeleteMapping
    public ServerResponse<List<Long>> deleteSpecificationByIds(Long[] ids) {
        return ServerResponse.success(specService.deleteByIds(ids));
    }
}
