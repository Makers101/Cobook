<template>
  <div>
    클럽 이벤트 방 번호: {{ roomId }}
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