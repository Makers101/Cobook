<template>
  <div class="container">
    <div class="page cover" > 
      <div class="back backcover"></div>
      <div class="front frontcover"><p>LAST PAGE</p>  </div>
    </div>
    <div class="page three" @click="handlePage">
      <div class="back"><p>Page 3 back</p></div>
      <div class="front"><p>Page 3</p></div>
    </div>
    <div class="page two" @click="handlePage">
      <div class="back"><p>Page 2 back</p></div>
      <div class="front"><p>Page 2</p></div>
    </div>
    <div class="page one" @click="handlePage">
      <div class="back"><p>Page 1 back</p></div>
      <div class="front"><p>Page 1</p></div>
    </div>
    <div class="page cover" @click="handlePage"> 
      <div class="back backcover">{{selectedBook}}</div>
      <div class="front frontcover" >
        <p >댓글 작성하기</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
'use strict';

const pages= document.querySelectorAll('.page');

let leftZ = 0;
let rightZ = 5;

for (const page of pages) {
  page.addEventListener('click', this.handlePage);
}

export default {
  name: "Book",
  data() {
    return {

    }
  },
  computed: {
    ...mapState('bookStore', ['selectedBook']),
  },
  methods: {
    ...mapActions('bookStore', ['findBook']),
    handlePage(event) {
      const thisPage = event.currentTarget;
      if (!thisPage.classList.contains('animationOpen') && !thisPage.classList.contains('animationClose')){
        rightZ += -1;
        leftZ += 1;
        thisPage.classList.add('animationOpen');
        thisPage.style.zIndex=leftZ;
      } else if (thisPage.classList.contains('animationOpen')){
        thisPage.classList.remove('animationOpen');
        thisPage.classList.add('animationClose');
        leftZ += -1;
        rightZ += +1;
        thisPage.style.zIndex=rightZ;
      }else{
        thisPage.classList.remove('animationClose');
        thisPage.classList.add('animationOpen');
        rightZ += -1;
        leftZ += 1;
        thisPage.classList.add('animationOpen');
        thisPage.style.zIndex=leftZ;
      }
    },
  },
  created() {
    this.findBook(this.$route.params.bookId)
  }
}
</script>

<style scoped>
.body {
  height: 100vh;
  width: 100%;
}
.container {
  height: 600px;
  width: 1000px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); 
}
.page {
  padding: 10px;
  position: absolute;
  left: 500px;
  height: 600px;
  width: 500px;
  background-color: #d6d093;
  /* background-color: ; */
  transform-origin: left;
  transform-style: preserve-3d;
  border-radius: 0 10px 10px 0;
  box-shadow: inset 10px 9px 10px rgba(0 0 0 0.3);
}
.cover{
  background-color: #825918;
}

.front{
  color: black;
  position: absolute;
  top:0;
  height: 600px;
  width: 500px;
  backface-visibility: hidden;
  transform: rotateY(0deg);
}
.back{
  color: black;
  position: absolute;
  top:0;
  height: 600px;
  width: 500px;
  backface-visibility: hidden;
  transform: rotateY(180deg);
}
.backcover{
  background-color: #825918;
}
.frontcover{
  color:#e5a102;
  background-color: #825918;
  text-align: center;
  line-height: 125px;
  font-size: 15px;
}
.animationOpen{
  animation: openPage 0.5s linear forwards;
}
.animationClose{
  animation: closePage 0.5s linear;
}
@keyframes openPage {
  0%{
    transform: perspective(1500px) rotateY(0deg); 
    z-index: 10;
  }
  100%{
    transform: perspective(1500px) rotateY(-180deg);
  }
}
@keyframes closePage {
  0%{
    transform: perspective(1500px) rotateY(-180deg);
    z-index: 10;
  }
  100%{
    transform: perspective(1500px) rotateY(0deg); 
  }
}
</style>