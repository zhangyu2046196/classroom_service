package com.longbei.service.api;

import com.longbei.bean.User;

/**
 * @author zhangy
 * @version 1.0
 * @description
 * @date 2020/3/10 21:50
 */
public interface UserService {

    /**
     * 查询用户
     *
     * @param name 用户名
     * @return 返回用户bean
     */
    public User getUserByName(String name);

    /**
     * 删除用户
     *
     * @param name 用户名
     * @return 返回删除用户数
     */
    public int delete(String name);

    /**
     * 增加用户
     *
     * @param user 用户
     * @return 返回增加的用户
     */
    public void insert(User user);

    /**
     * 修改用户
     *
     * @param user 用户
     * @return 返回修改数
     */
    public int update(User user);

}
