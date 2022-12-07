package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.User;


//@Mapper
public interface UserMapper extends BaseMapper<User> {

//    @Select("select * from sys_user")
//    List<User> findAll();
//
//    @Insert("INSERT into sys_user(username, password, nickname, phone, email, address) VALUES " +
//            "(#{username}, #{password}, #{nickname}, #{phone}, #{email}, #{address})")
//    int Insert(User user);
//
//    Integer update(User user);
//
//    @Delete("DELETE from sys_user where id = #{id} ;")
//    Integer deleteById(@Param("id") Integer id);
//
//    @Select("select * from sys_user limit #{pageNum}, #{pageSize}")
//    List<User> selectPage1(Integer pageNum, Integer pageSize);
//
//    @Select("SELECT count(*) from (select * from sys_user limit #{pageNum}, #{pageSize}) a")
//    Integer selectTotal(Integer pageNum, Integer pageSize);
}
