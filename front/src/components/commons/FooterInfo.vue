<template>
  <footer>
    <div class="w-100 footer-container d-flex justify-content-center">
      <div class="footerTop my-auto">
        <img src="@/assets/img/ssafylogo.png" height="50px" width="65px" />
        <div class="footerTopText">
          <h3>Find Us</h3>
          <p>
            address<span id="findUs1"
              >| (SSAFY) 서울시 강남구 테헤란로 멀티스퀘어</span
            >
          </p>
          <p>contact<span id="findUs2">| 1544-9001</span></p>
          <p>email<span id="findUs3">| admin@ssafy.com</span></p>
          <p>copyright by SSAFY. All rights reserved.</p>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: "FooterInfo.vue",
};
</script>

<style scoped>
.footer-container {
  background-color: #ababab;
  height: 30vh;
}

h3 {
  margin: 15px;
}

p {
  margin: 5px !important;
}
</style>
