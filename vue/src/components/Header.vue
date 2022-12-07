<template>
  <div style="line-height: 60px;">
    <div style="text-align: left;">
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px;">
        <el-breadcrumb-item :to="{ path: '/'}">首页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ this.$route.meta.title }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 150px; cursor: pointer; ">
      <img v-if="user.avatar" :src="user.avatar" alt=""
           style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
      <img v-else src="../assets/logo.png" alt=""
           style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
      <div v-if="user.nickname">
      <span>{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-right: 5px"></i></div>
      <span v-else>{{ user.username }}</span><i class="el-icon-arrow-down" style="margin-right: 5px"></i>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">修改密码</el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          个人信息
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span style="text-decoration: none" >退出</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
  },
  watch: {
    '$route': function() {
      this.currentPathName = localStorage.getItem("currentPathName")
    }
  },
  data() {
    return {
      currentPathName: '',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}

    }
  }
}
</script>

<style scoped>

</style>