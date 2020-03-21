package com.longbei.controller;

import com.longbei.bean.User;
import com.longbei.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description 测试用户增删改查
 * @date 2020/3/10 21:56
 */
@RestController
public class UserController {

    @Autowired
    private UserService userServiceImpl;

    /**
     * 查询
     *
     * @param name
     * @return
     */
    @GetMapping("/user/{name}")
    public User find(@PathVariable("name") String name) {
        System.out.println("method find invoke...");
        return userServiceImpl.getUserByName(name);
    }

    /**
     * 保存
     *
     * @param user
     * @return
     */
    @PostMapping("/user")
    public void insert(@RequestBody User user) {
        System.out.println("method insert invoke...");
        userServiceImpl.insert(user);
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @RequestMapping("/user/update")
    public int update(@RequestBody User user) {
        System.out.println("method update invoke...");
        return userServiceImpl.update(user);
    }

    /**
     * 删除
     *
     * @param name
     * @return
     */
    @GetMapping("/user/del/{name}")
    public int delete(@PathVariable("name") String name) {
        System.out.println("method    delete   invoke...");
        return userServiceImpl.delete(name);
    }

    /**
     * 获取所有用户列表
     * @return
     */
    @RequestMapping("/user/getAll")
    public List<User> getAll(){
        System.out.println("getAll user list invoke...");
        return userServiceImpl.getAll();
    }

}
