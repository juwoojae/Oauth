<template>
  <v-app>
    <HeaderComponent />
    <v-main>
      <router-view />
    </v-main>

  </v-app>
</template>

<script>
import HeaderComponent from './components/HeaderComponent.vue' //header Component 를 불러옴

export default {
  components: {
    HeaderComponent //header Component 를 추가함
  }
}
</script>
