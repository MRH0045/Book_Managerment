package com.library.service;

import com.library.common.ServerResponse;
import com.library.pojo.BorrowLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
public interface IBorrowLogService extends IService<BorrowLog> {

    boolean addBorrowLog(BorrowLog borrowLog);

    ServerResponse removeBorrowLog(Integer id);

    ServerResponse queryAll();

}
