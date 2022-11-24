<template>
  <div class="list-container">
    <b-container class="bv-example-row mt-3">
      <b-row class="mb-1">
        <b-col>
          <b-card
            class="mb-2"
            border-variant="dark"
            :header-html="`<h3>${article.articleNo}. ${article.subject}</h3><div><h6>${article.userName}</div></div><div>${article.hit}</h6></div><div>${article.registerTime}</h6>`">
            <b-card-body class="text-left">
              <div v-html="message"></div>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-left">
          <b-button variant="outline-primary" @click="moveList">목록</b-button>
        </b-col>
        <b-col class="text-right">
          <b-button
            v-if="loginUser.isAdmin === 1"
            variant="outline-info"
            size="sm"
            @click="moveModifyArticle"
            class="mr-2"
            >글수정
          </b-button>
          <b-button
            v-if="loginUser.isAdmin === 1"
            variant="outline-danger"
            size="sm"
            @click="deleteArticle"
            >글삭제
          </b-button>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
// import moment from "moment";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "BoardDetail",

  computed: {
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
    ...mapGetters({ loginUser: "loginUser" }),
    ...mapGetters({ article: "board" }),
  },
  created() {
    const articleNo = this.$route.params;
    this.getBoard(articleNo);
  },
  methods: {
    ...mapActions({ getBoard: "getBoard", deleteBoard: "deleteBoard" }),
    moveModifyArticle() {
      this.$router.replace({
        name: "BoardModify",
        params: { articleno: this.article.articleNo },
      });
      this.$router.push({ path: `/board/modify/${this.article.articleNo}` });
    },
    deleteArticle() {
      if (confirm("삭제하시겠습니까??")) {
        const payload = {
          articleNo: this.$route.params,
          callback: () => {
            this.$router.push({ path: "/board/list" });
          },
        };
        this.deleteBoard(payload);
      }
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
  },
  // filters: {
  //   dateFormat(regtime) {
  //     return moment(new Date(regtime)).format("YY.MM.DD hh:mm:ss");
  //   },
  // },
};
</script>

<style scoped>
.list-container {
  min-height: 64.2vh;
}
</style>
