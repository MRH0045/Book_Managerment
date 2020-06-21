package com.library.service.impl;

import com.library.pojo.LibManager;
import com.library.dao.LibManagerMapper;
import com.library.service.ILibManagerService;
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
public class LibManagerService extends ServiceImpl<LibManagerMapper, LibManager> implements ILibManagerService {

}
