package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.common.ServerResponse;
import com.library.pojo.BookType;
import com.library.dao.BookTypeMapper;
import com.library.service.IBookTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        QueryWrapper<BookType> wrapper = new QueryWrapper<>();
        wrapper.eq("name",bookType.getName());
        BookType bookType1 = bookTypeMapper.selectOne(wrapper);
        if(bookType1==null){
            bookType.setCreateTime(LocalDateTime.now());
            bookType.setUpdateTime(LocalDateTime.now());
            return bookTypeMapper.insert(bookType)>0?
                    ServerResponse.createBySuccessMessage("添加成功"):
                    ServerResponse.createByErrorMessage("添加失败");
        }
        return ServerResponse.createByErrorMessage("图书种类已存在");
    }

    @Override
    public ServerResponse removeBookType(Integer id) {
        return bookTypeMapper.deleteById(id)>0?
                ServerResponse.createBySuccessMessage("删除成功"):
                ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse updateBookType(BookType bookType) {
        QueryWrapper<BookType> wrapper = new QueryWrapper<>();
        wrapper.eq("name",bookType.getName());
        BookType bookType1 = bookTypeMapper.selectOne(wrapper);
        if(bookType1!=null){
            bookType.setUpdateTime(LocalDateTime.now());
            return bookTypeMapper.updateById(bookType)>0?
                    ServerResponse.createBySuccessMessage("更新成功"):
                    ServerResponse.createByErrorMessage("更新失败");
        }
        return ServerResponse.createByErrorMessage("图书种类已存在，修改失败！");
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
