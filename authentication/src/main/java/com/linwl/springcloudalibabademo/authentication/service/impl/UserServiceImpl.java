package com.linwl.springcloudalibabademo.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linwl.springcloudalibabademo.authentication.entity.db1.UserEntity;
import com.linwl.springcloudalibabademo.authentication.mapper.db1.UserMapper;
import com.linwl.springcloudalibabademo.authentication.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/17 10:21
 * @description ：
 * @modified By：
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}