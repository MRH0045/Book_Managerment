package com.library.controller;

import com.library.common.ServerResponse;
import com.library.model.Form.queryUserForm;
import com.library.service.impl.UserService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserService userService;


    @ApiOperation("根据表单分页查找满足条件的用户")
    @GetMapping()
    public ServerResponse queryByForm(queryUserForm queryUserForm){
        return userService.queryByForm(queryUserForm);
    }

}

