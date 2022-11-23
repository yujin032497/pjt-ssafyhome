<template>
  <div class="d-block search-condition">
    <div class="d-flex search-bar">
      <b-form-select
        class="mr-1"
        v-model="sidoCode"
        :options="sidos"
        @change="gugunList"></b-form-select>
      <b-form-select
        class="mx-1"
        v-model="gugunCode"
        :options="guguns"
        @change="dongList"></b-form-select>
      <b-form-select
        class="mx-1"
        v-model="dongCode"
        :options="dongs"
        @change="changeDong"></b-form-select>
    </div>
    <div class="search-bar">
      <b-form-select
        class="mr-1"
        v-model="searchType"
        @change="changeType"
        :options="selectType"></b-form-select>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
export default {
  name: "SearchCondition",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      searchType: null,
      selectType: [
        { value: null, text: "매매/전월세 구분" },
        { value: 1, text: "매매" },
        { value: 0, text: "전월세" },
      ],
    };
  },
  computed: {
    ...mapState(["sidos", "guguns", "dongs"]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(["getSido", "getGugun", "getDong"]),
    ...mapMutations(["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST"]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      // console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.gugunCode = null;
      this.dongCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    changeDong() {
      this.$emit("changeDong", this.dongCode);
    },
    changeType() {
      this.$emit("changeType", this.searchType);
    },
  },
};
</script>

<style scoped>
.search-condition {
  width: 80%;
  height: 100%;
}

.search-bar {
  height: 50%;
}
</style>
