package com.pinyougou.manager.web.controller.v1;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.util.ObjectUtils;
import com.pinyougou.user.api.interfaces.SmsCodeService;
import com.pinyougou.user.api.interfaces.UserService;
import com.pinyougou.user.api.pojo.User;
import com.pinyougou.web.common.annotation.Phone;
import com.pinyougou.web.common.entity.ServerResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 邱长海
 */
@RestController
@RequestMapping(path = "v1/user")
public class UserController {

    @Reference(version = "1.0")
    private UserService userService;

    @Reference(version = "1.0")
    private SmsCodeService smsCodeService;

    @PostMapping
    public ServerResponse<User> register(@RequestBody User user) {
        if (StringUtils.isBlank(user.getUsername())) {
            return ServerResponse.error("用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return ServerResponse.error("密码不能为空");
        }
        if (!ObjectUtils.in(user.getSourceType(), 1, 2, 3, 4, 5)) {
            return ServerResponse.error("不支持的用户来源");
        }
        if (!ObjectUtils.in(user.getSex(), 1, 2)) {
            return ServerResponse.error("不支持的性别");
        }
        return ServerResponse.success(userService.register(user));
    }

    @PostMapping(path = "/captcha")
    public ServerResponse sendSmsCode(@NotBlank @Phone String phone) {
        smsCodeService.sendSmsCode(phone);
        return ServerResponse.success();
    }
}
