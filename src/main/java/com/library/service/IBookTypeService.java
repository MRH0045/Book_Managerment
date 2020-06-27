package com.library.service;

import com.library.common.ServerResponse;
import com.library.pojo.BookLocation;
import com.library.pojo.BookType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
public interface IBookTypeService extends IService<BookType> {

    ServerResponse addBookType(BookType bookType);

    ServerResponse removeBookType(Integer id);

    ServerResponse updateBookType(BookType bookType);

    ServerResponse queryBookType(Integer id);

    ServerResponse queryAllBookType();

}
