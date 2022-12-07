<template>
  <div style="height: 100%">
    <el-container style="height: 100%; ">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 100%">
        <Aside/>
      </el-aside>

      <el-container>

        <el-header style="border-bottom: 1px solid #ccc;">
            <div style="text-align: right">
              <Header/>
            </div>
        </el-header>

        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'HomeView',
  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
    }
  },
  components: {
    Aside,
    Header
  }

}
</script>
