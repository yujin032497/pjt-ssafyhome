<template>
  <div class="d-block search-condition">
    <div
      class="search-bar p-auto d-flex align-content-center justify-content-center">
      <div class="d-flex w-100">
        <b-form-select
          class="flex-fill mr-1"
          v-model="sidoCode"
          :options="sidos"
          @change="gugunList"></b-form-select>
        <b-form-select
          class="flex-fill mx-1"
          v-model="gugunCode"
          :options="guguns"
          @change="dongList"></b-form-select>
        <b-form-select
          class="flex-fill mx-1"
          v-model="dongCode"
          :options="dongs"
          @change="changeState"></b-form-select>
      </div>
    </div>
    <div class="d-flex w-100">
      <b-form-select
        class="flex-fill mr-1"
        v-model="type"
        @change="changeGubn"
        :options="selectType"></b-form-select>
      <b-form-select
        v-if="showInput == true"
        class="flex-fill mr-1"
        v-model="jeonwol"
        @change="changeState"
        :options="selectJeonwol"></b-form-select>
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
      type: null,
      selectType: [
        { value: null, text: "구분" },
        { value: 1, text: "매매" },
        { value: 0, text: "전/월세" },
      ],
      showInput: false,
      jeonwol: 1, // 0이면 전세 1이면 월세
      selectJeonwol: [
        { value: 1, text: "월세" },
        { value: 0, text: "전세" },
      ],
    };
  },
  props: {
    contentType: { type: String },
  },
  computed: {
    ...mapState(["sidos", "guguns", "dongs"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(["getSido", "getGugun", "getDong"]),
    ...mapMutations(["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST"]),
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.gugunCode = null;
      this.dongCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.sidoCode && this.gugunCode) {
        this.getDong(this.gugunCode);
      }
    },
    changeState() {
      this.$emit("changeState", this.dongCode, this.type, this.jeonwol);
    },
    changeGubn() {
      // 타입이 전월세면
      if (this.type === 0) {
        this.showInput = true;
      } else {
        this.showInput = false;
        this.jeonwol = 1;
      }
      this.$emit("changeState", this.dongCode, this.type, this.jeonwol);
    },
  },
  watch: {
    contentType() {
      this.CLEAR_SIDO_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.getSido();
      this.sidoCode = null;
      this.gugunCode = null;
      this.dongCode = null;
    },
  },
};
</script>

<style scoped>
.search-condition {
  width: 80%;
  height: auto;
}

.search-bar {
  height: 50%;
}
</style>
