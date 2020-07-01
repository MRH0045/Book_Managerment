package com.library.controller;


import com.library.common.ServerResponse;
import com.library.model.Form.queryBooksForm;
import com.library.pojo.Books;
import com.library.service.impl.BooksService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 0045M
 * @since 2020-06-23
 */
@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    BooksService booksService;


    @ApiOperation("添加图书")
    @PutMapping()
    public ServerResponse AddBooks(@RequestBody Books books){
        return booksService.AddBooks(books);
    }

    @ApiOperation("删除图书")
    @ApiImplicitParam(name = "id", value = "图书id", required = true, paramType = "path", dataType = "int", example = "1")
    @DeleteMapping("/{id:\\d+}")
    public ServerResponse removeBooks(@PathVariable Integer id){
        return booksService.deleteBooks(id);
    }




    @ApiOperation("根据表单分页查询一定数量的图书")
//    @ApiImplicitParam(name="queryBooksForm",value="查询表单",required=true,paramType="query")
    @GetMapping("/query")
    public ServerResponse query( queryBooksForm queryBooksForm){
        return booksService.queryBooksByForm(queryBooksForm);
    }

    @ApiOperation("用户借书")
    @PutMapping("/borrow/{id}")
    public  ServerResponse borrowBook(@PathVariable("id")Integer id){
        return booksService.BorrowBook(id);
    }

    @ApiOperation("归还图书")
    @PutMapping("/return/{id}")
    public ServerResponse returnBook(@PathVariable("id")Integer id){
        return booksService.returnBook(id);
    }

}

