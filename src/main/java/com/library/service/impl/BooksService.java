package com.library.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.common.ServerResponse;
import com.library.model.Form.queryBooksForm;
import com.library.pojo.Books;
import com.library.dao.BooksMapper;
import com.library.service.IBooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;



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

    @Autowired
    BooksMapper booksMapper;

    /**
         * @Author MRH0045
         * @Description  添加图书
         * @Date 19:48 2020/6/25
         * @Param [books]
         * @return com.library.common.ServerResponse
         **/
    @Override
    public ServerResponse AddBooks(Books books) {
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
        queryWrapper.like(queryBooksForm.getKeyWords()!=null,"name",queryBooksForm.getKeyWords()).or()
                .like(queryBooksForm.getKeyWords()!=null,"author",queryBooksForm.getKeyWords()).or()
                .like(queryBooksForm.getKeyWords()!=null,"name_pub",queryBooksForm.getKeyWords()).or()
                .like(queryBooksForm.getKeyWords()!=null,"details",queryBooksForm.getKeyWords())
                .eq(queryBooksForm.getBookKind()!=null,"book_kind",queryBooksForm.getBookKind())
                .eq(queryBooksForm.getBookSite()!=null,"book_site",queryBooksForm.getBookSite());
//                .orderByDesc(queryBooksForm.getSortType()==0,"create_time")
 //              .orderByDesc(queryBooksForm.getSortType()==1,"update_time");
        IPage iPage = booksMapper.selectPage(page, queryWrapper);
//        if(iPage.getRecords().isEmpty()){
//            return ServerResponse.createByErrorMessage("无符合条件的图书！");
//        }
        return ServerResponse.createBySuccess(iPage.getRecords());
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
    public ServerResponse updateBook(Integer id, Books books) {
        books.setId(id);
        books.setUpdateTime(LocalDateTime.now());
        return booksMapper.updateById(books)>0?
                ServerResponse.createBySuccessMessage("更新成功！"):
                ServerResponse.createByErrorMessage("更新失败！");
    }



//    public IPage<Books> selectUserPage(Page<Books> page, Integer state) {
//        // 不进行 count sql 优化，解决 MP 无法自动优化 SQL 问题，这时候你需要自己查询 count 部分
//        // page.setOptimizeCountSql(false);
//        // 当 total 为小于 0 或者设置 setSearchCount(false) 分页插件不会进行 count 查询
//        // 要点!! 分页返回的对象与传入的对象是同一个
//        return booksMapper.selectPageVo(page,state);
//    }

}
