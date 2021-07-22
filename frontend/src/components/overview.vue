<template>
  <el-row>
    <el-col :span="8">
      <div class="img">
        <img :src=item.url>
      </div>
    </el-col>
    <el-col :span="16">
      <ul>
        <li>pid: {{ item.pid }}</li>
        <li>画师: {{ item.illustrator }}</li>
        <li>标题: {{ item.title }}</li>
        <li>简介: {{ item.description }}</li>
      </ul>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "overview",
  props: {
    item: {
      pid: String,
      illustrator: String,
      title: String,
      description: String,
      url: String
    }
  }
}
</script>

<style scoped>
.img {
  width: 100%;
  overflow: hidden;
}
.img img{
  width: 100%;
}
</style>