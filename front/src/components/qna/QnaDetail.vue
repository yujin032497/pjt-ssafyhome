<template>
  <div class="list-container">
    <b-container class="bv-example-row mt-3">
      <b-row class="mb-1">
        <b-col>
          <b-card
            class="mb-2"
            border-variant="dark"
            :header-html="`<h3>${qna.qnaNo}. ${qna.subject}</h3></div><div>${qna.registerTime}</h6>`">
            <b-card-body class="text-left">
              <div v-html="message"></div>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
      <b-row v-if="qna.state === 0" class="mb-1">
        <b-col class="text-left">
          <p>답변내용</p>
          <b-alert show variant="secondary"
            ><a href="#" class="alert-link">Secondary Alert</a></b-alert
          >
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-left">
          <b-form-group
            label-cols="12"
            id="content"
            label="답변내용"
            label-for="content"
            description="답변내용을 입력하세요.">
            <b-form-textarea
              id="content"
              placeholder="답변내용 입력..."
              v-model="input.content"
              rows="15"
              max-row="20"
              ref="content" />
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-left">
          <b-button variant="outline-primary" @click="moveList">목록</b-button>
        </b-col>
        <b-col v-if="loginUser.isAdmin !== 1" class="text-right">
          <b-button
            v-if="qna.state === '1'"
            variant="outline-danger"
            @click="removeQna"
            >문의취소</b-button
          >
        </b-col>
        <b-col v-if="loginUser.isAdmin === 1" class="text-right">
          <b-button v-if="qna.state === '1'" variant="primary" @click="validate"
            >답변하기</b-button
          >
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
// import moment from "moment";
import { mapActions, mapGetters } from "vuex";

export default {
  name: "qnaDetail",

  computed: {
    message() {
      if (this.qna.content) return this.qna.content.split("\n").join("<br>");
      return "";
    },

    input() {
      return {
        content: "",
      };
    },
    ...mapGetters({ loginUser: "loginUser" }),
    ...mapGetters({ qna: "qna" }),
  },
  created() {
    const qnaNo = this.$route.params;
    console.log(qnaNo);
    this.getQna(qnaNo);
  },
  methods: {
    ...mapActions({ getQna: "getQna", deleteQna: "deleteQna" }),

    removeQna() {
      if (confirm("삭제하시겠습니까??")) {
        const payload = {
          qnaNo: this.$route.params,
          callback: () => {
            console.log("ok22");
            this.$router.push({ path: "/qna/list" });
          },
        };

        console.log(payload);
        this.deleteQna(payload);
      }
    },

    moveList() {
      this.$router.push({ name: "QnaList" });
    },

    validate() {
      let isValid = true;
      let errMsg = "";

      !this.input.content
        ? ((isValid = false),
          (errMsg = "내용을 입력해주세요."),
          this.$refs.content.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        const payload = {
          qna: {
            qnaNo: this.$route.params.qnaNo,
            content: this.input.content,
          },
          callback: () => {
            console.log("callback!! 호출", this);

            this.moveList();
          },
        };
        if (this.type == "create") {
          this.updateAnswerQna(payload);
        } else {
          //this.modifyBoard(payload);
        }
      }
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
