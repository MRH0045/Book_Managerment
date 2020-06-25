package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.library.common.exception.ConflictException;
import com.library.pojo.LibManager;
import com.library.dao.LibManagerMapper;
import com.library.pojo.User;
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

    @Override
    public LibManager getByCode(String code) {
        LambdaQueryWrapper<LibManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LibManager::getUsername, code);       // 通过学号查询
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean updatePassword(String code, String oldPassword, String newPassword) {
        LambdaQueryWrapper<LibManager> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(LibManager::getUsername,code).eq(LibManager::getPassword,oldPassword);
        LibManager libManager = this.baseMapper.selectOne(queryWrapper);
        if(libManager != null){
            libManager.setPassword(newPassword);
            LambdaQueryWrapper<LibManager> updateWrapper = new LambdaQueryWrapper<>();
            updateWrapper.eq(LibManager::getUsername,code);
            this.baseMapper.update(libManager,updateWrapper);
            return true;

        }
        throw new ConflictException("密码错误，修改失败");
    }
}
