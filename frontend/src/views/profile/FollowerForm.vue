<template>
  <v-app>
    <v-dialog v-model="show" max-width="500px">
    <v-card>
      <v-card-actions>
        <v-btn color="primary" text @click.stop="show=false">Close</v-btn>
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
    }
  }
}
</script>
