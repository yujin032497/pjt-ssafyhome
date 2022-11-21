<template>
  <div>
    <div>
      <div class="search-item mb-2 d-flex">
        <div
          class="info"
          @click="onEmit"
          v-b-tooltip.hover
          title="클릭하시면 주변정보를 조회할 수 있습니다.">
          <p class="content">{{ item.aptName }}</p>
          <p class="sub-content">{{ item.dealAmount | filterPrice }}만원</p>
          <p class="sub-content">{{ item.fullAddress }}</p>
          <p class="sub-content">{{ item.buildYear }}년 건축</p>
        </div>
        <div class="fav text-center">
          <div
            v-b-tooltip.hover
            title="클릭하시면 관심지역으로 등록하실 수 있습니다.">
            <b-icon class="icon" icon="star-fill"></b-icon>
          </div>
        </div>
      </div>
    </div>
    <div class="dropdown-divider"></div>
  </div>
</template>

<script>
export default {
  name: "ItemContent",
  props: {
    item: {
      idx: { type: Number },
      aptName: { type: String },
      dealAmount: { type: Number },
      fullAddress: { type: String },
      buildYear: { type: String },
    },
  },
  methods: {
    onEmit() {
      console.log(this.item.idx);
      this.$emit("detailIdx", this.item.idx);
    },
  },
  filters: {
    filterPrice: (value) => {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style scoped>
p {
  margin: 0;
}

.search-item {
  background-color: var(--skyblue);
  height: 130px;
  width: 100%;
}

.content {
  font-size: 1.5rem;
  font-weight: bold;
}

.sub-content {
  font-size: 1.2rem;
}

.fav {
  width: 20%;
  height: 100%;
  background-color: #2eca6a;
  vertical-align: center;
}

.fav:hover {
  color: yellow;
}

.info {
  width: 80%;
  height: 100%;
}

.icon {
  width: 20px;
  height: 20px;
  margin-top: 50%;
}
</style>
