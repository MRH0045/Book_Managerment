package com.library.service;

import com.library.common.ServerResponse;
import com.library.model.Form.queryUserForm;
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

    User getByCode(String code);

    ServerResponse removeUser(Integer id);

    ServerResponse queryByForm(queryUserForm queryUserForm);

    ServerResponse updateUser(User user);

    boolean updatePassword(String code, String oldPassword, String newPassword);

}
