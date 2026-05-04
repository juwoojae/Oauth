<template>
  <div>
    구글 로그인 진행중...
  </div>
</template>

<script>
import axios from 'axios';

export default{
  created(){ //훅함수, 화면이 열리자 마자 실행하는 함수
    const code = new URL(window.location.href).searchParams.get("code");
    this.sendCodeToServer(code); // 벡엔드에게 코드를 넘겨주는 코드 작성하기
    // 그후, 벡엔드에서는 모든 인증 작업 진행한후, 클라이언트에게 토큰을 주기
  },
  methods:{
    async sendCodeToServer(code){
      const response = await axios.post("http://localhost:8080/member/google/doLogin", {code});
      const token = response.data.token; // 벡엔드는 인증후 accesstoken 을 넘겨주게 되고, 프론트엔드는 이 토큰을 localStorage 에 저장
      localStorage.setItem("token", token);
      window.location.href = "/";
    }
  }
}
</script>