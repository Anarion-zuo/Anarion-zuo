<template>
  <div id="app">
    <Header/>
    <Blog/>
  </div>
</template>

<script>
// import HelloWorld from './components/HelloWorld.vue'
import Header from './components/header/Header.vue'
import Blog from './components/blog/Blog.vue'

export default {
  name: 'App',
  components: {
    Header,
    Blog,
  }
}
</script>

<style scoped>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
  #main {
    margin: 10px;
    padding: 10px;
    border: 1px;
    border-radius: 2px;

    display: flex;
  }
</style>
