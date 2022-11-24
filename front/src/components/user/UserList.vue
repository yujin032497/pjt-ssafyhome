<template>
  <section class="d-flex justify-content-center h-100 container">
    <div class="py-5">
      <span class="my-auto overflow-auto">
        <p class="h1 text-center font-weight-bold">회원정보 관리</p>
        <div class="wrapper-search d-flex">
          <b-form-select
            class="search-select"
            v-model="selected"
            :options="options">
          </b-form-select>
          <b-form-input
            class="search-input mx-2"
            v-model="searchVal"
            placeholder="유저검색" />
          <b-button class="search-btn" @click="search">검색</b-button>
        </div>
        <br />
        <b-table
          class="tbl text-center"
          :items="accounts"
          :fields="fields"
          :tbody-tr-class="rowStriped"
          responsive>
          <template #cell(수정)="data">
            <b-button
              class="p-sm-1 mr-1 btn-user h-100"
              @click="moveModify(data)"
              >수정</b-button
            >
          </template>
          <template #cell(삭제)="data">
            <b-button
              class="p-sm-1 mr-1 btn-user"
              @click="moveDelete(data)"
              v-if="data.item.isAdmin !== 1"
              >삭제</b-button
            >
          </template>
        </b-table>
        <div class="w-100 text-center" v-if="accounts.length === 0">
          조회된 내용이 없습니다.
        </div>
      </span>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      fields: [
        {
          key: "id",
          label: "ID",
        },
        { key: "password", label: "패스워드" },
        { key: "name", label: "이름" },
        { key: "address", label: "주소" },
        { key: "phone", label: "전화번호" },
        "수정",
        "삭제",
      ],
      searchVal: "",
      options: [
        { value: null, text: "검색조건" },
        { value: "id", text: "아이디" },
        { value: "name", text: "이름" },
        { value: "address", text: "주소" },
        { value: "phone", text: "전화번호" },
      ],
      selected: null,
    };
  },
  methods: {
    ...mapActions({
      selectAccountList: "selectAccountList",
      deleteAccount: "deleteAccount",
    }),
    moveModify(data) {
      const userId = data.item.id;
      this.$route.params.id = userId;
      this.$route.params.mode = "adm";
      this.$router.push({ name: "UserModifyAdm" });
    },
    moveDelete(data) {
      const userId = data.item.id;

      this.deleteAccount({
        id: userId,
        cb: () => {},
      });
    },
    rowStriped(item, type) {
      if (!item || type !== "row") return;
      if (item.idx % 2 === 0) return "table-striped-tr";
    },

    search() {
      if (!this.selected !== null && this.searchVal !== "") {
        const payload = {
          key: this.selected,
          value: this.searchVal,
        };
        this.selectAccountList(payload);
      } else {
        this.selectAccountList({});
      }
    },
  },
  mounted() {
    const el = document.querySelectorAll(".table-striped-tr");

    for (let e of el) {
      e.setAttribute("style", "background-color: #53589320");
    }
  },
  created() {
    if (this.loginUser.isAdmin !== 1) {
      alert("잘못된 접근입니다!");
      this.$router.push({ name: "home" });
    }
    this.selectAccountList({});
  },
  computed: {
    ...mapGetters({ accounts: "accounts", loginUser: "loginUser" }),
    input() {
      return {
        searchVal: "",
      };
    },
  },
};
</script>

<style scoped>
section {
  min-height: 66vh !important;
}
.tbl {
  min-width: 100vh;
}
.btn-user {
  color: white;
  background-color: #535893;
  width: 80px;
  border: 1px solid #ced4da !important;
}

.btn-user:hover {
  color: black;
  background-color: white;
}
.wrapper-search {
  width: 50%;
}
.search-input {
  width: 40%;
}
.search-select {
  width: 30%;
}
.search-btn {
  width: 20%;
}
</style>
