<template>
  <div class="home">
    <section>
      <div class="v-100 video-box">
        <div class="banner position-relative">
          <div class="dark-translucent-bg">
            <div class="video-text">
              <p>๊ตฌํด์ค HOMES๐๏ธ</p>
              <div id="title-line"></div>
              <p>์ฐ๋ฆฌ๋ฅผ ์ํ ์ง</p>
            </div>
          </div>
        </div>
      </div>
      <div
        class="w-100 bg-dark align-content-center search-box d-flex justify-content-center">
        <b-button
          class="my-auto info-btn"
          variant="light"
          size="lg"
          @click="routeAptView">
          ๋งค๋งค ์ ๋ณด
        </b-button>
      </div>
    </section>
    <article class="d-flex justify-content-center container">
      <b-card class="w-50 m-5" title="์งํ๋กญ๊ฒ ๋ด ์ง ๋ง๋ จํ๊ธฐ">
        <b-card-text>
          <ul class="info-ul">
            <li>๊ฐ์ฉ์๊ธ ํ์ธ ๋ฐ ๋์ถ ๊ณํ</li>
            <li>์ง ์ข๋ฅ ๋ฐ ์ง์ญ ์ ํ</li>
            <li>์ ๋ณด ์์ง &amp; ์์ธํ์</li>
            <li>๋ถ๋์ฐ ๋ฐฉ๋ฌธ &amp; ์ง๊ตฌ๊ฒฝ</li>
            <li>๊ณ์ฝ ๋ฐ ์๊ธ ์น๋ฅด๊ธฐ</li>
            <li>์์ ๊ถ ์ด์ ๋ฑ๊ธฐ</li>
          </ul>
        </b-card-text>
      </b-card>
      <b-card class="w-50 m-5" title="๋ถ๋์ฐ ๋ด์ค">
        <b-card-text>
          <ul class="info-ul">
            <li>์์ธ ์ํํธ, ๋งค๋งคยท์ ์ธ ๋ค๋ฅธ ์์โฆ์์น..์์์๊ฒฝ์ </li>
            <li>์์ธ ์์ฃผ 2๋์ฐจ ์ํํธ ์ ์๊ฐ 1์ต400๋ง์ ์ฌ๋ผ</li>
            <li>12ยท16็ผ ๊ฑฐ๋ํํโฆ๋งค์์๊ฐ ์ฌ๋ผ์ก๋ค</li>
            <li>์ฌ๊ฑด์ถ ๋์ ๋จ๋ ์์ง์ฆ์ถ ๋ฆฌ๋ชจ๋ธ๋งโฆ ์ถ์ง ์..</li>
            <li>๊ณ ๊ฐ๊ธฐ์ค 9์ต, ์์ธ ์ํํธ ์ค๊ฐ๊ฐ ๋๋ค</li>
          </ul>
        </b-card-text>
      </b-card>
    </article>
  </div>
</template>

<script>
export default {
  methods: {
    routeAptView() {
      this.$router.push({ name: "HouseView" });
    },
  },
};
</script>

<style scoped>
.video-box {
  height: 500px;
  margin: 0 auto;
  overflow: hidden;
}

.banner {
  background-image: url(@/assets/img/banner.png);
  height: 100vh;
  background-size: cover;
}

.dark-translucent-bg {
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 100%;
}

.video-text {
  position: absolute;
  top: 25%;
  width: 100%;
}

.video-text p {
  margin-top: -24px;
  text-align: center;
  font-size: 30px;
  line-height: 60px;
  color: #ffffff;
  text-shadow: -1px 0 #000, 0 1px #000, 1px 0 #000, 0 -1px #000;
}

.info-btn {
  width: 9.6rem;
  height: 4rem;
  border-radius: 1.2rem;
}

#title-line {
  position: absolute;
  left: 44.4%;
  top: 30%;
  justify-content: center;
  background-color: white;
  width: 225px;
  height: 2px;
  box-shadow: 2px 2px 2px gray;
}

.search-box {
  height: 120px;
}

.info-ul > li {
  margin-top: 5px;
  margin-bottom: 5px;
}
</style>
