<template>
  <div class="background">
    <div class="container p-3 mt-5 bg-light-ivory login-form">
      <h3 class="login-title">
        <strong>로그인</strong>
      </h3>

      <div class="input-with-label">
        <input
          v-model="loginData.email"
          v-bind:class="{error : error.email, complete:!error.email&&loginData.email.length!==0}"
          class="inputs"
          id="email"
          placeholder="이메일"
          type="text"
          autocapitalize="none"
          autocorrect="none"
          style="text-transform:lowercase"
          required
        />
        <label for="email"></label>
        <div class="error-text ml-3" v-if="error.email">{{error.email}}</div>
      </div>
      <div class="input-with-label">
        <input
          v-model="loginData.password"
          v-bind:class="{error : error.password, complete:!error.password&&loginData.password.length!==0}"
          class="inputs"
          id="password"
          type="password"
          placeholder="비밀번호를 입력하세요."
          required
          @keyup.enter="clickLogin"
        />
        <label for="password"></label>
        <div class="error-text ml-3" v-if="error.password">{{error.password}}</div>
      </div>
      <p class="my-3">
        <span class="items" @click="clickSignup">회원가입하기</span>ㆍ
        <span class="items" @click="clickPasswordFind">비밀번호 찾기</span>
      </p>
      <div class="buttons mt-3">
        <button class="btn login-button" :class="{disabled: !isSubmit}" @click="clickLogin">로그인하기</button>
      </div>

      <hr class="divide" />

      <div class="buttons d-flex justify-content-center">
        <button
          class="btn kakao d-flex align-items-center justify-content-center"
          @click="kakaoLogin"
        >
          <i class="xi-2x xi-kakaotalk text-dark"></i>
          <span class="justify-content-center">카카오 아이디 로그인</span>
        </button>
      </div>
      <div class="buttons mt-2 d-flex justify-content-center mx-auto px-0">
        <button
          class="btn google d-flex align-items-center justify-content-center"
          @click="handleClickSignIn"
        >
          <!-- <i class="fab fa-google-plus-g"></i> -->
          <img
            class="google-logo"
            src="https://user-images.githubusercontent.com/57381062/88908677-291dcb80-d295-11ea-8a24-2a96837dd714.png"
            width="32px"
            height="32px"
          />
          <span class="justify-content-center">구글 아이디 로그인</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations } from "vuex";
import Swal from "sweetalert2";
import axios from "axios";
import SERVER from "@/api/api";

