package com.library.service;

import com.library.common.ServerResponse;
import com.library.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
public interface IUserService extends IService<User> {

    User queryById(int id);

    /**
     * @Author: 0045M
     * @Description:用户登录
     * @Param
     * @return
     * @Date: 18:58 2020/5/29
     */
    ServerResponse login(String username,String password);

    /**
     * @Author: 0045M
     * @Description:用户登出
     * @Param
     * @return
     * @Date: 18:58 2020/5/29
     */
    ServerResponse logout();


   User getByCode(String code);

    boolean updatePassword(String code, String oldPassword, String newPassword);

}
