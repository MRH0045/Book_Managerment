package com.library.controller;


import com.library.common.ServerResponse;
import com.library.pojo.BookType;
import com.library.service.impl.BookTypeService;
import com.library.service.impl.BooksService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/bookType")
public class BookTypeController {

    @Autowired
    BookTypeService bookTypeService;

    @ApiOperation("增加图书类别")
    @PutMapping
    public ServerResponse addBookType(BookType bookType){
        return bookTypeService.addBookType(bookType);
    }

    @ApiOperation("根据id删除图书类别")
    @DeleteMapping("/{id:\\d+}")
    public ServerResponse removeBookType(@PathVariable Integer id){
        return bookTypeService.removeBookType(id);
    }

    @ApiOperation("更新图书类别")
    @PutMapping("/updateBookType")
    public ServerResponse updateBookType(BookType bookType){
        return bookTypeService.updateBookType(bookType);
    }

    @ApiOperation("查询所有图书种类")
    @GetMapping
    public  ServerResponse queryAllBookType(){
        return bookTypeService.queryAllBookType();
    }

}

