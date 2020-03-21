package com.longbei.excel;

import com.longbei.bean.User;
import com.longbei.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dyh
 * @create 2018-07-14 下午9:21
 * @desc excel测试类
 **/

@Service
public class ExcelService {

    @Autowired
    private UserService userServiceImpl;

    /**
     * 得到所有地址列表
     *
     * @return
     */
    public List<User> findAll() {
        return userServiceImpl.getAll();
    }
}