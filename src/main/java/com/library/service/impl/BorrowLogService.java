package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.library.common.ServerResponse;
import com.library.pojo.BorrowLog;
import com.library.dao.BorrowLogMapper;
import com.library.service.IBorrowLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 0045M
 * @since 2020-05-23
 */
@Service
public class BorrowLogService extends ServiceImpl<BorrowLogMapper, BorrowLog> implements IBorrowLogService {


    @Autowired(required=false)
    BorrowLogMapper borrowLogMapper;

    @Override
    public boolean addBorrowLog(BorrowLog borrowLog) {
        return borrowLogMapper.insert(borrowLog)>0? true:false;
    }

    @Override
    public ServerResponse removeBorrowLog(Integer id) {
        return borrowLogMapper.deleteById(id)>0?
                ServerResponse.createBySuccessMessage("删除成功"):
                ServerResponse.createByErrorMessage("删除失败");
    }

    @Override
    public ServerResponse queryAll() {
        QueryWrapper<BorrowLog> queryWrapper = new QueryWrapper<>();
        List<BorrowLog> logList = borrowLogMapper.selectList(queryWrapper);
        return ServerResponse.createBySuccess(logList);
    }


}
