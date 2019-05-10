package com.pinyougou.manager.web.controller.v1;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.pinyougou.sellergoods.api.interfaces.TypeTemplateService;
import com.pinyougou.sellergoods.api.pojo.TypeTemplate;
import com.pinyougou.web.common.entity.ServerResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 邱长海
 */
@RestController
@RequestMapping(path = "v1/typeTemplate")
public class TypeTemplateController {

    @Reference(version = "1.0")
    private TypeTemplateService templateService;

    @GetMapping
    public ServerResponse<PageInfo<TypeTemplate>> getTypeTemplates(String name,
                                                                   @RequestParam(defaultValue = "1", required = false) Integer pageNum,
                                                                   @RequestParam(defaultValue = "10", required = false) Integer pageSize) {
        return ServerResponse.success(templateService.getTypeTemplatesByPageNName(name, pageNum, pageSize));
    }

    @PostMapping
    public ServerResponse<TypeTemplate> addTypeTemplate(@RequestBody TypeTemplate template) {
        return ServerResponse.success(templateService.addTypeTemplate(template));
    }

    @PutMapping
    public ServerResponse<TypeTemplate> updateTypeTemplate(@RequestBody TypeTemplate template) {
        return ServerResponse.success(templateService.updateTypeTemplate(template));
    }

    @DeleteMapping
    public ServerResponse<List<Long>> deleteTypeTemplateByIds(Long[] ids) {
        if (ids == null || ids.length <= 0) {
            return ServerResponse.error("ids不能为空");
        }
        return ServerResponse.success(templateService.deleteByIds(ids));
    }
}
