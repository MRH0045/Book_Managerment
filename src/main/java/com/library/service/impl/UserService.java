package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.common.Const;
import com.library.common.ServerResponse;
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


}
