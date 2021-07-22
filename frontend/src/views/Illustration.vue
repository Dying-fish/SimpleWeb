<template>
  <el-container>
    <el-header>
      <navigation></navigation>
    </el-header>
    <el-main>
      <template v-for="item in items">
        <overview :item = item></overview>
      </template>
    </el-main>
    <el-footer>
    </el-footer>
  </el-container>
</template>

<script>
import navigation from "@/components/navigation";
import Overview from "../components/overview";
export default {
  name: "Illustration",
  components: {
    Overview,
    navigation,
  },
  data() {
    return {
      items : []
    }
  },
  mounted() {
    this.$axios.get('/showAllPic')
        .then(resp => {
          if (resp.status === 200) {
            this.items = resp.data
          } else {
            console.log("Error")
          }
        })
  }
}
</script>

<style scoped>
img {
  width: 250px;
  height: auto;
}
</style>