<template>
  <div class="home">
    <section>
      <div class="v-100 video-box">
        <video muted autoplay loop class="w-100">
          <source src="@/assets/img/mainHouse.mp4" type="video/mp4" />
          <strong>Your browser does not support the video tag.</strong>
        </video>
        <div class="video-text">
          <p>구해줘 HOMES🏘️</p>
          <div id="title-line"></div>
          <p>우리를 위한 집</p>
        </div>
      </div>
      <div
        class="w-100 bg-dark align-content-center search-box d-flex justify-content-center">
        <b-button
          class="my-auto info-btn"
          variant="light"
          size="lg"
          @click="routeAptView">
          매매 정보
        </b-button>
      </div>
    </section>
    <article class="d-flex justify-content-center container">
      <b-card class="w-50 m-5" title="지혜롭게 내 집 마련하기">
        <b-card-text>
          <ul class="info-ul">
            <li>가용자금 확인 및 대출 계획</li>
            <li>집 종류 및 지역 선택</li>
            <li>정보 수집 &amp; 시세파악</li>
            <li>부동산 방문 &amp; 집구경</li>
            <li>계약 및 잔금 치르기</li>
            <li>소유권 이전등기</li>
          </ul>
        </b-card-text>
      </b-card>
      <b-card class="w-50 m-5" title="부동산 뉴스">
        <b-card-text>
          <ul class="info-ul">
            <li>서울 아파트, 매매·전세 다른 양상…상승..아시아경제</li>
            <li>서울 입주 2년차 아파트 전셋값 1억400만원 올라</li>
            <li>12·16發 거래한파…매수자가 사라졌다</li>
            <li>재건축 대안 뜨는 수직증축 리모델링… 추진 속..</li>
            <li>고가기준 9억, 서울 아파트 중간값 됐다</li>
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
      this.$router.push({ name: "AptView" });
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

.video-text {
  position: absolute;
  top: 30%;
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
