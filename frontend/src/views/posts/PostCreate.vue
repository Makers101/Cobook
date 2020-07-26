<template>
  <div class="row">
    <div class="offset-2 col-8">
      <h2 class="font-weight-extrabold">게시글 작성</h2>
      <div class="row">
        <div class="search-bar col-3">
          <input v-model="inputValue" @keypress.enter="fetchVideos" class="w-100 customcard" placeholder="책을 검색하세요." autofocus>
          <hr>
          <!-- <div @click="setVideoPath(video)" class="select-video d-flex border p-2" v-for="video in videos" :key="video.etag"> -->
          <div class="row">
            <div class="col-4 book-img">
              <img class="img-fluid" src="@/assets/미움받을 용기.png" alt="">
            </div>
            <div class="col-8">
              <p class="font-weight-bold">미움 받을 용기</p>
            </div>
          </div>
          <!-- </div> -->
        </div>
        <div class="col-9">
          <div class="post mb-5">
            <div class="post-content bg-light-beige row">
              <div class="col-4 my-5">
                <img style="height: 30vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
              </div>
              <div class="col-8 d-flex align-items-center">
                <div class="w-100 color-black">
                  <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
                  <input
                    class="content-input mb-2 bg-light-beige"
                    :class="{ 'mr-5' : !oneline }"
                    @keydown.enter="makeTwoline"
                    type="text" 
                    placeholder="한줄 평을 입력해주세요."
                  >
                  <input
                    class="content-input ml-5 bg-light-beige" 
                    :class="{ 'd-none' : oneline, active : oneline}"
                    type="text" 
                    placeholder=""
                  >
                  <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
                </div>
              </div>
            </div>
            <div class="post-footer text-left mt-2">
              <span class="badge bg-green rounded-pill px-3 py-2 mr-2">태그1</span>
              <span class="badge bg-green rounded-pill px-3 py-2">태그2</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PostCreate',
  data() {
    return {
      oneline: true
    }
  },
  methods: {
    makeTwoline() {
      this.oneline = false
    }
  }


}
</script>

<style scoped>
.customcard {
  position: relative;
  display: flex;
  min-width: 0;
  word-wrap: break-word;
  background-color: #fff;
  background-clip: border-box;
  border: 1px solid rgba(0,0,0,.125);
  border-radius: .25rem;
  padding: 0.5rem;
}

.feed-profile-img {
  height: 25px;
}

.small-text {
  font-size: 0.8rem;
}

.large-text {
  font-size: 1.3rem;
}

.content-input {
  border: none;
}

.content-input:focus {
  outline: none;
}

</style>