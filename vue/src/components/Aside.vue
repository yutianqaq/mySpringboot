<template>
  <el-menu :default-openeds="['1', '3']"
            router
           route
           @select="handleSelect"
  >
    <div style="height: 60px; line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; right: 5px">
      <b style="color: black">后台管理系统</b>
    </div>
    <el-menu-item index="/">
      <template slot="title"><i class="el-icon-house"></i>主页</template>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title"><i class="el-icon-setting"></i>系统管理</template>
        <el-menu-item index="/user"><i class="el-icon-user"></i>用户管理</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  data() {
    return {
      path1: ''
    }
  },
  methods: {
    handleSelect(index) {
      console.log(index)
      console.log(this.$route)
    }
  }
}

</script>

<style scoped>

</style>