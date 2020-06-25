package com.library.service;

import com.library.pojo.LibManager;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
public interface ILibManagerService extends IService<LibManager> {

    LibManager getByCode(String code);

    boolean updatePassword(String code, String oldPassword, String newPassword);

}