export default {
  name: "Login",
  data() {
    return {
      loginData: {
        email: "",
        password: "",
      },
      error: {
        email: false,
        password: false,
      },
      isSubmit: false,
    };
  },
  created() {
    this.component = this;
  },
  watch: {
    loginData: {
      deep: true,
      handler() {
        this.checkEmailForm();
        this.checkPasswordForm();
      },
    },
  },
  methods: {
    ...mapMutations(["SET_TOKEN"]),
    ...mapActions("accountStore", ["login"]),
    async handleClickSignIn() {
      const googleUser = await this.$gAuth.signIn();
      const profile = googleUser.getBasicProfile();
      const userInfo = {
        nickName: profile.getName(),
        email: profile.getEmail(),
        platformType: "GOOGLE",
      };
      axios
        .post(SERVER.URL + SERVER.ROUTES.social, userInfo)
        .then((res) => {
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });
          this.SET_TOKEN(res.data);
          Toast.fire({
            icon: "success",
            title: "로그인에 성공하였습니다.",
          });
          this.$router.push("/");
        })
        .catch((err) => {
          const Toast = Swal.mixin({
            toast: true,
            position: "top-end",
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
            onOpen: (toast) => {
              toast.addEventListener("mouseenter", Swal.stopTimer);
              toast.addEventListener("mouseleave", Swal.resumeTimer);
            },
          });
          Toast.fire({
            icon: "error",
            title: err.response.data.message,
          });
          this.$router.push("/");
        });
    },
    clickLogin() {
      if (this.isSubmit) {
        this.login(this.loginData);
      }
    },
    kakaoLogin() {
      window.Kakao.Auth.login({
        scope: "profile, account_email",
        success: this.GetMe,
      });
    },
    GetMe() {
      window.Kakao.API.request({
        url: "/v2/user/me",
        success: (res) => {
          const kakao_account = res.kakao_account;
          const userInfo = {
            nickName: kakao_account.profile.nickname,
            email: kakao_account.email,
            platformType: "KAKAO",
          };
          if (userInfo.email === undefined) {
            this.$router.push({
              name: "SignupKakao",
              params: {
                nickName: userInfo.nickName,
                platformType: userInfo.platformType,
              },
            });
          } else {
            axios
              .post(SERVER.URL + SERVER.ROUTES.social, userInfo)
              .then((res) => {
                this.SET_TOKEN(res.data);
                const Toast = Swal.mixin({
                  toast: true,
                  position: "top-end",
                  showConfirmButton: false,
                  timer: 2000,
                  timerProgressBar: true,
                  onOpen: (toast) => {
                    toast.addEventListener("mouseenter", Swal.stopTimer);
                    toast.addEventListener("mouseleave", Swal.resumeTimer);
                  },
                });
                Toast.fire({
                  icon: "success",
                  title: "로그인에 성공하였습니다.",
                });
                this.$router.push("/");
              })
              .catch((err) => {
                const Toast = Swal.mixin({
                  toast: true,
                  position: "top-end",
                  showConfirmButton: false,
                  timer: 2000,
                  timerProgressBar: true,
                  onOpen: (toast) => {
                    toast.addEventListener("mouseenter", Swal.stopTimer);
                    toast.addEventListener("mouseleave", Swal.resumeTimer);
                  },
                });
                Toast.fire({
                  icon: "error",
                  title: err.response.data.message,
                });
                this.$router.push("/");
              });
          }
        },
      });
    },
    checkEmailForm() {
      if (
        this.loginData.email.length > 0 &&
        !this.validEmail(this.loginData.email)
      ) {
        this.error.email = "올바른 이메일 형식이 아니에요";
      } else this.error.email = false;
    },
    validEmail(email) {
      // eslint-disable-next-line
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    checkPasswordForm() {
      if (
        this.loginData.password.length > 0 &&
        this.loginData.password.length < 8
      ) {
        this.error.password = "비밀번호가 너무 짧아요";
      } else if (
        this.loginData.password.length >= 8 &&
        !this.validPassword(this.loginData.password)
      ) {
        this.error.password = "영문, 숫자 포함 8 자리 이상이어야 해요.";
      } else this.error.password = false;
      // 버튼 활성화
      if (
        this.loginData.password.length > 0 &&
        this.loginData.email.length > 0
      ) {
        let isSubmit = true;
        Object.values(this.error).map((v) => {
          if (v) isSubmit = false;
        });
        this.isSubmit = isSubmit;
      }
    },
    validPassword(password) {
      var va = /^(?=.*\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}$/;
      return va.test(password);
    },
    clickSignup() {
      this.$router.push({ name: "Signup" });
    },
    clickPasswordFind() {
      this.$router.push({ name: "PasswordFind" });
    },
  },
};
</script>

<style scoped>
.login-form {
  margin-top: 20vh !important;
  opacity: 0.9;
}

.container {
  width: 30%;
  border-radius: 25px;
}

h3 {
  color: #88a498;
  font-weight: 900;
}

.inputs {
  border-style: none;
  border-bottom: 1px solid #88a498;
  background-color: transparent;
  width: 80%;
  padding: 10px;
  padding-left: 10px;
  padding-right: 10px;
  margin-top: 20px;
}

.login-button {
  background-color: #88a498;
  color: #f8f8f8;
  width: 80%;
}

.divide {
  width: 10%;
  border-top: 1px solid #88a498;
  margin-left: auto;
  margin-right: auto;
}

.kakao {
  background-color: #ffe812;
  border-radius: 5px;
  width: 80%;
}

.google {
  background-color: #ffffff;
  border-radius: 5px;
  width: 80% !important;
}

.inputs:focus {
  border-style: none;
  border-bottom: 2px solid #d6cbbd;
  outline-style: none;
}

input[type="password"] {
  font-family: sans-serif;
}

.error,
.error:focus {
  border-bottom: 2px solid rgb(250, 25, 59, 0.7);
}

.error-text {
  color: rgb(250, 25, 59, 0.7);
  text-align: left;
  padding-left: 30px;
}

.login-button:hover {
  background-color: #3c755a;
  color: #f8f8f8;
}

.disabled,
.disabled:hover {
  background-color: rgb(136, 154, 152, 0.25);
  color: #f8f8f8;
  cursor: inherit;
}

.items:hover {
  cursor: pointer;
  color: #d6cbbd;
}

.background {
  background-image: url("https://user-images.githubusercontent.com/25967949/90751489-27ce4480-e311-11ea-93aa-2ab9d1f41b4e.png");
  position: absolute;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  background-repeat: repeat;
}




.formatting {
  opacity: 0.9;
}
</style>