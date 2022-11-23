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
          <p class="sub-content">
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
    </div>
    <div>
      <detail-item
        v-show="expanded"
        v-for="(data, index) in datas"
        :key="index"
        :detailItem="data"></detail-item>
      <p v-if="datas.length === 0 && check">로딩중입니다</p>
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
      check: false,
      expanded: false,
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
      this.$emit("detailIdx", this.item.idx);
      this.check = true;
      if (!this.expanded) {
        console.log("1호출");
        http
          .get(
            `/map/detail?aptCode=${this.item.aptCode}&type=${this.type}&gubn=${this.contentType}`,
          )
          .then((response) => {
            switch (response.status) {
              case 200:
                this.datas = response.data;
                console.log(this.datas);
                this.expanded = !this.expanded;
                break;
            }
          })
          .catch(() => {
            this.datas = [];
          });
        // let response = {
        //   status: 200,
        //   data: [
        //     {
        //       floor: 3,
        //       price: 5000,
        //       dealYmd: "2022-11-23",
        //     },
        //     {
        //       floor: 3,
        //       price: 5000,
        //       dealYmd: "2022-11-23",
        //     },
        //     {
        //       floor: 3,
        //       price: 5000,
        //       dealYmd: "2022-11-23",
        //     },
        //   ],
        // };
        //
        // switch (response.status) {
        //   case 200: {
        //     this.mockup = response.data;
        //     this.expanded = !this.expanded;
        //     break;
        //   }
        // }
      } else {
        this.expanded = !this.expanded;
      }
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
