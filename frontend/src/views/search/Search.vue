<template>
  <div>
    <div class="col-3 text-left mt-2 search-bar">
      <h4>검색 결과:</h4>
      <p>000</p>
      <hr>
      <h5 class="font-weight-bold">필터</h5>
      <div class="filter d-flex p-2">
        <div class="bg-beige mr-2" style="border-radius:50%">
          <i class="fas fa-user p-2"></i>
        </div>
        <p class="m-0">유저</p>
      </div>
      <div class="filter d-flex  p-2">
        <div class="bg-beige mr-2" style="border-radius:50%">
          <i class="fas fa-comment-dots p-2"></i>
        </div>
        <p class="m-0">리뷰</p>
      </div>
      <div class="filter d-flex p-2">
        <div class="bg-beige mr-2" style="border-radius:50%">
          <i class="fas fa-book-open p-2"></i>
        </div>
        <p class="m-0">도서</p>
      </div>
      <div class="filter d-flex p-2">
        <div class="bg-beige mr-2" style="border-radius:50%">
           <i class="fas fa-users p-2"></i>
        </div>
        <p class="m-0">클럽</p>
      </div>
      <div class="filter d-flex p-2">
        <div class="bg-beige mr-2" style="border-radius:50%;">
          <span class="mdi mdi-calendar-star p-2" width="50px"></span>
        </div>
        <p class="m-0">원데이 이벤트</p>
      </div>
    </div>
    <router-view class="col-0">

    </router-view>
  </div>
</template>

<script>
export default {
  name: 'Search'
}
</script>

<style scoped>
.search-bar {
  height: 90vh;
  border-right: 1px solid rgb(0, 0, 0, 0.2);
  box-shadow: 5px 5px 10px rgb(0, 0, 0, 0.2)
}

.filter:hover {
  background-color: #88A498;
}
</style>