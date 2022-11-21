<template>
  <div class="list-container py-3">
    <b-container class="my-auto">
      <b-row>
        <b-col class="text-center">
          <h3 class="underline title">1:1 문의하기</h3>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-right">
          <div class="wrapper-search d-flex">
            <b-form-select
              class="search-select"
              v-model="selected"
              :options="options">
            </b-form-select>
            <b-form-input
              class="search-input mx-2"
              v-model="searchVal"
              placeholder="검색어를 입력하세요." />
            <b-button class="search-btn" @click="search">검색</b-button>
          </div>
          <b-button variant="outline-primary" @click="movePage">등록</b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col v-if="qnas.length">
          <b-table-simple hover responsive>
            <b-thead class="table-info">
              <b-tr class="text-center">
                <b-th>제목</b-th>
                <b-th width="10%">작성일</b-th>
                <b-th width="15%">진행상태</b-th>
              </b-tr>
            </b-thead>
            <tbody>
              <list-row
                v-for="(qnas, index) in qnas"
                :key="index"
                :articleNo="qnas.articleNo"
                :subject="qnas.subject"
                :registerTime="qnas.registerTime"
                :state="qnas.state"></list-row>
            </tbody>
          </b-table-simple>
        </b-col>
        <b-col v-else class="text-center">등록된 문의글이 없습니다.</b-col>
      </b-row>
    </b-container>
    <div class="overflow-auto">
      <b-pagination
        @page-click="pageChange"
        v-model="pgno"
        :total-rows="total"
        :per-page="10"
        aria-controls="my-table"
        align="center"></b-pagination>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  data() {
    return {
      selected: null,
      searchVal: "",
      pgno: 1,
      spp: 10,
      options: [
        { value: null, text: "분류 선택" },
        { value: "subject", text: "제목" },
        { value: "state", text: "진행상태" },
      ],
    };
  },
  methods: {
    ...mapActions(["getQnas", "getTotal"]),
    movePage: function () {
      this.$router.push({ name: "QnaWrite" });
    },
    search() {
      if (this.selected !== null && this.searchVal !== "") {
        const payload = {
          key: this.selected,
          value: this.searchVal,
          pgno: this.pgno,
          spp: this.spp,
        };
        this.getQnas(payload);
      } else {
        this.getQnas({});
      }
    },
  },
  computed: {
    ...mapGetters(["qnas"]),
    input() {
      return {
        searchVal: "",
        selected: null,
      };
    },
  },
};
</script>

<style>
.list-container {
  min-height: 64.2wh;
}
</style>
