package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.library.common.exception.ConflictException;
import com.library.pojo.LibManager;
import com.library.pojo.SysManager;
import com.library.dao.SysManagerMapper;
import com.library.service.ISysManagerService;
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
public class SysManagerService extends ServiceImpl<SysManagerMapper, SysManager> implements ISysManagerService {

    @Override
    public SysManager getByCode(String code) {
        LambdaQueryWrapper<SysManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysManager::getUsername, code);       // 通过学号查询
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean updatePassword(String code, String oldPassword, String newPassword) {
        LambdaQueryWrapper<SysManager> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(SysManager::getUsername,code).eq(SysManager::getPassword,oldPassword);
        SysManager sysManager = this.baseMapper.selectOne(queryWrapper);
        if(sysManager != null){
            sysManager.setPassword(newPassword);
            LambdaQueryWrapper<SysManager> updateWrapper = new LambdaQueryWrapper<>();
            updateWrapper.eq(SysManager::getUsername,code);
            this.baseMapper.update(sysManager,updateWrapper);
            return true;

        }
        throw new ConflictException("密码错误，修改失败");
    }
}
