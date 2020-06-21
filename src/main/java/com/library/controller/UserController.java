package com.library.controller;


import com.library.Form.LoginForm;
import com.library.common.ServerResponse;
import com.library.pojo.User;
import com.library.service.IUserService;
import com.library.service.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

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

    @Autowired
    IUserService userService;

    /**
     *
     * @param loginForm
     * @return
     */

    @ApiOperation("用户登录")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "query", dataType = "String", example = "1"),
                    @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query", dataType = "String", example = "1")}
    )
    @PostMapping("/login")
    public ServerResponse userLogin(@RequestBody LoginForm loginForm){
        return userService.login(loginForm.username,loginForm.password);
    }

    @ApiOperation("用户登出")
    @GetMapping("/logout")
    public ServerResponse managerLogin()
    {
        return userService.logout();
    }

}

