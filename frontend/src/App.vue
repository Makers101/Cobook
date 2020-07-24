<template>
  <div id="app">
    <div id="nav">
      <nav class="navbar navbar-expand-md navbar-light navbar-bg-color">
        <router-link class="navbar-brand" to="/">
          <img 
            class="img-fluid logo-img" 
            src="@/assets/logo.png" 
            alt="로고 이미지"
          ><span class="ml-2 logo-text">Co-Book</span>
        </router-link>
        <button 
          class="navbar-toggler" 
          type="button" 
          data-toggle="collapse" 
          data-target="#navbarSupportedContent" 
          aria-controls="navbarSupportedContent" 
          aria-expanded="false" 
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
          </form>
          <ul class="navbar-nav mr-auto row w-100">
            <li class="nav-item col-3">
              <router-link class="nav-link" to="/">
                <i class="fas fa-home color-green"></i>
                Feed
              </router-link>
            </li>
            <li class="nav-item col-3">
              <router-link class="nav-link" :to="{ name: 'ClubList' }">
                <i class="fas fa-users color-green"></i>
                Club
              </router-link>
            </li>
            <li class="nav-item col-3">
              <router-link class="nav-link" to="/">
                <img class="img-fluid club-img" src="@/assets/meetup.png" alt="클럽 이미지">
                Meetup
              </router-link>
            </li>
            <li class="nav-item offset-1 col-1">
              <router-link class="nav-link" to="/">
                <i class="fas fa-bell color-green"></i>
              </router-link>
            </li>
            <li class="nav-item dropdown col-1">
              <router-link 
                class="nav-link dropdown-toggle" 
                to="/" 
                id="navbarDropdown" 
                role="button" 
                data-toggle="dropdown" 
                aria-haspopup="true" 
                aria-expanded="false"
              >
                <i class="fas fa-user color-green"></i>
              </router-link>
              <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                <router-link class="dropdown-item" to="/">Action</router-link>
                <router-link class="dropdown-item" to="/">Another action</router-link>
                <div class="dropdown-divider"></div>
                <router-link class="dropdown-item" to="/">Something else here</router-link>
              </div>
            </li>
          </ul>
        </div>
      </nav>
    </div>
    <router-view/>
  </div>
</template>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav a {
  font-size: 1.1rem;
  font-weight: 900;
  color: #3A2F15;
}

.navbar-bg-color {
  background: #D6CBBD;
  height: 65px;
}

.logo-img {
  height: 58px;
}

.logo-text {
  font-size: 1.5rem;
  padding: 0;
}

.club-img {
  height: 23px;
}

</style>