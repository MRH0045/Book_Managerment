package com.library.service;

import com.library.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 0045M
 * @since 2020-06-23
 */
public interface IRoleService extends IService<Role> {

    void checkRole(String role);

}
