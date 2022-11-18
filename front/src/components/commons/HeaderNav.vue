<template>
  <b-navbar toggleable="lg" class="bg-nav">
    <b-navbar-brand href="/">
      <img src="@/assets/img/home.png" class="logo" />
    </b-navbar-brand>

    <b-navbar-nav>
      <b-nav-item href="/house" class="text-white">실거래가 조회</b-nav-item>
      <b-nav-item href="/board/faq" class="text-white">FAQ</b-nav-item>
      <b-nav-item href="/board/qna" class="text-white">1:1 문의하기</b-nav-item>
      <!-- <b-nav-item
        href="/fav/list"
        class="text-white"
        :class="{ 'text-hide': loginUser.id === undefined }"
        >관심지역 설정
      </b-nav-item>
      <b-nav-item
        href="/fav"
        class="text-white"
        :class="{ 'text-hide': loginUser.id === undefined }"
        >관심지역 내역
      </b-nav-item> -->
      <b-nav-item
        href="/user/list"
        class="text-white"
        :class="{
          'text-hide': loginUser.isAdmin !== 1,
        }"
        >회원 관리
      </b-nav-item>
    </b-navbar-nav>

    <b-navbar-nav class="ml-auto">
      <b-nav-item
        href="/user/loginForm"
        class="text-white"
        :class="{ 'text-hide': loginUser.id !== undefined }"
        >로그인
      </b-nav-item>
      <b-nav-item
        href="/user/regist"
        class="text-white"
        :class="{ 'text-hide': loginUser.id !== undefined }"
        >회원가입
      </b-nav-item>
      <b-nav-item
        @click="logout"
        class="text-white"
        :class="{ 'text-hide': loginUser.id === undefined }"
        >로그아웃
      </b-nav-item>
      <b-nav-item
        @click="moveDetail"
        class="text-white"
        :class="{ 'text-hide': loginUser.id === undefined }"
        >내 정보
      </b-nav-item>
    </b-navbar-nav>
  </b-navbar>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  methods: {
    ...mapActions({ logoutUser: "logout" }),
    logout() {
      this.logoutUser({
        callback: () => {
          alert("로그아웃되었습니다!");
          if (this.$route.path !== "/") {
            this.$router.push({ name: "home" });
          }
        },
      });
    },

    moveDetail() {
      this.$router.push({ path: "/user/detail/" + this.loginUser.id });
    },
  },
  computed: {
    ...mapGetters({ loginUser: "loginUser" }),
  },
};
</script>

<style scoped>
.logo {
  width: 25px;
  height: 25px;
}
.bg-nav {
  background-color: var(--deep-skyblue) !important;
}
</style>
