<template>
  <div class="mt-5">
    <div class="d-flex justify-content-center"> 
      <div class="w-75">
        <div class="row no-gutters row-cols-1 row-cols-md-3">
          <div class="col mb-4 p-3">
            <div class="card h-100">
              <img class="bookImg card-img-top" src="https://images-na.ssl-images-amazon.com/images/I/712KSYstH9L.jpg">
              <div class="overlay">
                <div class="text">Hello World</div>
              </div>
            </div>
          </div>
          <div class="col mb-4 p-3">
            <div class="card h-100">
              <img class="bookImg card-img-top" src="https://images-na.ssl-images-amazon.com/images/I/712KSYstH9L.jpg">
            </div>
          </div>
          <div class="col mb-4 p-3">
            <div class="card h-100">
              <img class="bookImg card-img-top" src="https://images-na.ssl-images-amazon.com/images/I/712KSYstH9L.jpg">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SearchBook',
}
</script>

<style scoped>
.card {
  box-shadow: 8px 8px 10px 3px rgb(0, 0, 0, 0.2)
}
</style>