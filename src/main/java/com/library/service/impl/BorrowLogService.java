package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.common.ServerResponse;
import com.library.dao.BooksMapper;
import com.library.dao.UserMapper;
import com.library.manage.UserManage;
import com.library.model.Form.queryBorrowLogForm;
import com.library.pojo.*;
import com.library.dao.BorrowLogMapper;
import com.library.service.IBorrowLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.library.service.ILibManagerService;
import com.library.service.ISysManagerService;
import com.library.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@Service
public class BorrowLogService extends ServiceImpl<BorrowLogMapper, BorrowLog> implements IBorrowLogService {


    @Autowired(required=false)
    BorrowLogMapper borrowLogMapper;

    @Autowired(required = false)
    BooksMapper booksMapper;

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired
    UserManage userManage;

    @Autowired
    IUserService userService;

    @Autowired
    ILibManagerService libManagerService;

    @Autowired
    ISysManagerService sysManagerService;

    @Override
    public boolean addBorrowLog(BorrowLog borrowLog) {
        return borrowLogMapper.insert(borrowLog)>0? true:false;
    }

    @Override
    public ServerResponse removeBorrowLog(Integer id) {
        return borrowLogMapper.deleteById(id)>0?
                ServerResponse.createBySuccessMessage("删除成功"):
                ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse queryAll() {
        QueryWrapper<BorrowLog> queryWrapper = new QueryWrapper<>();
        List<BorrowLog> logList = borrowLogMapper.selectList(queryWrapper);
        return ServerResponse.createBySuccess(logList);
    }

    /**
         * @Author MRH0045
         * @Description 根据表单分页查询借书记录 用户查询到自己的 管理员查询到全部用户的借书记录
         * @Date 14:51 2020/6/28
         * @Param [queryBorrowLogForm]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse queryByForm(queryBorrowLogForm queryBorrowLogForm) {
        String code = userManage.getCodeByToken();
        User user = userService.getByCode(code);
        LibManager libManager = libManagerService.getByCode(code);
        SysManager sysManager = sysManagerService.getByCode(code);
        List<Map<String, Object>> allData = new ArrayList<>();
        Page page = new Page<>(queryBorrowLogForm.getPage(),queryBorrowLogForm.getPageSize());
        QueryWrapper<BorrowLog> BorrowLogWrapper = new QueryWrapper<>();
        IPage iPage =borrowLogMapper.selectPage(page,BorrowLogWrapper);
        List<BorrowLog> borrowLogList = null;
        if(user != null){
            BorrowLogWrapper.eq("user_id",user.getId())
                    .eq(queryBorrowLogForm.getStatus()!=null,"status",queryBorrowLogForm.getStatus());
            iPage = borrowLogMapper.selectPage(page,BorrowLogWrapper);
            borrowLogList = iPage.getRecords();
        }else if(libManager!=null||sysManager!=null){
            BorrowLogWrapper.eq(queryBorrowLogForm.getStatus()!=null,"status",queryBorrowLogForm.getStatus());
            iPage = borrowLogMapper.selectPage(page,BorrowLogWrapper);
            borrowLogList = iPage.getRecords();
        }
        for(BorrowLog borrowLog: borrowLogList){
            Map<String, Object> data = new HashMap<>();
            Books books = booksMapper.selectById(borrowLog.getBookId());
            User user1 = userMapper.selectById(borrowLog.getUserId());
            data.put("user",user1);
            data.put("borrowLog",borrowLog);
            data.put("book",books);
            allData.add(data);
        }
        HashMap<String,Object> list = new HashMap<>();
        list.put("data",allData);
        list.put("CurrentPage",iPage.getCurrent());
        list.put("total",iPage.getTotal());
        return ServerResponse.createBySuccess(list);
    }


}
