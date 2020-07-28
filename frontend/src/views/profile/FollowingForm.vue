<template>
  <v-app>
    <v-dialog v-model="show" max-width="500px">
    <v-card>
      <v-card-actions>
        <v-card-text>
          <div class="display-1">팔로잉</div>
          <p class=" text--primary">유저가 팔로잉 하는 사람입니다.</p>
          <!-- 팔로잉 리스트 -->
          <div class="">

          </div>
        </v-card-text>
        
        <v-btn color="primary" text @click.stop="show=false"> Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
  </v-app>
</template>

<script>
export default {
  props: {
     value: Boolean
  },
  computed: {
    show: {
      get () {
        return this.value
      },
      set (value) {
         this.$emit('input', value)
      }
    },
    user: {
      name: 'user'
    }
  }
}
</script>
