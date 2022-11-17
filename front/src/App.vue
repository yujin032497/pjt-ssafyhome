<template>
  <div id="app">
    <header-nav />
    <router-view />
    <footer-info />
  </div>
</template>
<script>
import HeaderNav from "@/components/commons/HeaderNav.vue";
import FooterInfo from "@/components/commons/FooterInfo.vue";

export default {
  components: {
    FooterInfo,
    HeaderNav,
  },
};
</script>

<style scoped></style>
