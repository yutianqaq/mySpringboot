package com.example.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.common.ResultCode;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.JWTUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();

    @Override
    public UserDTO login(UserDTO userDTO) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查信息
        } catch (ServiceException e) {
            LOG.error(e);
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "系统错误");
        }
        if (one != null) {
            BeanUtil.copyProperties(one, userDTO, true);
            String token = JWTUtils.createToken(userDTO.getId().toString());
            userDTO.setToken(token);
            return userDTO;
        } else {
            throw new ServiceException(ResultCode.UNAUTHORIZED, "用户名或密码错误");
        }
    }

    @Override
    public UserDTO register(UserDTO userDTO) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        User one;
        try {
            one = getOne(queryWrapper); // 从数据库查信息
        } catch (ServiceException e) {
            LOG.error(e);
            throw new ServiceException(ResultCode.INTERNAL_SERVER_ERROR, "系统错误");
        }
        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
            return userDTO;
        } else {
            throw new ServiceException(ResultCode.UNAUTHORIZED, "用户名或密码错误");
        }

    }

}
