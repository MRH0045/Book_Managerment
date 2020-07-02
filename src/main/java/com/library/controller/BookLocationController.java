package com.library.controller;


import com.library.common.ServerResponse;
import com.library.pojo.BookLocation;
import com.library.service.IBookLocationService;
import com.library.service.impl.BookLocationService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/bookLocation")
public class BookLocationController {

    @Autowired
    BookLocationService bookLocationService;

    /**
         * @Author MRH0045
         * @Description  添加图书定位
         * @Date 13:00 2020/6/21
         * @Param [bookLocation]
         * @return com.library.common.ServerResponse
         **/
    @ApiOperation(value = "添加图书定位")
    @PutMapping("/save")
    public ServerResponse addBookLocation(BookLocation bookLocation){
        return bookLocationService.addBookLocation(bookLocation);
    }


    /**
         * @Author MRH0045
         * @Description  根据id删除图书点位
         * @Date 13:01 2020/6/21
         * @Param [id]
         * @return com.library.common.ServerResponse<java.lang.String>
         **/
    @ApiOperation(value = "删除图书定位")
    @ApiImplicitParam(name="id",value="类型Id",required=true,paramType="path",dataType = "int",example = "1")
    @DeleteMapping("/{id:\\d+}")
    public ServerResponse<String> removeBookLocation(@PathVariable Integer id)
    {
        return bookLocationService.removeBookLocation(id);
    }



    /**
         * @Author MRH0045
         * @Description 更新图书定位
         * @Date 13:04 2020/6/21
         * @Param [bookLocation]
         * @return com.library.common.ServerResponse
         **/
    @ApiOperation("修改图书定位")
    @PutMapping
    public ServerResponse updateBookLocation(@RequestBody BookLocation bookLocation){
        return bookLocationService.updateBookLocation(bookLocation);
    }


    /**
         * @Author MRH0045
         * @Description  根据id获得单个图书定位
         * @Date 13:56 2020/6/21
         * @Param [id]
         * @return com.library.common.ServerResponse<com.library.pojo.BookLocation>
         **/
    @ApiOperation("根据id获得单个图书")
    @ApiImplicitParam(name = "id",value = "图书定位id",required = true,paramType = "query",dataType = "int",example = "1")
    @GetMapping
    public ServerResponse<BookLocation> getLocation(Integer id){
        return  bookLocationService.queryBookLocation(id);
    }


    /**
         * @Author MRH0045
         * @Description 获得全部定位
         * @Date 1:35 2020/6/26
         * @Param []
         * @return com.library.common.ServerResponse<java.util.List<com.library.pojo.BookLocation>>
         **/
    @ApiOperation("获得全部图书定位")
    @GetMapping("/getAllBookLocation")
    public ServerResponse<List<BookLocation>> getAllBookLocation(){
        return bookLocationService.queryAllBookLocation();
    }








}

