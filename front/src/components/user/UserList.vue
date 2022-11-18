<template>
  <section class="d-flex justify-content-center h-100 container">
    <div class="py-5">
      <span class="my-auto overflow-auto">
        <p class="h1 text-center font-weight-bold">회원정보 관리</p>
        <b-table
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
            <b-button class="p-sm-1 mr-1 btn-user" @click="moveDelete(data)"
              >삭제</b-button
            >
          </template>
        </b-table>
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
    };
  },
  methods: {
    ...mapActions({
      selectAccountList: "selectAccountList",
      deleteAccount: "deleteAccount",
    }),
    moveModify(data) {
      const userId = data.item.id;
      this.$router.push({ path: `/user/modify/${userId}` });
    },
    moveDelete(data) {
      const userId = data.item.id;
      this.deleteAccount({
        id: userId,
        cb: () => {
          this.$router.push({ name: "home" });
        },
      });
    },
    rowStriped(item, type) {
      if (!item || type !== "row") return;
      if (item.idx % 2 === 0) return "table-striped-tr";
    },
  },
  mounted() {
    const el = document.querySelectorAll(".table-striped-tr");

    for (let e of el) {
      e.setAttribute("style", "background-color: #53589320");
    }
  },
  created() {
    this.selectAccountList();
  },
  computed: {
    ...mapGetters({ accounts: "accounts" }),
  },
};
</script>

<style scoped>
section {
  min-height: 64vh !important;
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
</style>
