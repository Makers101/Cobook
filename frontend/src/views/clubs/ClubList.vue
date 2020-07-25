<template>
  <div class="row">
    <div class="offset-md-2 col-md-8 col-12">
      <!-- club-banner -->
      <div class="club-banner">
        <img
          class="club-banner-img"
          src="@/assets/club_banner.jpg" 
          alt="">
        <div class="club-banner-text">
          <h3 class="font-weight-bold">Club</h3>
          <p class="mb-0">
            클럽은 정기적으로 함께 책을 읽는 사람들의 모임입니다.
            <br>
            취향이 맞는 클럽에 참여하여 함께 읽는 즐거움을 경험해보세요.
          </p>
        </div>
      </div>
      <!-- club-menubar -->
      <div class="club-menubar my-2 d-flex justify-content-between">
        <div class="club-toggle">
          <button class="btn btn-toggle mx-1">#인기</button>
          <button class="btn btn-toggle mx-1">#모집중</button>
          <button 
            class="btn btn-toggle mx-1"
            v-for="genre in user_genres"
            :key="`user_genre_${genre.id}`"
          >
            #{{ genre.name }}
          </button>
        </div>
        <button class="btn btn-club-create mx-1">클럽 생성</button>
      </div>
      <hr class="my-0">
      <!-- club-list -->
      <div class="club-list my-2 row">
        <div 
          class="col-md-4 col-12 p-3"
          v-for="club in sample_clubs"
          :key="`club_${club.id}`">
          <div class="card">          
            <img
              :src="club.image"
              class="card-img-top"
              :alt="club.name">
            <div class="card-body">
              <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="card-title font-weight-bold mb-0">{{ club.name }}</h4>
                <small class="color-green font-weight-bold">{{ club.followers }} FOLLOW</small>
              </div>
              <p class="card-text text-left club-oneline">{{ club.oneline_description }}</p>
              <div class="d-flex justify-content-start my-3">
                <span
                  class="badge badge-genre mr-2"
                  v-for="genre in club.genres"
                  :key="`club_genre_${genre.id}`">
                  #{{ genre.name }}
                </span>
              </div>
              <div class="d-flex justify-content-between">
                <p class="card-text mb-0">
                  <small class="color-black"><i class="fas fa-map-marker-alt"></i> {{ club.residence }}</small>
                </p>
                <div class="d-flex">
                  <p class="card-text mb-0 mr-2">
                    <small class="color-black"><i class="fas fa-users"></i> {{ club.participant_num }}</small>
                  </p>
                  <p class="card-text mb-0" v-if="club.open">
                    <small class="text-danger font-weight-bold">모집중</small>
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ClubList',
  data() {
    return {
      sample_clubs: [
        {
          id: 1,
          name: 'sample_name_1',
          image: 'http://placehold.jp/300x200.png?text=sample',
          oneline_description: '주로 문학 작품을 읽는 클럽 sample_club_1입니다. 쿄쿄쿄쿄쿄쿜쿄쿄쿄쿄쿄쿄쿄',
          residence: '역삼동',
          participant_num: 3,
          followers: 50,
          genres: [
            {
              id: 1,
              name: '문학',
            }
          ],
          open: true
        },
        {
          id: 2,
          name: 'sample_name_2',
          image: 'http://placehold.jp/300x200.png?text=sample',
          oneline_description: '주로 철학 작품을 읽는 클럽 sample_club_2입니다.',
          residence: '온라인',
          participant_num: 5,
          followers: 10,
          genres: [
            {
              id: 2,
              name: '철학',
            },
          ],
          open: true
        },
        {
          id: 3,
          name: 'sample_name_3',
          image: 'http://placehold.jp/300x200.png?text=sample',
          oneline_description: '같이 읽어봐요 sample_club_3입니다.',
          residence: '온라인',
          participant_num: 1,
          followers: 1,
          genres: [
            {
              id: 1,
              name: '문학',
            },
            {
              id: 2,
              name: '철학',
            },
          ],
          open: true
        },
        {
          id: 4,
          name: 'sample_name_4',
          image: 'http://placehold.jp/300x200.png?text=sample',
          oneline_description: '주로 문학작품을 읽는 클럽 sample_club입니다.',
          residence: '온라인',
          participant_num: 3,
          followers: 50,
          genres: [
            {
              id: 1,
              name: '문학',
            },
            {
              id: 2,
              name: '철학',
            },
          ],
          open: true
        },
        {
          id: 5,
          name: 'sample_name_5',
          image: 'http://placehold.jp/300x200.png?text=sample',
          oneline_description: '주로 문학작품을 읽는 클럽 sample_club입니다.',
          residence: '온라인',
          participant_num: 3,
          followers: 50,
          genres: [
            {
              id: 1,
              name: '문학',
            },
            {
              id: 2,
              name: '철학',
            },
          ],
          open: true
        },
        {
          id: 6,
          name: 'sample_name_6',
          image: 'http://placehold.jp/300x200.png?text=sample',
          oneline_description: '주로 문학작품을 읽는 클럽 sample_club입니다.',
          residence: '온라인',
          participant_num: 3,
          followers: 50,
          genres: [
            {
              id: 1,
              name: '문학',
            },
            {
              id: 2,
              name: '철학',
            },
          ],
          open: true
        }
      ],
      user_genres: [
        {
          id: 1,
          name: '문학',
        },
        {
          id: 2,
          name: '철학',
        },
        {
          id: 3,
          name: '예술',
        }
      ]
    }
  },
  methods: {

  }
}
</script>

<style scoped>
  .club-banner {
    position: relative;
  }

  .club-banner-img {
    width: 100%;
    vertical-align: middle;
    filter: brightness(0.7)
  }
  .club-banner-text {
    color: #F8F8F8;
    text-align: center;
    text-shadow: 2px 2px 2px rgb(100, 100, 100);
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate( -50%, -50% );
  }

  .btn-toggle {
    border-color: #88A498;
    color: #88A498;
  }

  .btn-toggle:hover {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-club-create {
    background-color: #88A498;
    color: #F8F8F8;
  }

  .btn-club-create:hover {
    background-color: #3c755a;
    color: #F8F8F8;
  }

  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .club-oneline {
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2; 
    -webkit-box-orient: vertical;
    text-align: start;
    height: 3rem;
  }
</style>