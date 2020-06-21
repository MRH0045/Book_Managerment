package com.library.service.impl;

import com.library.pojo.BorrowLog;
import com.library.dao.BorrowLogMapper;
import com.library.service.IBorrowLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@Service
public class BorrowLogService extends ServiceImpl<BorrowLogMapper, BorrowLog> implements IBorrowLogService {

}
