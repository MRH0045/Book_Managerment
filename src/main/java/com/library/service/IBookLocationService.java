package com.library.service;

import com.library.common.ServerResponse;
import com.library.pojo.BookLocation;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
public interface IBookLocationService extends IService<BookLocation> {

     ServerResponse addBookLocation(BookLocation bookLocation);

     ServerResponse removeBookLocation(Integer id);

     ServerResponse updateBookLocation(BookLocation bookLocation);

     ServerResponse queryBookLocation(Integer id);

     ServerResponse queryAllBookLocation();


}
