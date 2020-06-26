package com.library.service;

import com.library.common.ServerResponse;
import com.library.model.Form.BookForm;
import com.library.model.Form.queryBooksForm;
import com.library.pojo.Books;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 0045M
 * @since 2020-06-23
 */
public interface IBooksService extends IService<Books> {

    ServerResponse AddBooks(Books books);

    ServerResponse queryBooksByForm(queryBooksForm queryBooksForm);

    ServerResponse deleteBooks(Integer id);

    ServerResponse updateBook(Integer id,Books books);




}
