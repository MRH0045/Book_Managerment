package com.library.service.impl;

import com.library.pojo.OperLog;
import com.library.dao.OperLogMapper;
import com.library.service.IOperLogService;
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
public class OperLogService extends ServiceImpl<OperLogMapper, OperLog> implements IOperLogService {

}
