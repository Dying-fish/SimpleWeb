<template>
  <el-container>
    <el-header>
      <navigation></navigation>
    </el-header>
    <el-main>
      <h2>欢迎</h2>
      <carousel></carousel>
    </el-main>
    <el-footer>
    </el-footer>
  </el-container>
</template>

<script>
import navigation from "@/components/navigation";
import Carousel from "../components/carousel";
export default {
  name: 'Home',
  components: {
    Carousel,
    navigation,
  }
}
</script>

<style scoped>

</style>