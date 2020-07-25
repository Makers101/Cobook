<template>
  <div class="mt-3">
    <h5 class="text-left font-weight-bold">Club</h5>
    <div class="club-list my-2 row">
      <div 
        class="col-sm-4 col-12 p-3"
        v-for="club in clubs"
        :key="`club_${club.id}`">
        <div class="card">
          <div class="card-head">
            <img
              class="card-img-top to-detail"
              :src="club.image"
              :alt="club.name"
              @click="selectClub(club.id)"
            >
            <span class="badge mb-0 club-open" v-if="club.open">모집중</span>
          </div>        
          <div class="card-body">
            <div class="d-flex justify-content-between align-items-center mb-3">
              <h4 class="card-title font-weight-bold mb-0 club-name to-detail" @click="selectClub(club.id)">{{ club.name }}</h4>
              <small class="color-green font-weight-bold club-followers">{{ club.followers }} FOLLOW</small>
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
                <small class="color-black">주로 <span class="color-black font-weight-bold">{{ club.residence }}</span>에서 만남 :)</small>
              </p>
              <div class="d-flex">
                <p class="card-text mb-0 mr-2">
                  <small class="color-black"><i class="fas fa-users"></i> {{ club.participant_num }}</small>
                </p>
                <!-- <p class="card-text mb-0" v-if="club.open">
                  <small class="text-danger font-weight-bold">모집중</small>
                </p> -->
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <h5 class="text-left font-weight-bold">Reading & Meetup</h5>
  </div>
</template>

<script>
const sample_clubs = [
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
        name: '문학'
      },
      {
        id: 3,
        name: '예술'
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

]

import router from '@/router'
export default {
  name: 'ProfileClub',
  data() {
    return {
      popular_filter: false,
      open_filter: false,
      genre_filter: new Set(),
      
      clubs: sample_clubs,
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
    selectClub(club_id) {
      router.push({ name: 'ClubDetail', params: { club_id: club_id }})
    },
    filterClubs() {
      let new_clubs = []
            
      if (this.open_filter) {
        sample_clubs.forEach(club => {
          if (club.open) {
            new_clubs.push(club)
          }
        });
      } else {
        new_clubs = sample_clubs
      }

      console.log(new_clubs)

      if (this.genre_filter.size !== 0) {
        console.log('여기1')
        let new_clubs2 = new Set()
        new_clubs.forEach(club => {
          let temp = 0
          club.genres.forEach(genre => {
            if (this.genre_filter.has(genre.name)) {
              temp = temp + 1
            }
          })
          console.log(club.genres.length)
          console.log(temp)
          if (club.genres.length === temp) {
            new_clubs2.add(club)
          }
        })
        this.clubs = new_clubs2
      } else {
        console.log('여기2')
        this.clubs = new_clubs
      }
    },
    selectFilter(filter) {
      if (filter === 'popular') {
        this.popular_filter = !this.popular_filter
      } else if (filter === 'open') {
        this.open_filter = !this.open_filter
      } else {
        if (!this.genre_filter.has(filter)) {
          this.genre_filter.add(filter)
        } else {
          this.genre_filter.delete(filter)
        }
      }
      this.$forceUpdate();
      this.filterClubs()
    }
  }
}
</script>

<style scoped>
  .badge-genre {
    background-color: #88A498;
    color: #F8F8F8;
    padding: 6px;
  }

  .club-name {
    overflow: hidden;
    white-space: normal;
    word-wrap: break-word;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 1; 
    -webkit-box-orient: vertical;
    text-align: start;
  }

  .club-followers {
    white-space: nowrap;
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

  .card-head {
    position: relative;
  }

  .club-open {
    /* background-color: #b68145; */
    /* background-color: #907a62; */
    background-color: rgba(221, 118, 0, 0.8); 
    color: #F8F8F8;
    text-align: center;
    position: absolute;
    top: 11%;
    left: 89%;
    transform: translate( -50%, -50% );
    padding: 6px;
  }

  .to-detail:hover {
    cursor: pointer;
    color: #88A498
  }
</style>