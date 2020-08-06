<template>
  <div class="custom-container">

    <!-- onedayEvents-banner -->
    <div class="onedayEvents-banner">
      <img
        class="onedayEvents-banner-img"
        src="https://user-images.githubusercontent.com/57381062/89487644-f9188000-d7e0-11ea-8d6d-4fa1283c7393.jpg" 
        alt="onedayEvents-banner-image">
      <div class="onedayEvents-banner-text">
        <h3 class="font-weight-bold">원데이이벤트</h3>
        <p class="mb-0">
          원데이이벤트는 간편하게 만나 함께 책을 읽는 일회성 책 모임입니다.
          <br>
          취향이 맞는 원데이이벤트에 참여하여 함께 읽는 즐거움을 경험해보세요.
        </p>
      </div>
    </div>

    <!-- onedayEvents-menubar -->
    <div class="onedayEvents-menubar my-3 d-flex justify-content-between">
      <div class="onedayEvents-toggle">
        <!-- <button
          class="btn btn-toggle-false mx-1"
          @click="selectFilter('recruit')"
          v-show="filters.recruit_filter"
        >
          #모집중
        </button>
        <button
          class="btn btn-toggle-true mx-1"
          @click="selectFilter('recruit')"
          v-show="!filters.recruit_filter"
        >
          #모집중
        </button> -->

        <span             
          v-for="genre in myaccount.likeGenres"
          :key="`userGenres_${genre.id}`"
        >
          <button 
            class="btn btn btn-toggle-false mx-1"
            @click="selectFilter(genre.id)"
            v-show="filters.genre_filter.has(genre.id)"
          >
            #{{ genre.name }}
          </button>
          <button 
            class="btn btn btn-toggle-true mx-1"
            @click="selectFilter(genre.id)"
            v-show="!filters.genre_filter.has(genre.id)"
          >
            #{{ genre.name }}
          </button>
        </span>
      </div>
      <button class="btn btn-green mx-1" @click="toOnedayEventCreate">원데이이벤트 생성</button>
    </div>
    <hr class="my-0">

    <!-- onedayEvents-list -->
    <div class="onedayEvents-list my-2 row">
      <div 
        class="col-lg-4 col-12 p-3"
        v-for="onedayEvent in filteredOnedayEvents"
        :key="`onedayEvent_${onedayEvent.id}`">

        <div class="card h-100">
          <div class="row no-gutters">
            <div class="col-6 onedayEvent-left">
              <img class="bg-image" :src="onedayEvent.book.bookImg" width="100%">
              <span class="badge mb-0 onedayEvent-closed-true" v-if="onedayEvent.recruit">종료</span>
              <span class="badge mb-0 onedayEvent-closed-false" v-else>예정</span>
            </div>
            <div class="col-6 text-left d-flex flex-column p-2">
              <p class="color-light-black book-title" lt="book">{{ onedayEvent.book.title }}</p>
              <small>{{ onedayEvent.name }}</small>
              <div class="mt-auto">
                <div class="d-flex justify-content-between">
                  <span><small><i class="fas fa-users"></i> {{ onedayEvent.participantCnt}}</small></span>
                  <span><small><i class="fas fa-map-marker-alt"></i> {{ onedayEvent.place }}</small></span>
                </div>
                <span class="onedayEvent-date"><small>{{ onedayEvent.datetime | moment('YYYY-MM-DD HH:mm') }}</small></span>
              </div>
            </div>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router'
import { mapState, mapActions } from 'vuex'
export default {
  name: 'OnedayEventList',
  data() {
    return {
      filters: {
        // recruit_filter: false,
        genre_filter: new Set()
      }
    }
  },
  computed: {
    ...mapState(['myaccount']),
    ...mapState('onedayEventStore', ['onedayEvents', 'filteredOnedayEvents']),
  },
  methods: {
    ...mapActions('onedayEventStore', ['fetchOnedayEvents', 'filterOnedayEvents']),
    selectOnedayEvent(onedayEventId) {
      router.push({ name: 'OnedayEventDetail', params: { onedayEventId: onedayEventId }})
    },
    toOnedayEventCreate() {
      router.push({ name: 'OnedayEventCreate' })
    },
    selectFilter(filter) {
      if (filter === 'recruit') {
        this.filters.recruit_filter = !this.filters.recruit_filter
      } else {
        if (!this.filters.genre_filter.has(filter)) {
          this.filters.genre_filter.add(filter)
        } else {
          this.filters.genre_filter.delete(filter)
        }
      }
      this.$forceUpdate();
      this.filterOnedayEvents(this.filters);
    }
  },
  created() {
    this.fetchOnedayEvents()
  }
}
</script>

<style scoped>
  .onedayEvents-banner {
    position: relative;
  }

  .onedayEvents-banner-img {
    width: 100%;
    height: 200px;
    vertical-align: middle;
    filter: brightness(0.7)
  }

  .onedayEvents-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }

  .btn-toggle-true {
    border-color: #88A498;
    color: #88A498;
  }

  .btn-toggle-false {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .onedayEvent-left {
    position: relative;
  }

  .onedayEvent-closed-false {
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 9%;
    left: 18%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .onedayEvent-closed-true {
    background-color: #707070; 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 9%;
    left: 18%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .book-title {
    word-break: keep-all;
  }

</style>