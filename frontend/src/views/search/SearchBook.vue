<template>
  <div class="mt-5">
    <div class="d-flex justify-content-center"> 
      <div class="w-75">
        <div class="row no-gutters row-cols-1 row-cols-md-3">
          <div class="col mb-4 p-3">
            <div class="card h-100">
              <img class="bookImg card-img-top" src="https://images-na.ssl-images-amazon.com/images/I/712KSYstH9L.jpg">
              <div class="overlay">
                <div class="text">
                  <p>Little Chinese Seamstress</p>
                  <small>By. Dal Sijie</small>
                </div>
              </div>
            </div>
          </div>
          <div class="col mb-4 p-3">
            <div class="card h-100">
              <img class="bookImg card-img-top" src="https://images-na.ssl-images-amazon.com/images/I/712KSYstH9L.jpg">
              <div class="overlay">
                <div class="text">
                  <p>Little Chinese Seamstress</p>
                  <small>By. Dal Sijie</small>
                </div>
              </div>
            </div>
          </div>
          <div class="col mb-4 p-3">
            <div class="card h-100">
              <img class="bookImg card-img-top" src="https://images-na.ssl-images-amazon.com/images/I/712KSYstH9L.jpg">
              <div class="overlay">
                <div class="text">
                  <p>Little Chinese Seamstress</p>
                  <small>By. Dal Sijie</small>
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
  name: 'SearchBook',
}
</script>

<style scoped>
.card {
  box-shadow: 8px 8px 10px 3px rgb(0, 0, 0, 0.2)
}

.image {
  display: block;
  width: 100%;
  height: auto;
}

.card .overlay {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  /* transition: .5s ease; */
  background-color: rgba(0, 0, 0, 0.7);
}

.card:hover .overlay {
  opacity: 1;
  -webkit-transition: all 0.4s ease-in-out 0s;
  -moz-transition: all 0.4s ease-in-out 0s;
  transition: all 0.4s ease-in-out 0s;
}
.card:hover .text {
  top: 50%;
  left: 50%;
  opacity: 1;
}

.text {
  color: white;
  font-size: 20px;
  position: absolute;
  padding-left: 0.3em;
  padding-right: 0.3em;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  transition: all 0.3s ease-in-out 0s;
  text-align: center;
}

.text p {
  color: #fff;
  padding-left: 5px;
  font-size: 0.75em;
  font-weight: 300;
  letter-spacing: 0.15em;
  margin-bottom: 0.5em;
  white-space: pre-wrap;
  word-break: keep-all;
}
</style>