package com.library.controller;


import com.library.common.ServerResponse;
import com.library.dao.BorrowLogMapper;
import com.library.model.Form.queryBorrowLogForm;
import com.library.service.impl.BorrowLogService;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@RestController
@RequestMapping("/borrowLog")
public class BorrowLogController {

    @Autowired
    BorrowLogService borrowLogService;

    @ApiOperation("根据表单分页查询借书记录")
    @GetMapping
    public ServerResponse queryBorrowLog(queryBorrowLogForm queryBorrowLogForm){
        return borrowLogService.queryByForm(queryBorrowLogForm);
    }

}

