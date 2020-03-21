package com.longbei.mapper;

import com.longbei.annotation.LBRedisCache;
import com.longbei.bean.User;
import com.longbei.common.RedisKeyConstant;
import com.longbei.common.RedisType;
import org.apache.ibatis.annotations.*;

/**
 * @author zhangy
 * @version 1.0
 * @description 用户接口mapper
 * @date 2020/3/10 21:42
 */
@Mapper
public interface UserMapper {

    /**
     * 查询用户
     *
     * @param name 用户名
     * @return 返回用户bean
     */
    @Select("select * from user where name=#{name}")
    public User getUserByName(String name);

    /**
     * 删除用户
     *
     * @param name 用户名
     * @return 返回删除用户数
     */
    @Delete("delete from user where name=#{name}")
    public int delete(String name);

    /**
     * 增加用户
     * useGeneratedKeys 主键使用自增
     * keyProperty   主键的属性是id
     *
     * @param user 用户
     * @return 返回增加的用户
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert({"insert into user (name,address) values (#{name},#{address})"})
    public void insert(User user);

    /**
     * 修改用户
     *
     * @param user 用户
     * @return 返回修改数
     */
    @Update({"update user set name=#{name},address=#{address} where id=#{id}"})
    public int update(User user);

}
