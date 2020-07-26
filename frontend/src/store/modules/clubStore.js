import axios from 'axios'
import SERVER from '@/api/api'

const clubStore = {
    namespaced: true,
    state: {
        clubs: [
            {
              id: 1,
              name: 'sample_name_1',
              clubImg: 'http://placehold.jp/300x200.png?text=sample',
              onelineDescription: '주로 문학 작품을 읽는 클럽 sample_club_1입니다. 쿄쿄쿄쿄쿄쿜쿄쿄쿄쿄쿄쿄쿄',
              residence: '역삼동',
              memberCnt: 3,
              followerCnt: 50,
              genres: [
                {
                  id: 810,
                  name: "한국문학"
                }
              ],
              recruit: true
            },
            {
              id: 2,
              name: 'sample_name_2',
              clubImg: 'http://placehold.jp/300x200.png?text=sample',
              onelineDescription: '주로 철학 작품을 읽는 클럽 sample_club_2입니다.',
              residence: '온라인',
              memberCnt: 5,
              followerCnt: 10,
              genres: [
                {
                  id: 840,
                  name: "영미문학"
                }
              ],
              recruit: true
            },
            {
              id: 3,
              name: 'sample_name_3',
              clubImg: 'http://placehold.jp/300x200.png?text=sample',
              onelineDescription: '같이 읽어봐요 sample_club_3입니다.',
              residence: '온라인',
              memberCnt: 1,
              followerCnt: 1,
              genres: [
                {
                  id: 830,
                  name: "일본문학"
                }
              ],
              recruit: true
            },
            {
              id: 4,
              name: 'sample_name_4',
              clubImg: 'http://placehold.jp/300x200.png?text=sample',
              onelineDescription: '주로 문학작품을 읽는 클럽 sample_club입니다.',
              residence: '온라인',
              memberCnt: 3,
              followerCnt: 50,
              genres: [
                {
                  id: 830,
                  name: "일본문학"
                }
              ],
              recruit: true
            },
        ],
        // clubs: null,
        selectedClub: {
          id: 1,
          name: 'sample_name_1',
          clubImg: 'http://placehold.jp/300x200.png?text=sample',
          onelineDescription: '주로 문학 작품을 읽는 클럽 sample_club_1입니다. 쿄쿄쿄쿄쿄쿜쿄쿄쿄쿄쿄쿄쿄',
          description: '우리 클럽은 블라블라 꽤나 긴 설명 글이 여기에 들어가겠죵 ㅎㅎㅎㅎㅎㅎㅎㅎ쿄쿄쿄쿜쿄쿄',
          residence: '역삼동',
          recruit: true,
          createdAt: '2020-07-25T02:39:08',
          updatedAt: '2020-07-25T02:39:08',
          memberCnt: 3,
          followerCnt: 50,
          genres: [
            {
              id: 810,
              name: '한국문학'
            }
          ],
          members: [
            {
              id: 1,
              userName: '아무개',
              profileImg: 'http://placehold.jp/100x100.png?text=profile'
            }
          ],
          readings: [
            {
              id: 1,
              name: "예시 리딩입니다",
              datetime: "2020-07-24T17:21:41",
              place: "string",
              participantCnt: 1,
              closed: false,
              book: {
                bookId: 57,
                bookTitle: "내 강아지 마음 상담소",
                bookImage: "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5116719%3Ftimestamp%3D20200723135844",
              },
            }
          ],
        },
        userGenres: [
          {
            id: 700,
            name: '언어'
          },
          {
            id: 810,
            name: '한국문학'
          },
          {
            id: 600,
            name: '예술'
          }
        ]
    },
    getters: {
    },
    mutations: {
      SET_CLUBS(state, clubs) {
        state.clubs = clubs
      }
    },
    actions: {
      fetchClubs({ commit }) {
        axios.get(SERVER.URL + SERVER.ROUTES.clubs)
          .then(res => {
            commit('SET_CLUBS', res.data)
          })
          .catch(err => console.log(err.response.data))
      }
    },
}

export default clubStore