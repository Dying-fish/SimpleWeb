<template>
  <div class="carousel">
    <el-carousel :interval="5000" arrow="hover" height="500px">
      <el-carousel-item v-for="(img,index) in imgList" :key="index">
        <img :src="img.url" width="100%">
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: "carousel",
  data(){
    return{
      imgList:[
        {
          url:require('../assets/background/bg1.jpg')
        },
        {
          url:require('../assets/background/bg2.jpg')
        },
        {
          url:require('../assets/background/bg3.jpg')
        },
        {
          url:require('../assets/background/bg4.jpg')
        },
        {
          url:require('../assets/background/bg5.jpg')
        }
      ]
    }
  }
}
</script>

<style scoped>
.carousel {
  margin: 0 10%;
}
.el-carousel__item {
  height: 100%;
  background-size:100% auto;
  background-repeat:no-repeat;
}
.el-carousel__item:nth-child(1) {
  background-image: url("../assets/background/bg1.jpg");
}

.el-carousel__item:nth-child(2) {
  background-image: url("../assets/background/bg2.jpg");
}
.el-carousel__item:nth-child(3) {
  background-image: url("../assets/background/bg3.jpg");
}
.el-carousel__item:nth-child(4) {
  background-image: url("../assets/background/bg4.jpg");
}
.el-carousel__item:nth-child(5) {
  background-image: url("../assets/background/bg5.jpg");
}
</style>