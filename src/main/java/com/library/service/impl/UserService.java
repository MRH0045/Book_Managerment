package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.common.Const;
import com.library.common.ServerResponse;
import com.library.common.exception.ConflictException;
import com.library.pojo.User;
import com.library.dao.UserMapper;
import com.library.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;


    @Override
    public User queryById(int id) {
        return userMapper.selectById(id);
    }


    @Override
    public ServerResponse login(String username, String password) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_number",username);
        User user = userMapper.selectOne(queryWrapper);
        if(user != null && user.getPassword().equals(password))
        {
            HttpSession session = ServletUtils.getSession();
            session.setAttribute("role", Const.USER);
            session.setAttribute("userId",user.getId());

            return ServerResponse.createBySuccessMessage("登录成功！");
        }
        return ServerResponse.createByErrorMessage("用户名或密码错误！");
    }

    @Override
    public ServerResponse logout() {

        HttpSession session = ServletUtils.getSession();
        if(Const.USER.equals(session.getAttribute("role"))){
            session.invalidate();
        }
        return ServerResponse.createBySuccessMessage("注销成功！");
    }

    @Override
    public User getByCode(String code) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getStudentNumber, code);       // 通过学号查询
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean updatePassword(String code, String oldPassword, String newPassword) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getStudentNumber,code).eq(User::getPassword,oldPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        if(user != null){
            user.setPassword(newPassword);
            LambdaQueryWrapper<User> updateWrapper = new LambdaQueryWrapper<>();
            updateWrapper.eq(User::getStudentNumber,code);
            this.baseMapper.update(user,updateWrapper);
            return true;

        }
        throw new ConflictException("密码错误，修改失败");
    }


}
