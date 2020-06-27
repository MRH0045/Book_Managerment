package com.library.manage;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.common.Const;
import com.library.common.ServerResponse;
import com.library.common.authentication.JWTUtil;
import com.library.common.exception.AuthenticationException;
import com.library.common.exception.EmptyException;
import com.library.model.vo.UserVo;
import com.library.pojo.LibManager;
import com.library.pojo.Role;
import com.library.pojo.SysManager;
import com.library.pojo.User;
import com.library.service.ILibManagerService;
import com.library.service.IRoleService;
import com.library.service.ISysManagerService;
import com.library.service.IUserService;
import com.library.utils.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserManage
 * @Description TODO
 * @Author 安羽兮
 * @Date 2019/11/2516:22
 * @Version 1.0
 **/
@Component
public class UserManage {

    private final IRoleService roleService;

    private final IUserService userService;

    private final ILibManagerService libManagerService;

    private final ISysManagerService sysManagerService;


    @Value("${upload.picture.path}")
    private String uploadPicturePath;

    public UserManage(IRoleService roleService, IUserService userService, ILibManagerService libManagerService, ISysManagerService sysManagerService) {
        this.roleService = roleService;
        this.userService = userService;
        this.libManagerService = libManagerService;
        this.sysManagerService = sysManagerService;
    }


    /**
         * @Author MRH0045
         * @Description  登录
         * @Date 19:58 2020/6/23
         * @Param [code, password]
         * @return com.library.model.vo.UserVo
         **/
    public UserVo login(String code, String password) {

        User user = userService.getByCode(code);
        LibManager libManager = libManagerService.getByCode(code);
        SysManager sysManager = sysManagerService.getByCode(code);

        if (user != null) {          // 学生登录
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_number",code)       // 通过学号登录
                    .eq("password",password);
            User u = userService.getOne(queryWrapper);
            if(u==null) throw new EmptyException("账号或密码错误");
            userService.saveOrUpdate(u);
            String token = JWTUtil.encryptToken(JWTUtil.sign(code,password));
            UserVo userVo = UserVo.convert(u,token);
            userVo.setRole(this.getRolesByCode(code));
            return userVo;
        }
        else if (libManager != null) {  // 教师登录
            QueryWrapper<LibManager> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", code)       // 通过工号登录
                    .eq("password", password);
            LibManager t = libManagerService.getOne(queryWrapper);
            if(t==null) throw new EmptyException("账号或密码错误");
            libManagerService.saveOrUpdate(t);
            String token = JWTUtil.encryptToken(JWTUtil.sign(code, password));
            UserVo userVo = UserVo.convert(t, token);
            userVo.setRole(this.getRolesByCode(code));
            return userVo;
        }
        else if(sysManager != null){
            QueryWrapper<SysManager> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", code)       // 通过工号登录
                    .eq("password", password);
            SysManager s = sysManagerService.getOne(queryWrapper);
            if(s==null) throw new EmptyException("账号或密码错误");
            sysManagerService.saveOrUpdate(s);
            String token = JWTUtil.encryptToken(JWTUtil.sign(code, password));
            UserVo userVo = UserVo.convert(s, token);
            userVo.setRole(this.getRolesByCode(code));
            return userVo;

        }
        // 登录失败
        return null;
    }

    /**
         * @Author MRH0045
         * @Description 用过旧密码修改密码
         * @Date 22:47 2020/6/23
         * @Param [oldPassword, newPassword]
         * @return com.library.common.ServerResponse
         **/
    public ServerResponse changePassword(String oldPassword, String newPassword) {
        String code = this.getCodeByToken();
        User user = userService.getByCode(code);
        LibManager libManager = libManagerService.getByCode(code);
        SysManager sysManager = sysManagerService.getByCode(code);
        boolean result = false;
        if (user != null) {          // 用户
            userService.updatePassword(code, oldPassword, newPassword);
        } else if (libManager != null) {  // 图书管理员
            libManagerService.updatePassword(code, oldPassword, newPassword);
        } else if (sysManager != null){
            sysManagerService.updatePassword(code, oldPassword, newPassword);
        }
        return ServerResponse.createBySuccessMessage("密码修改成功");
    }

    /**
         * @Author MRH0045
         * @Description 通过token获取用户信息
         * @Date 22:47 2020/6/23
         * @Param []
         * @return com.library.model.vo.UserVo
         **/
    public UserVo getUserInfo() {
        String code = getCodeByToken();

        User user = userService.getByCode(code);
        LibManager libManager = libManagerService.getByCode(code);
        SysManager sysManager = sysManagerService.getByCode(code);

        List<String> roles = getRolesByCode(code);

        if (user != null) {
            if (roles.isEmpty()) {
                roles.add(Const.USER);
            }
            // 重新签署token
            String token = JWTUtil.encryptToken(JWTUtil.sign(code, user.getPassword()));
            UserVo userVo = UserVo.convert(user, token);
            userVo.setRole(roles);
            return userVo;
        } else if (libManager != null) {
            if (roles.isEmpty()) {
                roles.add(Const.LIBMANAGER);
            }
            // 重新签署token
            String token = JWTUtil.encryptToken(JWTUtil.sign(code, libManager.getPassword()));
            UserVo userVo = UserVo.convert(libManager, token);
            userVo.setRole(roles);
            return userVo;
        }
        else if(sysManager != null){
            if (roles.isEmpty()) {
                roles.add(Const.SYSMANAGER);
            }
            // 重新签署token
            String token = JWTUtil.encryptToken(JWTUtil.sign(code, sysManager.getPassword()));
            UserVo userVo = UserVo.convert(sysManager, token);
            userVo.setRole(roles);
            return userVo;
        }
        throw new AuthenticationException("用户token异常！");
    }

