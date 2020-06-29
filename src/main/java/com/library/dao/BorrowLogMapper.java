package com.library.dao;

import com.library.model.Form.queryBorrowLogForm;
import com.library.pojo.BorrowLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
public interface BorrowLogMapper extends BaseMapper<BorrowLog> {

    List<BorrowLog> queryByForm(queryBorrowLogForm queryBorrowLogForm);

}
