package com.longbei.service;

import com.longbei.annotation.LBRedisCache;
import com.longbei.bean.User;
import com.longbei.common.RedisKeyConstant;
import com.longbei.common.RedisType;
import com.longbei.mapper.UserMapper;
import com.longbei.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangy
 * @version 1.0
 * @description 用户接口实现类
 * @date 2020/3/10 21:52
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @LBRedisCache(prefixKey = RedisKeyConstant.CLASSROOM_PREFIX_KEY, key = "#name", type = RedisType.SAVE)
    @Override
    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    //@LBRedisCache(prefixKey = RedisKeyConstant.CLASSROOM_PREFIX_KEY, key = "#name", redisValue = "#user", type = RedisType.DELETE)
    @Override
    public int delete(String name) {
        return userMapper.delete(name);
    }


    //@LBRedisCache(prefixKey = RedisKeyConstant.CLASSROOM_PREFIX_KEY, key = "#user?.name", redisValue = "#user", type = RedisType.DELETE)
    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    //@LBRedisCache(prefixKey = RedisKeyConstant.CLASSROOM_PREFIX_KEY, key = "#user?.name", redisValue = "#user", type = RedisType.DELETE)
    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }
}
