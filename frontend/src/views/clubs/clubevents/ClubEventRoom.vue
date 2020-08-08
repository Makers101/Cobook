<template>
  <div>
    {{ roomId }}
  </div>
</template>

<script>
export default {
  name: "ClubEventRoom",
  data() {
    return {
      roomId: this.$route.params['roomId']
    }
  }
}
</script>

<style>

</style>