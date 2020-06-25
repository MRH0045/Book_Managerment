package com.library.controller;

import com.library.common.ServerResponse;
import com.library.service.impl.UserService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@Api("用户功能")
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @ApiOperation("用户登出")
    @GetMapping("/logout")
    public ServerResponse managerLogin()
    {
        return userService.logout();
    }

}

