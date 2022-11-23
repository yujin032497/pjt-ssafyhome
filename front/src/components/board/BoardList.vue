<template>
  <div class="list-container py-3">
    <b-container class="my-auto">
      <b-row>
        <b-col class="text-center">
          <h3 class="underline title">FAQ</h3>
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
          <b-button
            v-if="loginUser.isAdmin === 1"
            variant="outline-primary"
            @click="movePage"
            >등록</b-button
          >
        </b-col>
      </b-row>
      <b-row>
        <b-col v-if="boards.length">
          <b-table-simple hover responsive>
            <b-thead class="table-info">
              <b-tr class="text-center">
                <b-th width="10%">번호</b-th>
                <b-th>제목</b-th>
                <b-th width="10%">작성자</b-th>
                <b-th width="10%">조회수</b-th>
                <b-th width="10%">작성일</b-th>
              </b-tr>
            </b-thead>
            <tbody>
              <list-row
                v-for="(board, index) in boards"
                :key="index"
                :articleNo="board.articleNo"
                :subject="board.subject"
                :userName="board.userName"
                :hit="board.hit"
                :registerTime="board.registerTime"></list-row>
            </tbody>
          </b-table-simple>
        </b-col>
        <b-col v-else class="text-center">등록된 게시글이 없습니다.</b-col>
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
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      selected: null,
      searchVal: "",
      pgno: 1,
      spp: 10,
      options: [
        { value: null, text: "분류 선택" },
        { value: "author", text: "작성자" },
        { value: "subject", text: "제목" },
      ],
    };
  },
  components: {
    "list-row": () => import("@/components/board/include/ListRow.vue"),
  },
  methods: {
    ...mapActions(["getBoards", "getTotal"]),
    movePage: function () {
      this.$router.push({ name: "BoardWrite" });
    },
    search() {
      if (this.selected !== null && this.searchVal !== "") {
        const payload = {
          key: this.selected,
          value: this.searchVal,
          pgno: this.pgno,
          spp: this.spp,
        };
        this.getBoards(payload);
      } else {
        this.getBoards({});
      }
    },

    pageChange(bvEvent, page) {
      const payload = {
        pgno: page,
        spp: this.spp,
      };
      this.getBoards(payload);
    },
  },

  created() {
    console.log("뷰단");
    this.getTotal({});
    const payload = {
      pgno: this.pgno,
      spp: this.spp,
    };
    this.getBoards(payload);
    console.log(this.total);
  },

  computed: {
    ...mapGetters(["boards", "total"]),
    input() {
      return {
        searchVal: "",
        selected: null,
        pgno: 1,
        spp: 10,
      };
    },
    ...mapGetters({ loginUser: "loginUser" }),
  },
};

// <b-form-group class="wrapper-search">
//             <b-form-select class="search-select" options=""> </b-form-select>
//             <b-form-input
//               class="search-input"
//               placeholder="검색어를 입력하세요." />
//             <b-button class="search-btn">검색</b-button>
</script>

<style scoped>
.list-container {
  min-height: 64.2vh;
}

.wrapper-search {
  width: 50%;
}

.custom-select {
  width: 25%;
}

.search-input {
  width: 40%;
}

.search-btn {
  width: 20%;
}

.table-info {
  background-color: var(--deep-skyblue);
}
</style>
