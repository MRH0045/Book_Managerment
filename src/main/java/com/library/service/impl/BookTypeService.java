package com.library.service.impl;

import com.library.pojo.BookType;
import com.library.dao.BookTypeMapper;
import com.library.service.IBookTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
