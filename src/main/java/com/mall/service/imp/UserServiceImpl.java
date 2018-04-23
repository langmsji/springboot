package com.mall.service.imp;

import com.mall.entity.User;
import com.mall.mapper.UserMapper;
import com.mall.service.AbstractService;
import com.mall.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService{
    @Resource
    private UserMapper userMapper;
}
