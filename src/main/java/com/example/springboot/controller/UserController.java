package com.example.springboot.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.common.ResultResponse;
import com.example.springboot.controller.dto.UserDTO;
import com.example.springboot.entity.User;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.CheckRules;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;


    @PostMapping("/login")
    public Result userLogin(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return ResultResponse.getFailResult("输入错误");
        }
        return ResultResponse.getSuccessResult(userService.login(userDTO));
    }

    @PostMapping("/register")
    public Result userRegister(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        String password2 = userDTO.getPassword2();

        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return ResultResponse.getFailResult("输入错误");
        }
        if (!password.equals(password2)) {
            return ResultResponse.getFailResult("密码输出不相同");
        }
        if (!CheckRules.checkName(username)){
            return ResultResponse.getFailResult("用户名需3为以上需以字母开头");
        }
        if (!CheckRules.checkPwd(password)){
            return ResultResponse.getFailResult("密码限制7位以上20为以下");
        }
        return ResultResponse.getSuccessResult(userService.register(userDTO));
    }


    @PostMapping
    public Result save(@RequestBody User user) {
//        System.out.println(user);
        // 新增或更新
        if (user.getPassword() == null) { //默认密码
            user.setPassword("123456.");
        }
        return  ResultResponse.getSuccessResult(userService.saveOrUpdate(user));
    }

    //    查询所有数据
    @GetMapping()
    @ApiOperation(value = "获取用户列表", notes = "获取所有用户的列表")
    public Result findAll() {

        return ResultResponse.getSuccessResult(userService.list());
    }

    //    删除用户
    @DeleteMapping("/{id}") // url 中使用 PathVariable
    public Result delete(@PathVariable Integer id) {

        return ResultResponse.getSuccessResult(userService.removeById(id));
    }

    // 批量删除用户
    @PostMapping("/del/batch")
    public Result deleteByIds(@RequestBody List<Integer> ids) {
        return ResultResponse.getSuccessResult(userService.removeByIds(ids));
    }

    //    查询所有数据
    // 分页查询实现
    // 接口路径: /user/page
    // @RequestParam ？pageNum=1&pageSize=10
//    @GetMapping("/page")
//    public Map<String, Object> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
//
//        // 对传过来的数据做处理
//        // limit第一个参数 = (pageNum - 1) * pageSize
//        pageNum = (pageNum - 1) * pageSize;
//
//        return userService.findPage(pageNum, pageSize);
//    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address) {

        // 对传过来的数据做处理
        // limit第一个参数 = (pageNum - 1) * pageSize
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }

        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }

        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        return ResultResponse.getSuccessResult(userService.page(page, queryWrapper));

    }

    /**
     * 导入excel
     *
     * @param file
     */
    @PostMapping("/import")
    public void importExcel(MultipartFile file) throws IOException {
        //1.第一种 头必须和实体(英文)一样
        //文件处理成io流
        InputStream in = file.getInputStream();
//        //io流给ExcelReader
        ExcelReader excelReader = ExcelUtil.getReader(in);
//        //读取数据且转化为list
//        List<User> list = excelReader.readAll(User.class);

        //2.第二种导入方式
        //忽略第一行头(第一行是中文的情况),直接读取表的内容
        List<List<Object>> list = excelReader.read(1);
        List<User> listUser = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUsername(row.get(1).toString());
            user.setPassword(row.get(2).toString());
            user.setNickname(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setEmail(row.get(5).toString());
            user.setAddress(row.get(6).toString());
            user.setAvatar(row.get(7).toString());
            listUser.add(user);
//            ****类似一一对应****
        }
        //批量注册进数据库
        userService.saveBatch(listUser);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {
        // 查出数据
        List<User> list = userService.list();
        // 选择导出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 定义标题列名
        writer.addHeaderAlias("username", "用户名");
        writer.addHeaderAlias("password", "密码");
        writer.addHeaderAlias("nickname", "昵称");
        writer.addHeaderAlias("phone", "手机号");
        writer.addHeaderAlias("email", "邮箱");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("create_time", "创建时间");
        writer.addHeaderAlias("avatar", "头像");

        //一次性写出内容，强制输出标题
        writer.write(list, true);
        //设置content—type
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset:utf-8");

        //设置标题
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        //Content-disposition是MIME协议的扩展，MIME协议指示MIME用户代理如何显示附加的文件。
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();

        //将Writer刷新到OutPut
        writer.flush(outputStream, true);
        outputStream.close();
        writer.close();
    }
}
