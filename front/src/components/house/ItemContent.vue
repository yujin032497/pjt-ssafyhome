<template>
  <div>
    <div>
      <div class="search-item mb-2 d-flex">
        <div
          class="info"
          @click="onEmit"
          v-b-tooltip.hover
          title="클릭하시면 주변정보와 거래내역을 조회할 수 있습니다.">
          <p class="content">{{ item.aptName }}</p>
          <p class="sub-content" v-if="type === 1">
            평균 {{ item.dealAmount | filterPrice }}만원
          </p>
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
      <div class="d-flex flex-row category-bar">
        <b-button
          class="flex-fill text-center align-self-center btn-category"
          @click="onCategory(0)">
          맛집
        </b-button>
        <b-button
          class="flex-fill text-center align-self-center btn-category"
          @click="onCategory(1)">
          카페
        </b-button>
        <b-button
          class="flex-fill text-center align-self-center btn-category"
          @click="onCategory(2)">
          병원
        </b-button>
        <b-button
          class="flex-fill text-center align-self-center btn-category"
          @click="onCategory(3)">
          은행
        </b-button>
      </div>
    </div>
    <div>
      <detail-item
        v-show="expanded"
        v-for="(data, index) in datas"
        :key="index"
        :detailItem="data"></detail-item>
      <p v-if="!check">{{ msg }}</p>
    </div>
    <div class="dropdown-divider"></div>
  </div>
</template>

<script>
import DetailItem from "@/components/house/import/DetailItem";
import http from "@/util/http-common";

export default {
  name: "ItemContent",
  components: { DetailItem },
  data() {
    return {
      datas: [],
      check: true,
      expanded: false,
      msg: "로딩중입니다...",
    };
  },
  props: {
    item: {
      idx: { type: Number },
      aptCode: { type: String },
      aptName: { type: String },
      dealAmount: { type: Number },
      fullAddress: { type: String },
      buildYear: { type: String },
    },
    type: { type: Number },
    contentType: { type: String },
  },
  methods: {
    onEmit() {
      this.$emit("detailIdx", this.item.idx, this.expanded);

      if (!this.expanded) {
        this.check = false;
        this.expanded = !this.expanded;
        http
          .get(
            `/map/detail?aptCode=${this.item.aptCode}&type=${this.type}&gubn=${this.contentType}`,
          )
          .then((response) => {
            switch (response.status) {
              case 200:
                this.datas = response.data;
                this.check = true;
                break;
            }
          })
          .catch(() => {
            this.datas = [];
            this.msg = "데이터를 불러오는데 실패하였습니다.";
            this.expanded = false;
          });
      } else {
        this.expanded = !this.expanded;
      }
    },
    onCategory(cIdx) {
      this.$emit("categoryIdx", this.item.idx, cIdx);
    },
  },
  mounted() {
    console.log("이거", this.item);
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
  margin-left: 1rem;
}

.search-item {
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

.category-bar {
  height: 50px;
  width: 100%;
}

.btn-category {
  margin-left: 1rem;
  margin-right: 1rem;
}
</style>
