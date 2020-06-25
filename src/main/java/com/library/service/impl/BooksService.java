package com.library.service.impl;

import com.library.pojo.Books;
import com.library.dao.BooksMapper;
import com.library.service.IBooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
