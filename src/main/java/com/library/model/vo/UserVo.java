package com.library.model.vo;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.library.pojo.LibManager;
import com.library.pojo.SysManager;
import com.library.pojo.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
     * @Author MRH0045
     * @Description
     * @Date 15:26 2020/6/25
     * @Param
     * @return
     **/
@Data
public class UserVo {

    private static final String DEFAULT_AVATAR = "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";

    private static final long serialVersionUID = 1L;

    private Integer id;

    private List<String> role;

    private String name;

    private Integer sex;

    private String title;

    private String studentNumber;

    private String password;

    private String headPortrait;

    private String phone;

    private String username;
    /**
     * 已借图书数量
     */
    private Integer brrowedCount;

    /**
     * 生效时间
     */
    private LocalDateTime effDate;

    /**
     * 失效时间
     */
    private LocalDateTime expDete;

    /**
     * 用户详情
     */
    private String details;

//    private LocalDate birth;
//
//    private String graduateSchool;
//
//    private String graduateMajor;



    /**
     * 正常-0
     */
    private Integer status;

    private String token;

//    private LocalDateTime loginTime;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    public static UserVo convert(User user, String token) {
        UserVo userVo = convert(user);
        userVo.setToken(token);
        return userVo;
    }

    public static UserVo convert(SysManager sysManager, String token) {
        UserVo userVo = convert(sysManager);
        userVo.setToken(token);
        return userVo;
    }

    public static UserVo convert(LibManager libManager, String token) {
        UserVo userVo = convert(libManager);
        userVo.setToken(token);
        return userVo;
    }

    public static UserVo convert(User User) {
        UserVo userVo = new UserVo();
        userVo.id = User.getId();
        userVo.name = User.getName();
        userVo.sex = User.getSex();
        userVo.sex = User.getSex();
        userVo.brrowedCount = User.getBrrowedCount();
        userVo.details = User.getDetails();
        userVo.expDete = User.getExpDete();
        userVo.effDate = User.getEffDate();
        userVo.headPortrait = User.getHeadPortrait();
        userVo.studentNumber = User.getStudentNumber();
        userVo.phone = User.getPhone();
   //     userVo.avatar = StringUtils.isNotEmpty(student.getAvatar()) ? student.getAvatar() : DEFAULT_AVATAR;
        userVo.status = 0;
        userVo.updateTime = User.getUpdateTime();
        userVo.createTime = User.getCreateTime();
        return userVo;
    }

    public static UserVo convert(SysManager SysManager) {
        UserVo userVo = new UserVo();
        userVo.id = SysManager.getId();
        userVo.username = SysManager.getUsername();
        userVo.phone = SysManager.getPhone();
        userVo.details = SysManager.getDetails();
        userVo.sex = SysManager.getSex();
        userVo.status = 0;
        userVo.updateTime = SysManager.getUpdateTime();
        userVo.createTime = SysManager.getCreateTime();
        return userVo;
    }

    public static UserVo convert(LibManager libManager) {
        UserVo userVo = new UserVo();
        userVo.id = libManager.getId();
        userVo.username = libManager.getUsername();
        userVo.phone = libManager.getPhone();
        userVo.details = libManager.getDetails();
        userVo.sex = libManager.getSex();
        userVo.status = 0;
        userVo.updateTime = libManager.getUpdateTime();
        userVo.createTime = libManager.getCreateTime();
        return userVo;
    }
}
