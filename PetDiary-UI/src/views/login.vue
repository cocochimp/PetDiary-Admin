<template>
  <div class="container">
    <canvas id="draw"  style="position: fixed; width: 100vw; height: 100vh; z-index: 0; display: block">
      当前浏览器不支持Canvas，请更换浏览器后再试
    </canvas>
    <el-row type="flex" class="row-bg" justify="center">
      <el-col :xl="6" :lg="7">
        <div id="top">
          <el-image style="vertical-align: middle;" :src="require('../assets/images/top.png')"></el-image>
          <span>PetDaily宠物日记</span>
        </div>
        <el-image style="padding-bottom: 20px;" :src="require('../assets/images/petHome.png')"></el-image>
      </el-col>
      <el-col :span="1">
        <el-divider direction="vertical"></el-divider>
      </el-col>
      <el-col :xl="6" :lg="7">
        <el-image :src="require('../assets/logo/logo.png')" style="width: 100px; height: 100px;"></el-image>
        <p style="font-size: 14px; font-weight: 400; color: #02a7f0">PetDaily后台管理系统</p>

        <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">

          <el-form-item label="用户名" prop="username" style="width: 380px;">
            <el-input v-model="loginForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密 码" prop="password" style="width: 380px;">
            <el-input v-model="loginForm.password" type="password" @keyup.enter.native="handleLogin"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code" style="width: 380px;">
            <el-input
              v-model="loginForm.code"
              auto-complete="off"
              placeholder="验证码"
              style="width: 63%"
              @keyup.enter.native="handleLogin"
            >
              <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
            </el-input>
            <div class="login-code">
              <img :src="codeUrl" @click="getCode" class="login-code-img"/>
            </div>
          </el-form-item>

          <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>

          <el-form-item size="medium">
            <el-button
              :loading="loading"
              type="primary"
              @click.native.prevent="handleLogin"
            >
              <span v-if="!loading">登 录</span>
              <span v-else>登 录 中...</span>
            </el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>

        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import qs from 'qs';
import Cookies from "js-cookie";
import {decrypt, encrypt} from "@/utils/jsencrypt";
import {getCodeImg} from "@/api/login";
export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: 'admin',
        password: 'admin123',
        rememberMe: false,
        code: '',
        key: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
        ]
      },
      loading: false,
      codeUrl: ""
    };
  },
  created() {
    this.getCode();
    this.getCookie();
  },
  mounted() {
    this.draw();
  },
  methods: {
    draw(){
      let canvas = document.querySelector("#draw")
      let yuan = canvas.getContext("2d");
      let arr = [];
      window.onresize = resizeCanvas;
      function resizeCanvas() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
      }
      resizeCanvas();
      /* 绘制小圆形的方法，x与y是初始位置，r是圆半径 */
      function circle(x, y, r) {
        this.x = x;
        this.y = y;
        this.r = r;
        /* 得一个随机颜色 */
        this.color = `rgba(${Math.floor(Math.random() * 255)},
                          ${Math.floor(Math.random() * 255)},
                          ${Math.floor(Math.random() * 255)}, 0.2)`;
        /* 圆的移动方向，random函数为随机返回一个0.0到1.0的数，x可得随机正负数，y为随机正数 */
        this.xMove = parseInt(Math.random() * 10 - 5);
        this.yMove = parseInt(Math.random() * 10);
        /* 向arr数组末尾添加这个元素 */
        arr.push(this);

        /* 渲染小圆 */
        this.render = function () {
          yuan.beginPath();
          yuan.arc(this.x, this.y, this.r, 0, 2 * 3.14, false);
          yuan.fillStyle = this.color;
          // yuan.stroke();
          yuan.fill();
        };

        /* 更新圆形的方法 */
        this.updated = function () {
          this.x = this.x + this.xMove;
          this.y = this.y + this.yMove;
          /* 半径慢慢减少 */
          this.r = this.r - 0.08;
          /* 当半径小于1清除这个圆 */
          if (this.r < 0) {
            this.remove();
          }
        };
        /* 删除小圆的函数 */
        this.remove = function () {
          for (let i = 0; i < arr.length; i++) {
            if (this == arr[i]) {
              arr.splice(i, 1);
            }
          }
        };
      }
      /* 给画布绑定鼠标经过事件 */
      canvas.addEventListener("mousemove", function (e) {
        /* 传入x，y，r。offsetX距离左侧距离，..， */
        new circle(e.offsetX, e.offsetY, 10);
      });

      /* 定时器渲染小圆，开始动画 ，30毫秒一次 */
      setInterval(function () {

        /* 清屏 */
        yuan.clearRect(0, 0, canvas.width, canvas.height);
        /* 循环数组，给每个小圆更新和渲染 */
        for (let i = 0; i < arr.length; i++) {
          arr[i].render();
          /* 更新 */
          arr[i].updated();
        }
      }, 30);
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('/login?' + qs.stringify(this.loginForm)).then(res => {
            const jwt = res.headers['authorization'];
            this.$store.commit('SET_TOKEN',jwt);
            this.$router.push("/index");
          }).catch(res => {
            // this.getCaptcha();
            this.getCode();
          })
        } else {
          return false;
        }
      });
    },
    resetForm() {
      this.$refs.loginForm.resetFields()
    },
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get('rememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set('rememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove('rememberMe');
          }
          this.$store.dispatch("Login", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
}
</script>

<style scoped>
.container{
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
  background-color: #f1f2f6;
}
.el-row{
  background-color: #f1f2f6;
  display: flex;
  align-items: center;
  text-align: center;
}

.el-row .el-col:nth-child(1){
  flex: 45%;
}
.el-row .el-col:nth-child(2){
  flex: 10%;
}
.el-row .el-col:nth-child(3){
  flex: 45%;
  margin-left: -20px;
}

.el-divider{
  height: 380px;
}
.captchaImg{
  float: left;
  margin-left: 8px;
  border-radius: 4px;
}
#top{
  padding-bottom: 20px;
  text-align: left;
}
#top span{
  font-size: 20px;
  color: rgb(2, 167, 240);
}
.login-code-img {
  height: 38px;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
</style>
