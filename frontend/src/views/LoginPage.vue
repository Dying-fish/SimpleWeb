<template>
  <el-container>
    <el-header>
      <navigation/>
    </el-header>
    <el-main>
      <div id="loginForm">
        <h1>登录</h1>
        <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" class="demo-ruleForm">
          <el-form-item prop="account" :error="errorMsg">
            <el-input type="text" v-model="loginForm.account" autocomplete="off" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item prop="pass" :error="errorMsg">
            <el-input type="password" v-model="loginForm.pass" autocomplete="off" placeholder="密码"></el-input>
          </el-form-item>
          <p><router-link to="/home" id="forgetPwd">忘记密码？</router-link></p>
          <el-form-item>
            <el-row :gutter="20">
              <el-col :span="12"><el-button type="primary" @click="submitForm('loginForm')">提交</el-button></el-col>
              <el-col :span="12"><el-button @click="signup()">注册</el-button></el-col>
            </el-row>
          </el-form-item>
        </el-form>
      </div>


    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>

<script>
import navigation from "@/components/navigation";

export default {
  name: 'LoginPage',
  components: {
    navigation
  },
  data() {
    return {
      errorMsg: '',
      loginForm: {
        account: '',
        pass: '',
      },
      rules: {
        account: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        pass: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.errorMsg = ''
          this.$axios.post(
              '/login',
              {
                username: this.loginForm.account,
                password: this.loginForm.pass,
              }
          ).then(resp => {
            if (resp.status === 200) {
              if(resp.data.code === 0) {
                this.$store.commit('LOGIN', resp.data.token);
                this.$message.success({
                  message: '登陆成功'
                })
                this.$router.push('/home');
              }
              if (resp.data.code === -1) {
                this.errorMsg = '用户名或密码错误'
              }
            } else {
              console.log("Login Error")
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    signup() {
      this.$router.push('/signup');
    }
  }
}

</script>

<style scoped>
.el-header {
  padding: 0;
}
.el-main {
  padding: 0;
}
#loginForm {
  height: 600px;
  background-image: url("../assets/background/login.png");
  background-size:100% auto;
  background-repeat:no-repeat;
  opacity: 85%;
  padding-top: 50px;
  text-align: center;
}
.el-form {
  margin: auto;
  width: 45%;
  max-width: 320px;
}
p {
  margin-top: -12px;
  text-align: right;
  font-size: 12px;
}
.el-button {
  width: 100%;
}
h1 {
  font-size: 28px;
}
#forgetPwd {
  text-decoration: none;
  color: powderblue;
}
#forgetPwd:hover {
  color: darkblue;
}
</style>