<template>
  <v-app-bar app dark>
    <v-container >
      <v-row align="center">
        <v-col class="d-flex justify-end">
          <v-btn v-if="!isLogin" :to="{path: '/member/create'}">회원가입</v-btn>
          <v-btn v-if="!isLogin" :to="{path: '/member/login'}">로그인</v-btn> <!--로그인이 안되었다면 회원가입, 로그인 활성화-->
          <v-btn v-if="isLogin" @click="doLogout()">로그아웃</v-btn> <!--로그인이 되어 있다면 로그아웃 활성화-->
        </v-col>
      </v-row>
    </v-container>
  </v-app-bar>
</template>

<script>
export default { // 다른 파일에서도 불러올수있게 (공통화면)
  data(){
    return {
      isLogin: false,
    }
  },
  //화면이 열리자 마자 바로 실행되는 함수(훅 함수)
  created(){
    if(localStorage.getItem('token')){
      this.isLogin = true;
    }
  },
    methods:{
      doLogout(){ //로그 아웃 버튼을 누르면
        localStorage.clear(); //로컬 스토리지 clear
        window.location.reload();
      }
    }
 }
</script>
