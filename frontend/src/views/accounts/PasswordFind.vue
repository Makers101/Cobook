<template>
  <div class="background">
    <div class="container p-3 mt-5 bg-light-ivory formatting">
      <h3>비밀번호 찾기</h3>
      <h5 class="mt-3">비밀번호를 찾고싶으시다면<br><br>
        가입하실 때 기입하신<br><br>
        이메일을 입력해주세요.</h5>

      <div class="input-with-label mt-3">
        <input 
          v-model="passwordFindData.email" 
          v-bind:class="{error : error.email, complete:!error.email&&passwordFindData.email.length!==0}"
          class="inputs"
          id="email" 
          placeholder="이메일" 
          type="text" 
          autocapitalize="none"
          autocorrect="none"
          style="text-transform:lowercase"
          required
          @click="isSubmit && findPassword(passwordFindData)"
          />
        <label for="email"></label>
        <div class="error-text ml-3" v-if="error.email">{{error.email}}</div>
      </div>
      <div class="buttons mt-5 ">
        <button class="btn done-button" :class="{disabled: !isSubmit}" @click="isSubmit && findPassword(passwordFindData)" >입력완료</button>
      </div>

    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import SERVER from '@/api/api'
import axios from 'axios'
import router from '@/router'
import Swal from 'sweetalert2'

export default {
  name: 'PasswordFind',
  data() {
    return {
      passwordFindData: {
        email: ""
      },
      error: {
        email: false,
      },
      isSubmit: false
    }
  },
  created() {
    this.component = this;
  },
  watch: {
    passwordFindData: {
      deep: true,

      handler() {
        this.checkEmailForm();
      }
    },
  },
  methods: {
    ...mapActions('accountStore', ['findPassword']),
    checkEmailForm() {
      if ( this.passwordFindData.email.length > 0 && !this.validEmail(this.passwordFindData.email) ) {
        this.error.email = "올바른 이메일 형식이 아니에요"   
      }
      else this.error.email = false;

      let isSubmit = true;
      Object.values(this.error).map(v => {
        if (v) isSubmit = false;
      });
      this.isSubmit = isSubmit;
    },
    validEmail(email) {
      // eslint-disable-next-line
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    clickDone() {
      if ( this.isSubmit ){
        this.$router.push({name: 'PasswordFindEmail'})
      }
    },
    sendPasswordEmail(info) {
      let timerInterval
        Swal.fire({
          title: '이메일을 보내는 중입니다.',
          html: '조금만 기다려주세요',
          timer: 4000,
          timerProgressBar: true,
          onBeforeOpen: () => {
            Swal.showLoading()
            timerInterval = setInterval(() => {
              const content = Swal.getContent()
              if (content) {
                const b = content.querySelector('b')
                if (b) {
                  b.textContent = Swal.getTimerLeft()
                }
              }
            }, 100)
          },
          onClose: () => {
            clearInterval(timerInterval)
          }
        })               
      axios.post(SERVER.URL + SERVER.ROUTES.password, info.data, {
        headers: { 'Content-Type': 'application/json' }
      })
        .then (() => {                                    
          router.push({ name: 'PasswordFindEmail', params: {email: this.passwordFindData.email}})
        })
        .catch (() =>{
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: false,
            onOpen: (toast) => {
              toast.addEventListener('mouseenter', Swal.stopTimer)
              toast.addEventListener('mouseleave', Swal.resumeTimer)
              }
           })
           Toast.fire({
            icon: 'error',
            title: "이메일이 존재하지 않습니다."
          })
        })
    },
    findPassword(passwordFindData) {
      const info = {
        data: passwordFindData,
        location: SERVER.ROUTES.password,
        // to: '/'
      }
      this.sendPasswordEmail(info)
    },
  },
}
</script>

<style scoped>
.background {
  background-repeat: repeat;
}

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

.done-button{
  background-color: #88A498;
  color: #F8F8F8;
  width: 70%;
}

.done-button:hover {
  background-color: #3c755a;
  color: #F8F8F8;
}

.disabled, .disabled:hover {
  background-color: rgb(136, 154, 152, 0.25);
  color: #F8F8F8;
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

.formatting {
  opacity: 0.9;
}

</style>