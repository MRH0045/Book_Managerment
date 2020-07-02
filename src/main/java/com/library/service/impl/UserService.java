package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.common.ServerResponse;
import com.library.common.exception.ConflictException;
import com.library.model.Form.queryUserForm;
import com.library.pojo.User;
import com.library.dao.UserMapper;
import com.library.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


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

    @Autowired(required=false)
    UserMapper userMapper;


    @Override
    public User queryById(int id) {
        return userMapper.selectById(id);
    }



    /**
         * @Author MRH0045
         * @Description 通过学号查询用户
         * @Date 11:04 2020/6/27
         * @Param [code]
         * @return com.library.pojo.User
         **/
    @Override
    public User getByCode(String code) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getStudentNumber, code);       // 通过学号查询
        return baseMapper.selectOne(queryWrapper);
    }

    /**
         * @Author MRH0045
         * @Description 通过用户id删除用户
         * @Date 11:04 2020/6/27
         * @Param [id]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse removeUser(Integer id) {
        return userMapper.deleteById(id)>0?
                ServerResponse.createBySuccessMessage("删除成功！"):
                ServerResponse.createByErrorMessage("删除失败！");
    }


    /**
         * @Author MRH0045
         * @Description 根据表单的分页查询满足条件的用户
         * @Date 11:34 2020/6/27
         * @Param [queryUserForm]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse queryByForm(queryUserForm queryUserForm) {
        Page page = new Page<>(queryUserForm.getPage(),queryUserForm.getPageSize());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(queryUserForm.getKeyWords()!=null&&queryUserForm.getKeyWords()!="","name",queryUserForm.getKeyWords()).or()
                .like(queryUserForm.getKeyWords()!=null&&queryUserForm.getKeyWords()!="","student_number",queryUserForm.getKeyWords()).or()
                .like(queryUserForm.getKeyWords()!=null&&queryUserForm.getKeyWords()!="","phone",queryUserForm.getKeyWords()).or()
                .like(queryUserForm.getKeyWords()!=null&&queryUserForm.getKeyWords()!="","details",queryUserForm.getKeyWords())
                .eq(queryUserForm.getStatus()!=null,"status",queryUserForm.getStatus())
            .orderByDesc(queryUserForm.getSortType()==0,"create_time")
               .orderByDesc(queryUserForm.getSortType()==1,"update_time");
        IPage iPage = userMapper.selectPage(page,userQueryWrapper);
        HashMap<String,Object> list = new HashMap<>();
        list.put("data",iPage.getRecords());
        list.put("CurrentPage",(int)iPage.getCurrent());
        list.put("total",(int)iPage.getTotal());
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse updateUser(User user) {
        return userMapper.updateById(user)>0?
                ServerResponse.createBySuccessMessage("修改成功"):
                ServerResponse.createByErrorMessage("修改失败");
    }

    /**
         * @Author MRH0045
         * @Description 根据旧密码修改密码
         * @Date 11:03 2020/6/27
         * @Param [code, oldPassword, newPassword]
         * @return boolean
         **/
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
