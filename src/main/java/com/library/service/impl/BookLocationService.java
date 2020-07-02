package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.library.common.ServerResponse;
import com.library.pojo.BookLocation;
import com.library.dao.BookLocationMapper;
import com.library.service.IBookLocationService;
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
public class BookLocationService extends ServiceImpl<BookLocationMapper, BookLocation> implements IBookLocationService {


    @Autowired(required = false)
    BookLocationMapper bookLocationMapper;

    @Override
    public ServerResponse addBookLocation(BookLocation bookLocation) {
        bookLocation.setCreateTime(LocalDateTime.now());
        bookLocation.setUpdateTime(LocalDateTime.now());
        return bookLocationMapper.insert(bookLocation)>0?
                ServerResponse.createBySuccessMessage("添加成功"):
                ServerResponse.createByErrorMessage("添加失败");
    }

    @Override
    public ServerResponse removeBookLocation(Integer id) {
        return bookLocationMapper.deleteById(id)>0?
                ServerResponse.createBySuccessMessage("删除成功"):
                ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse updateBookLocation(BookLocation bookLocation) {
        return bookLocationMapper.updateById(bookLocation)>0?
                ServerResponse.createBySuccessMessage("更新成功"):
                ServerResponse.createByErrorMessage("更新失败");
    }

    @Override
    public ServerResponse queryBookLocation(Integer id) {

        return ServerResponse.createBySuccess(bookLocationMapper.selectById(id));
    }

    @Override
    public ServerResponse queryAllBookLocation() {
        QueryWrapper<BookLocation> queryWrapper = new QueryWrapper<>();
        return ServerResponse.createBySuccess(bookLocationMapper.selectList(queryWrapper));
    }
}
