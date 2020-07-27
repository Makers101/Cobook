<template>
  <div class="offset-2 col-8 mt-3">
    <div class="post mb-5">
      <div class="post-header d-flex justify-content-between mb-2">
        <div>
          <span class="rounded-circle"><img class="img-fluid feed-profile-img" src="@/assets/anonymous user.png" alt="유저 프로필 사진"></span>
          clubname1
          <span class="badge bg-green">Club</span>
        </div>
        <div class="color-beige small-text">
          <i class="fas fa-heart mr-2"></i><small class="mr-3">609</small>
          <i class="fas fa-comments mr-2"></i><small class="mr-3">120 </small>
          <i class="fas fa-bookmark"></i>
        </div>
      </div>
      <div class="post-content">
        <div class="row bg-light-beige">
          <div class="col-3 my-5">
            <img style="height: 20vh;" src="@/assets/미움받을 용기.png" alt="책 이미지">
          </div>
          <div class="col-9 my-5 text-left">
            <h5 class="font-weight-bold">미움받을 용기</h5>
            <p>- 고가 후미타케, 기시미 이치로 -</p>
            <p>출판사</p>
            <p>평점</p>
          </div>
        </div>
        <div class="row">
          <div class="col-12 mt-3">
            <div class="w-100 color-black">
              <div class="large-text text-left"><i class="fas fa-quote-left"></i></div>
              <div class="mr-5 mb-2">한줄 평은 이렇습니다.</div>
              <div class="ml-5">두줄 평은 이렇습니다.</div>
              <div class="large-text text-right"><i class="fas fa-quote-right"></i></div>
            </div>
          </div>
          <div class="col-12 mt-3">
            <div class="w-100 color-black">
              <p class="text-left">Lorem ipsum dolor sit amet consectetur adipisicing elit. Quam voluptatibus similique, odio, impedit unde consequatur voluptatem reiciendis nam accusamus quos voluptates illum dignissimos natus praesentium est ut aut qui exercitationem! Lorem, ipsum dolor sit amet consectetur adipisicing elit. Sit, obcaecati iusto. Aliquam repellendus, voluptatum possimus natus, sequi iure, nam laudantium repellat officiis voluptas non quia officia culpa veritatis consequatur quae!</p>
            </div>
          </div>
        </div>
      </div>
      <div class="post-footer text-left mt-2">
        <span class="badge bg-green rounded-pill px-3 py-2 mr-2">태그1</span>
        <span class="badge bg-green rounded-pill px-3 py-2">태그2</span>
      </div>
    </div>

    <div class="comment mb-5">
      <h5 class="text-left row">댓글</h5>
      <div class="input-group row">
        <textarea @keyup.enter="saveCreateComment" v-model="commentCreateData.content" class="col-11" type="content" placeholder="댓글을 작성하세요 :)" rows="2" ></textarea>
        <button class="input-group-append btn btn-green col-1" @click="saveCreateComment">작성</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PostDetail',
  data() {
    return {
      postId: this.$route.params['postId'],
      commentCreateData: {
        content: null
      }
    }
  }


}
</script>

<style>
.feed-profile-img {
  height: 25px;
}

.small-text {
  font-size: 0.8rem;
}

.large-text {
  font-size: 1.3rem;
}

.myclub {
  position: fixed;
  top: 200px;
  left: 1%;
  width: 14%;
}

.recommend {
  position: fixed;
  top: 200px;
  right: 1%;
  width: 14%;
}
</style>