    /**
         * @Author MRH0045
         * @Description 注册
         * @Date 22:48 2020/6/23
         * @Param [code, password, role]
         * @return com.library.common.ServerResponse
         **/
    public ServerResponse register(String code, String password, String role) {
        if (role.equals(Const.USER)) {          // 学生注册
            User user  = userService.getByCode(code);
            // 学号不能重复
            if (user == null) {
                User s = new User();
                s.setStudentNumber(code);
                s.setPassword(password);
                userService.saveOrUpdate(s);
                return ServerResponse.createBySuccessMessage("注册成功");
            } else {
                return ServerResponse.createByErrorMessage("此学号已被注册");
            }
        } else if (role.equals(Const.LIBMANAGER)) {  // 教师注册
            LibManager libManager = libManagerService.getByCode(code);
            // 工号不能重复
            if (libManager == null) {
                LibManager t = new LibManager();
                t.setUsername(code);
                t.setPassword(password);
                libManagerService.saveOrUpdate(t);
                return ServerResponse.createBySuccessMessage("注册成功");
            } else {
                return ServerResponse.createByErrorMessage("此工号已被注册");
            }
        }
        // 注册失败
        return ServerResponse.createByErrorMessage("注册失败");
    }

    /**
         * @Author MRH0045
         * @Description 通过token获取role集合
         * @Date 22:48 2020/6/23
         * @Param []
         * @return java.util.List<java.lang.String>
         **/
    public List<String> getRolesByToken() {
        String code = this.getCodeByToken();
        return getRolesByCode(code);
    }

   /**
        * @Author MRH0045
        * @Description  通过token获取code
        * @Date 22:48 2020/6/23
        * @Param []
        * @return java.lang.String
        **/
    public String getCodeByToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(Const.TOKEN);
        if (StringUtils.isEmpty(token))
            throw new AuthenticationException("请登录后访问！");
        token = JWTUtil.decryptToken(token);            // 解密token
        String code = JWTUtil.getName(token);
        User user = userService.getByCode(code);
        LibManager libManager = libManagerService.getByCode(code);
        SysManager sysManager = sysManagerService.getByCode(code);
        String password = null;
        if (user != null) {
            password = user.getPassword();
        } else if (libManager != null) {
            password = libManager.getPassword();
        } else if(sysManager != null){
            password = sysManager.getPassword();
        }
        // 校验token是否合法
        if (!JWTUtil.verify(token, code, password))
            throw new AuthenticationException("token校验不通过");
        return code;
    }

    /**
         * @Author MRH0045
         * @Description  通过code判断用户角色
         * @Date 22:48 2020/6/23
         * @Param [code]
         * @return java.util.List<java.lang.String>
         **/
    public List<String> getRolesByCode(String code) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getCode, code);
        List<String> roles = new ArrayList<>();
        List<Role> list = roleService.list(queryWrapper);
        if (list == null) {
            throw new AuthenticationException("用户未分配角色, 请联系管理员！");
        }
        list.forEach(r -> {
            roleService.checkRole(r.getRole());
            roles.add(r.getRole());
        });
        return roles;
    }


    /**
         * @Author MRH0045
         * @Description  通过UserVo修改信息
         * @Date 22:48 2020/6/23
         * @Param [userVo]
         * @return boolean
         **/
    public boolean updateUserInfo(UserVo userVo) {
        List<String> roles = this.getRolesByCode(this.getCodeByToken());
        UserVo user = this.getUserInfo();
        for (String role : roles) {
            if (role.equals(Const.USER)) {
                User user1 = new User();
                BeanUtils.copyProperties(userVo, user1);
                user1.setId(user.getId());
                return this.userService.updateById(user1);
            }
        }
        return false;
    }


    /**
         * @Author MRH0045
         * @Description  上传头像
         * @Date 22:49 2020/6/23
         * @Param [image]
         * @return com.library.common.ServerResponse
         **/
    public  ServerResponse uploadAvatar(MultipartFile image) throws IOException {
        String url="";
        //检查图片的大小
        boolean flag = FileUtil.checkFileSize(image.getSize(), 5, "M");


        if (!flag)
        {
            //图片大小超限
            return ServerResponse.createByErrorMessage("图片大小超过限制(5M), 请重新上传");
        }
        if (!image.isEmpty())
        {
            String originalFilename = image.getOriginalFilename();//获取图片文件的名字
            String path = null;
            String type = null; //图片类型
            type = originalFilename.indexOf(".") != -1 ?
                    originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length())
                    : null;

            if (type != null)
            {
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase()))
                {
                    // 新的图片的名称
                    String trueFileName = System.currentTimeMillis() + FileUtil.getRandomString(15)+'.'+type;
                    // 设置存放图片文件的路径
                    path = uploadPicturePath + trueFileName;
                    File file1 = new File(uploadPicturePath);
                    if (!file1.exists())
                    {
                        file1.mkdirs();
                    }
                    //把图片存储到服务器中
                    image.transferTo(new File(path));
                    url = trueFileName;
                    return ServerResponse.createBySuccessMessage("上传成功!",url);
                }
                return ServerResponse.createByErrorMessage("上传图片失败, 文件类型错误");
            }
        }
        return ServerResponse.createByErrorMessage("上传图片失败, 请重新上传");
    }

}
