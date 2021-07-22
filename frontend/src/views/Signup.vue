<template>
  <el-container>
    <el-header>
      <navigation/>
    </el-header>
    <el-main>
      <div id="signupForm">
        <div class="submit" ref="one">
          <h1>注册</h1>
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item prop="account" :error="errorMsg1">
              <el-input type="text" v-model="ruleForm.account" autocomplete="off" placeholder="用户名" ></el-input>
            </el-form-item>
            <el-form-item prop="email" :error="errorMsg2">
              <el-input type="email" v-model="ruleForm.email" autocomplete="off" placeholder="邮箱"></el-input>
            </el-form-item>
            <el-form-item prop="pwd">
              <el-input type="password" v-model="ruleForm.pwd" autocomplete="off" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item prop="cPwd">
              <el-input type="password" v-model="ruleForm.cPwd" autocomplete="off" placeholder="确认密码"></el-input>
            </el-form-item>
            <p class="login"><router-link to="/login" id="login">已有账号，直接登录</router-link></p>
            <el-form-item>
              <el-button type="primary" @click="getCaptcha('ruleForm')">邮箱验证</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="submit" ref="two">
          <h1>验证邮箱</h1>
          <p>验证码已发往{{ruleForm.email}}</p>
          <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item :error="errorMsg3">
              <el-input type="text" v-model="ruleForm.code" autocomplete="off" placeholder="请输入验证码" ></el-input>
            </el-form-item>
            <el-form-item>
              <el-row :gutter="20">
                <el-col :span="12"><el-button type="primary" @click="register('ruleForm')">提交</el-button></el-col>
                <el-col :span="12"><el-button @click="getCaptcha('ruleForm')">重新发送</el-button></el-col>
              </el-row>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>

<script>
import navigation from "@/components/navigation";
export default {
  name: 'Signup',
  components: {
    navigation
  },
  mounted() {
    this.$refs.two.style.display = 'none';
  },
  data() {
    let validatePwd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.cPwd !== '') {
          this.$refs.ruleForm.validateField('cPwd');
        }
        callback();
      }
    };
    let validateCPwd = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pwd) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      code : '',
      errorMsg1 : '',
      errorMsg2 : '',
      errorMsg3 : '',
      ruleForm: {
        account: '',
        email:'',
        pwd: '',
        cPwd: '',
      },
      rules: {
        account: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {
            pattern: /^\w{6,32}$/,
            message: '用户名应由6-32位的英文字母，数字与下划线组成',
            trigger: 'blur'
          }
        ],
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {
            pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
            message: '邮箱格式错误',
            trigger: 'blur'
          }
        ],
        pwd: [
          { validator: validatePwd, trigger: 'blur' },
          {
            max: 32,
            min: 6,
            message: '密码长度应为6-32位',
            trigger: 'blur'
          },
          {
            pattern: /^(?![\d]+$)(?![a-zA-Z]+$)(?![-_]+$)[\da-zA-Z_-]*$/,
            message: '密码应至少包含英文字⺟，数字或-_中的两种',
            trigger: 'blur'
          }
        ],
        cPwd: [
          { validator: validateCPwd, trigger: 'blur' }
        ],
      }
    };
  },
  methods: {
    getCaptcha(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.errorMsg1 = ''
          this.errorMsg2 = ''
          this.$axios.post(
              '/getCaptcha',
              {
                username: this.ruleForm.account,
                email: this.ruleForm.email,
                password: this.ruleForm.pwd,
              }
          ).then(resp => {
            if (resp.status === 200) {
              let arr = resp.data.code;
              if(arr === 0) {
                this.$refs.one.style.display = 'none';
                this.$refs.two.style.display = '';
              } else {
                if (arr.find(item => item === -3)) {
                  this.errorMsg1 = "用户名已被注册"
                }
                if (arr.find(item => item === -4)) {
                  this.errorMsg2  = "邮箱已被使用"
                }
              }
            } else {
              console.log("Error")
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    register(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          self.errorMsg3 = ''
          this.$axios.post(
              '/register',
              {
                username: this.ruleForm.account,
                email: this.ruleForm.email,
                password: this.ruleForm.pwd,
                code: this.ruleForm.code
              }
          ).then(resp => {
            if (resp.status === 200) {
              let arr = resp.data.code;
              if(arr === 0) {
                this.$message.success({
                  message: '注册成功'
                })
                this.$router.push('/login');
              } else if (arr === -1) {
                this.errorMsg3 = '用户名或邮箱已被注册,请重新注册'
              } else if(arr === -2) {
                this.errorMsg3 = '验证码错误'
              }
            } else {
              console.log("Error")
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      })
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
.submit_two {
  display: none;
}
#signupForm {
  height: 720px;
  background-image: url("../assets/background/signup.png");
  background-size:100% auto;
  background-repeat:no-repeat;
  opacity: 85%;
  padding-top: 50px;
  text-align: center;
}
.submit {
  margin: auto;
  width: 45%;
  max-width: 320px;
}

.el-button {
  width: 100%;
}

h1 {
  font-size: 28px;
}

p.login {
  margin-top: -12px;
  text-align: right;
  font-size: 12px;
}
#login {
  text-decoration: none;
  color: powderblue;
}
#login:hover {
  color: darkblue;
}
</style>