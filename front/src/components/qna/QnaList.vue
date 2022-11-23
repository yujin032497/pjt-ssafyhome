<template>
  <div class="list-container py-3">
    <b-container class="my-auto">
      <b-row>
        <b-col class="text-center">
          <h3 v-if="loginUser.isAdmin !== 1" class="underline title">
            1:1 문의하기
          </h3>
          <h3 v-else class="underline title">1:1 답변하기</h3>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-right">
          <b-button
            v-if="loginUser.isAdmin !== 1"
            variant="outline-primary"
            @click="movePage">
            등록</b-button
          >
        </b-col>
      </b-row>
      <b-row>
        <b-col v-if="qnas.length">
          <b-table-simple hover responsive>
            <b-thead v-if="loginUser.isAdmin !== 1" class="table-info">
              <b-tr class="text-center">
                <b-th>제목</b-th>
                <b-th width="10%">작성일</b-th>
                <b-th width="15%">진행상태</b-th>
              </b-tr>
            </b-thead>
            <b-thead v-else class="table-info">
              <b-tr class="text-center">
                <b-th>제목</b-th>
                <b-th width="15%">사용자ID</b-th>
                <b-th width="10%">작성일</b-th>
                <b-th width="15%">진행상태</b-th>
              </b-tr>
            </b-thead>

            <tbody v-if="loginUser.isAdmin !== 1">
              <qna-list-row
                v-for="(qnas, index) in qnas"
                :key="index"
                :qnaNo="qnas.qnaNo"
                :subject="qnas.subject"
                :registerTime="qnas.registerTime"
                :state="qnas.state"></qna-list-row>
            </tbody>
            <tbody v-else>
              <admin-list-row
                v-for="(qnas, index) in qnas"
                :key="index"
                :qnaNo="qnas.qnaNo"
                :subject="qnas.subject"
                :userId="qnas.userId"
                :registerTime="qnas.registerTime"
                :state="qnas.state"></admin-list-row>
            </tbody>
          </b-table-simple>
        </b-col>
        <b-col v-else class="text-center">등록된 문의글이 없습니다.</b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";
export default {
  data() {
    return {
      pgno: 1,
      spp: 10,
    };
  },

  components: {
    "qna-list-row": () => import("@/components/qna/include/QnaListRow.vue"),
    "admin-list-row": () =>
      import("@/components/qna/include/QnaAdmListRow.vue"),
  },
  methods: {
    ...mapActions(["getQnas", "getAllQnas"]),
    movePage: function () {
      this.$router.push({ name: "QnaWrite" });
    },
  },
  computed: {
    ...mapGetters(["qnas", "loginUser"]),
  },

  created() {
    if (this.loginUser.isAdmin === 1) {
      this.getAllQnas();
    } else {
      const payload = {
        userId: this.loginUser.id,
      };
      this.getQnas(payload);
      //console.log(this.total);
    }
  },
};
</script>

<style>
.list-container {
  min-height: 64.2wh;
}
</style>
