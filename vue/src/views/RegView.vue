<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 350px; height: 470px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form :model="user" :rules="rules" ref="user">
        <el-form-item  label="用户名" prop="username">
          <el-input placeholder="请输入用户名" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item label="再次输入密码" prop="password2">
          <el-input placeholder="请再次输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password
                    v-model="user.password2"></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <router-link to="/login"><el-button type="primary" size="small" autocomplete="off">已有账号?点击登录</el-button></router-link>
          <el-button type="warning" size="small" autocomplete="off" @click="submitForm('user')">点击注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

export default {
  name: "RegView",
  data() {
    var validateUser = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'));
      } else {
        if (this.user.username !== '') {
          callback();
        }
        callback();
      }
    };
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.user.password !== '') {
          callback();
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.user.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      user: {
        username: '',
        password: '',
        password2: ''
      },
      rules: {
        username: [
          { validator: validateUser, trigger: 'blur' }
        ],
        password: [
          { validator: validatePass, trigger: 'blur' },
          { min: 5, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        password2: [
          { validator: validatePass2, trigger: 'blur' },
          { min: 5, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    submitForm(user) {
      this.$refs[user].validate((valid) => {
        if (valid) {
          this.login();
        } else {
          return false
        }
      });
    },
    login() {
      this.request.post("/user/register", this.user).then(res => {
        if (res.code === 200) {
          this.$message.success("注册成功")
        } else {
          this.$message.error("注册失败" + res.message)
        }
      }).catch(() => {
        this.$message.error("注册失败" + res.message)
      })
    },
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to right, #eea2a2 0%, #bbc1bf 19%, #57c6e1 42%, #b49fda 79%, #7ac5d8 100%);
  overflow: hidden;
}
</style>
