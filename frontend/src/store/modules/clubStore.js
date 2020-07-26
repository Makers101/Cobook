const clubStore = {
    namespaced: true,
    state: {
        clubs: [
            {
              id: 1,
              name: 'sample_name_1',
              image: 'http://placehold.jp/300x200.png?text=sample',
              oneline_description: '주로 문학 작품을 읽는 클럽 sample_club_1입니다. 쿄쿄쿄쿄쿄쿜쿄쿄쿄쿄쿄쿄쿄',
              residence: '역삼동',
              participant_num: 3,
              followers: 50,
              genres: ['문학', '예술'],
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
              genres: ['철학'],
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
              genres: ['문학', '철학'],
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
              genres: ['문학', '철학'],
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
              genres: ['문학', '철학'],
              open: false
            },
            {
              id: 6,
              name: 'sample_name_6길다리이름이',
              image: 'http://placehold.jp/300x200.png?text=sample',
              oneline_description: '주로 문학작품을 읽는 클럽 sample_club입니다.',
              residence: '신촌',
              participant_num: 3,
              followers: 50,
              genres: ['문학', '철학', '예술'],
              open: true
            }
        ]
    },
    getters: {
    },
    mutations: {
    },
    actions: {
    },
}

export default clubStore