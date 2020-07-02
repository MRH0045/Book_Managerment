package com.library.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.common.Const;
import com.library.common.ServerResponse;
import com.library.dao.BorrowLogMapper;
import com.library.dao.UserMapper;
import com.library.manage.UserManage;
import com.library.model.Form.queryBooksForm;
import com.library.pojo.Books;
import com.library.dao.BooksMapper;
import com.library.pojo.BorrowLog;
import com.library.pojo.User;
import com.library.service.IBooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 0045M
 * @since 2020-06-23
 */
@Service
public class BooksService extends ServiceImpl<BooksMapper, Books> implements IBooksService {

    @Autowired(required=false)
    BooksMapper booksMapper;

    @Autowired(required = false)
    UserMapper userMapper;

    @Autowired(required = false)
    BorrowLogMapper borrowLogMapper;

    @Autowired
    UserManage userManage;

    /**
         * @Author MRH0045
         * @Description  添加图书
         * @Date 19:48 2020/6/25
         * @Param [books]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse AddBooks(Books books) {
        books.setLendCount(0);
        books.setCreateTime(LocalDateTime.now());
        books.setUpdateTime(LocalDateTime.now());
        return booksMapper.insert(books)>0?
                ServerResponse.createBySuccessMessage("添加成功"):
                ServerResponse.createByErrorMessage("添加失败，请稍后再试！");
    }

    /**
         * @Author MRH0045
         * @Description 根据queryBooksForm分页查询满足条件的图书
         * @Date 14:59 2020/6/26
         * @Param [queryBooksForm]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse queryBooksByForm(queryBooksForm queryBooksForm) {
        Page page = new Page<>(queryBooksForm.getPage(),queryBooksForm.getPageSize());
        QueryWrapper<Books> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(queryBooksForm.getKeyWords()!=null&&queryBooksForm.getKeyWords()!= "","name",queryBooksForm.getKeyWords()).or()
                .like(queryBooksForm.getKeyWords()!=null&&queryBooksForm.getKeyWords()!="","author",queryBooksForm.getKeyWords()).or()
                .like(queryBooksForm.getKeyWords()!=null&&queryBooksForm.getKeyWords()!="","name_pub",queryBooksForm.getKeyWords()).or()
                .like(queryBooksForm.getKeyWords()!=null&&queryBooksForm.getKeyWords()!="","details",queryBooksForm.getKeyWords())
                .eq(queryBooksForm.getBookKind()!=null,"book_kind",queryBooksForm.getBookKind())
                .eq(queryBooksForm.getBookSite()!=null,"book_site",queryBooksForm.getBookSite())
               .orderByDesc(queryBooksForm.getSortType()==1&&queryBooksForm.getSortType()!=null,"create_time")
              .orderByDesc(queryBooksForm.getSortType()==0&&queryBooksForm.getSortType()!=null,"update_time");
        IPage iPage = booksMapper.selectPage(page, queryWrapper);
        HashMap<String,Object> list = new HashMap<>();
        list.put("data",iPage.getRecords());
        list.put("CurrentPage",(int)iPage.getCurrent());
        list.put("total",(int)iPage.getTotal());
        return ServerResponse.createBySuccess(list);
    }

    /**
         * @Author MRH0045
         * @Description 根据id删除图书
         * @Date 14:59 2020/6/26
         * @Param [id]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse deleteBooks(Integer id) {
        return booksMapper.deleteById(id)>0?
                ServerResponse.createBySuccessMessage("删除成功！"):
                ServerResponse.createByErrorMessage("删除失败！");
    }

    /**
         * @Author MRH0045
         * @Description 根据id修改图书的信息
         * @Date 15:00 2020/6/26
         * @Param [id, books]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse updateBook( Books books) {
        books.setUpdateTime(LocalDateTime.now());
        return booksMapper.updateById(books)>0?
                ServerResponse.createBySuccessMessage("更新成功！"):
                ServerResponse.createByErrorMessage("更新失败！");
    }

    /**
         * @Author MRH0045
         * @Description 用户借书
         * @Date 13:50 2020/6/28
         * @Param [BookId]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse BorrowBook(Integer BookId) {
        String code = userManage.getCodeByToken();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("student_number",code);
        User user = userMapper.selectOne(wrapper);
       // User user = userMapper.selectById(1);
        if(user.getStatus()==0){
            Books books = booksMapper.selectById(BookId);
            BorrowLog borrowLog = new BorrowLog();
            if(books.getLendCount()<books.getTotal()){
                books.setLendCount(books.getLendCount()+1);
                user.setBrrowedCount(user.getBrrowedCount()+1);
                userMapper.updateById(user);
                booksMapper.updateById(books);
                borrowLog.setBookId(BookId);
                borrowLog.setUserId(user.getId());
                borrowLog.setStatus(Const.UNRETURN);
                borrowLog.setCreateTime(LocalDateTime.now());
                borrowLog.setUpdateTime(LocalDateTime.now());
                borrowLog.setBorrowTime(LocalDateTime.now());
                return borrowLogMapper.insert(borrowLog)>0?
                        ServerResponse.createBySuccessMessage("借书成功"):
                        ServerResponse.createByErrorMessage("借书失败");
            }
            return ServerResponse.createByErrorMessage("仓库图书不足！");
        }
        return ServerResponse.createByErrorMessage("账户已过期！");
    }

    /**
         * @Author MRH0045
         * @Description 用户还书
         * @Date 13:50 2020/6/28
         * @Param [BorrowLogId]
         * @return com.library.common.ServerResponse
         **/

    @Override
    public ServerResponse returnBook(Integer BorrowLogId) {
        String code = userManage.getCodeByToken();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("student_number",code);
        User user = userMapper.selectOne(wrapper);
        user.setBrrowedCount(user.getBrrowedCount()-1);
        userMapper.updateById(user);
        BorrowLog borrowLog = borrowLogMapper.selectById(BorrowLogId);
        Books books = booksMapper.selectById(borrowLog.getBookId());
        books.setLendCount(books.getLendCount()-1);
        booksMapper.updateById(books);
        borrowLog.setStatus(Const.RETURNED);
        borrowLog.setReturnTime(LocalDateTime.now());
        borrowLog.setUpdateTime(LocalDateTime.now());
        return borrowLogMapper.updateById(borrowLog)>0?
                ServerResponse.createBySuccessMessage("归还成功！"):
                ServerResponse.createByErrorMessage("归还失败");

    }


//    public IPage<Books> selectUserPage(Page<Books> page, Integer state) {
//        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
//        // page.setOptimizeCountSql(false);
//        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
//        // 要点!! 分页返回的对象与传入的对象是同一个
//        return booksMapper.selectPageVo(page,state);
//    }

}
