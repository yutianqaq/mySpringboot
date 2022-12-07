package com.example.springboot.controller.dto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(allowSetters = true, value = {"password"})
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String password2;
    private String nickname;
    @TableField(value = "avatar_url") // 指定数据库的字段名字
    private String avatar;
    private String token;


}
