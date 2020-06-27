package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.common.ServerResponse;
import com.library.pojo.BookType;
import com.library.dao.BookTypeMapper;
import com.library.service.IBookTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@Service
public class BookTypeService extends ServiceImpl<BookTypeMapper, BookType> implements IBookTypeService {


    @Autowired(required=false)
    BookTypeMapper bookTypeMapper;

    @Override
    public ServerResponse addBookType(BookType bookType) {
        return bookTypeMapper.insert(bookType)>0?
                ServerResponse.createBySuccessMessage("添加成功"):
        ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse removeBookType(Integer id) {
        return bookTypeMapper.deleteById(id)>0?
                ServerResponse.createBySuccessMessage("删除成功"):
                ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse updateBookType(BookType bookType) {
        return bookTypeMapper.updateById(bookType)>0?
                ServerResponse.createBySuccessMessage("更新成功"):
                ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse queryBookType(Integer id) {
        BookType bookType = bookTypeMapper.selectById(id);
        return bookType!=null?ServerResponse.createBySuccess(bookType):
                ServerResponse.createByErrorMessage("不存在该图书位置");
    }

    @Override
    public ServerResponse queryAllBookType() {
        QueryWrapper<BookType> queryWrapper = new QueryWrapper<>();
        List<BookType> typeList = bookTypeMapper.selectList(queryWrapper);
        return ServerResponse.createBySuccess(typeList);
    }
}
