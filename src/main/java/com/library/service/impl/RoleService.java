package com.library.service.impl;

import com.library.common.Const;
import com.library.common.exception.ConflictException;
import com.library.pojo.Role;
import com.library.dao.RoleMapper;
import com.library.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 0045M
 * @since 2020-06-23
 */
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IRoleService {


    @Override
    public void checkRole(String role) {
        String[] roles = {Const.USER, Const.LIBMANAGER, Const.SYSMANAGER};
        for (String r : roles) {
            if (r.equals(role))
                return;
        }
        throw new ConflictException("用户角色分配异常！");
    }
}
