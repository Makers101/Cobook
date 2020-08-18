<template>
  <div class="background">
    <div class="container p-3 mt-5 bg-light-ivory signup-form">
      <h3>회원가입</h3>

      <div class="input-with-label">
        <input 
          v-model="signupData.nickName"
          v-bind:class="{error: error.nickName, complete:!error.nickName&&signupData.nickName.length!==0}"
          class="inputs"
          id="nickName"
          placeholder="닉네임" 
          type="text" 
          autocapitalize="none"
          autocorrect="none"
          style="text-transform:lowercase"
        />
        <label for="nickName"></label>
        <div class="error-text ml-3" v-if="error.nickName">{{error.nickName}}</div>
      </div>

      <div class="input-with-label">
        <input 
          v-model="signupData.email" 
          v-bind:class="{error : error.email, complete:!error.email&&signupData.email.length!==0}"
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
          v-model="signupData.password" 
          
          v-bind:class="{error : error.password, complete:!error.password&&signupData.password.length!==0}"
          class="inputs"
          id="password" 
          type="password"
          placeholder="비밀번호를 입력하세요." 
          required
        />
        <label for="password"></label>
        <div class="error-text ml-3" v-if="error.password">{{error.password}}</div>
      </div>

      <div class="input-with-label">
        <input
          v-model="signupData.passwordConfirm"
          type="password"
          id="password-confirm"
          v-bind:class="{error : error.passwordConfirm, complete:!error.passwordConfirm&&signupData.passwordConfirm.length!==0}"
          placeholder="비밀번호를 다시 입력해주세요."
          class="inputs"
          required
          @keyup.enter="clickSignup"
        />
        <label for="password-confirm"></label>
        <div class="error-text ml-3" v-if="error.passwordConfirm">{{error.passwordConfirm}}</div>
      </div>
      <div class="buttons mt-3">
        <button class="btn signup-button" :class="{disabled: !isSubmit}" @click="clickSignup">가입하기</button>
      </div>
      <p class="my-3">
        <span class="items" @click="toLogin">로그인하기</span>
      </p>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'Signup',
  data() {
    return {
      signupData: {
        email: "",
        password: "",
        passwordConfirm: "",
        nickName: "",
      },
      error: {
        email: false,
        nickName: false,
        password: false,
        passwordConfirm: false,
      },
      isSubmit: false,
    };
  },
  created() {
    this.component = this;
  },
  watch: {
    signupData: {
      deep: true,

      handler() {
        this.checknickNameForm();
        this.checkEmailForm();
        this.checkPasswordForm();
        this.checkPasswordConfirmationForm();
      }
    }
  },
  methods: {
    checknickNameForm() {
      if ( this.signupData.nickName.length > 0) {
        this.error.nickName = false;
      }
      else this.error.nickName="닉네임을 입력해주세요."
    },
    checkEmailForm() {
      if ( this.signupData.email.length > 0 && !this.validEmail(this.signupData.email) ) {
        this.error.email = "올바른 이메일 형식이 아니에요"   
      }
      else this.error.email = false;
    },
    validEmail(email) {
      // eslint-disable-next-line
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    checkPasswordForm() {
      if (this.signupData.password.length > 0 && this.signupData.password.length < 8) {
          this.error.password = "비밀번호가 너무 짧아요"
        } else if ( this.signupData.password.length >= 8 && !this.validPassword(this.signupData.password) ) {
          this.error.password = "영문, 숫자 포함 8 자리 이상이어야 해요.";
        } else this.error.password = false;
    },
    validPassword(password) {
      var va = /^(?=.*\d)(?=.*[a-z])(?=.*[a-zA-Z]).{8,}$/;
      return va.test(password);
    },
    checkPasswordConfirmationForm() {
      if (this.signupData.password.length >= 8 && this.validPassword(this.signupData.password)) {
         if (this.signupData.password !== this.signupData.passwordConfirm )
        this.error.passwordConfirm = "비밀번호가 일치하지 않아요."
      else this.error.passwordConfirm = false;
      }
      
      // 버튼 활성화
      if (this.signupData.nickName.length > 0 && this.signupData.email.length > 0 && this.signupData.password.length > 0 && this.signupData.passwordConfirm.length > 0){
        let isSubmit = true;
        Object.values(this.error).map(v => {
          if (v) isSubmit = false;
        });
        this.isSubmit = isSubmit;
      }
     
    },
    clickSignup() {
      if ( this.isSubmit ){
        this.signup(this.signupData)
      }
    },
    toLogin() {
      this.$router.push({name: "Login"});
    },
    ...mapActions('accountStore', ['signup'])
  }
}
</script>

<style scoped>
.container {
  width: 30%;
  border-radius: 25px;
}

h3 { 
  color: #88A498;
}

.inputs {
  border-style: none;
  border-bottom: 1px solid #88A498;
  background-color: transparent;
  width: 100%;
  padding: 10px;
  padding-left: 20px;
  margin-top: 20px;
}

.signup-button{
  background-color: #88A498;
  color: #F8F8F8;
  width: 70%;
}

.divide {
  width: 10%;
  border-top: 1px solid #88A498;
  margin-left: auto;
  margin-right: auto;
}


.kakao {
  background-color: #ffe812;
  border-radius: 5px;
  width: 70%;
}

.google {
  background-color:  #FFFFFF;
  border-radius: 5px;
  width: 70%;
}

.inputs:focus {
  border-style: none;
  border-bottom: 2px solid #D6CBBD;
  outline-style: none;
}

input[type="password"] {
  font-family:sans-serif;
}
.error, .error:focus {
  border-bottom: 2px solid rgb(250, 25, 59, 0.7); 
}

.error-text {
  color: rgb(250, 25, 59, 0.7);
  text-align: left;
  padding-left: 5px;
}

.signup-button:hover {
  background-color: #3c755a;
  color: #F8F8F8;
}

.disabled, .disabled:hover {
  background-color: rgb(136, 154, 152, 0.25);
  color: #F8F8F8;
  cursor: inherit;
}

.background {
  background-repeat: repeat;
}

.background::after {
  content:"";
  background-image: url('https://user-images.githubusercontent.com/57381062/88908481-de03b880-d294-11ea-8567-9e74079c2a7b.jpg');
  opacity: 0.5;
  top: 0;
  left:0;
  bottom:0;
  right: 0;
  position: absolute;
  z-index: -1;
  width: 100vw;
  height: 100vh;
  filter: brightness(0.7);
}

.signup-form {
  opacity: 0.9;
}

.items:hover {
  cursor: pointer;
  color: #d6cbbd;
}
</style>