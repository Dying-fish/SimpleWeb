<template>
  <el-row :gutter="20" background-color="#545c64">
    <el-menu
        router
        class="el-menu-demo"
        mode="horizontal"
        text-color="#fff"
        background-color="#545c64"
        active-text-color="#ffd04b">
      <el-menu-item index="/home"><i class="el-icon-s-home"></i>主页</el-menu-item>
      <el-menu-item index="/logs"><i class="el-icon-notebook-2"></i>日志</el-menu-item>
      <el-menu-item index="/illustrations"><i class="el-icon-picture-outline-round"></i> 插画</el-menu-item>
      <el-menu-item class="item-right" index="/"><i class="el-icon-message-solid"></i></el-menu-item>
      <el-submenu index="/user" class="item-right" v-if="ok">
        <template slot="title"><i class="el-icon-user-solid"></i> 用户</template>
        <el-menu-item class="nav-submenu" index="/login"><i class="el-icon-reading"></i> 登录</el-menu-item>
        <el-menu-item class="nav-submenu" index="/signup"><i class="el-icon-film"></i> 注册</el-menu-item>
      </el-submenu>
      <el-submenu index="/user" class="item-right" v-else>
        <template slot="title"><i class="el-icon-user-solid"></i> 用户</template>
        <el-menu-item class="nav-submenu" index="/upload"><i class="el-icon-reading"></i> 上传</el-menu-item>
        <el-menu-item class="nav-submenu" @click="logout()"><i class="el-icon-film"></i> 登出</el-menu-item>
      </el-submenu>
    </el-menu>
  </el-row>
</template>

<script>
export default {
  name: "navigation",
  props: {
    activeIndex: String,
    name: String,
    url: String
  },
  data() {
    if (this.$store.state.token === null) {
      return {
        ok : true
      }
    } else {
      return {
        ok : false
      }
    }
  },
  methods: {
    logout() {
      this.$store.commit("LOGOUT");
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
.el-menu {
  padding: 0 25px;
}
.item-right {
  float: right;
}
.nav-submenu {
  padding-left: 10px;
}
</style>