<template>
  <div class="list-container py-3">
    <b-container class="my-auto">
      <b-row>
        <b-col class="text-center">
          <h3 class="title">1:1 문의하기</h3>
        </b-col>
      </b-row>
      <b-row class="mb-1">
        <b-col class="text-left">
          <b-form>
            <b-form-group
              label-cols="12"
              id="subejct-group"
              label="제목"
              description="제목을 입력하세요.">
              <b-form-input
                id="subject"
                ref="subject"
                v-model="input.subject"
                type="text"
                required
                placeholder="제목 입력..." />
            </b-form-group>
            <b-form-group
              label-cols="12"
              id="content"
              label="내용"
              label-for="content"
              description="내용을 입력하세요.">
              <b-form-textarea
                id="content"
                placeholder="내용 입력..."
                v-model="input.content"
                rows="15"
                max-row="20"
                ref="content" />
            </b-form-group>
            <b-button
              v-if="type == 'create'"
              variant="primary"
              class="m-1"
              @click="validate"
              >문의하기
            </b-button>
          </b-form>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
export default {
  props: {
    // 부모 component로 부터 전달받은 type 정보를 받아옴
    type: { type: String },
  },
  methods: {
    ...mapActions(["createQna"]),
    validate() {
      let isValid = true;
      let errMsg = "";

      console.log(this.input);

      !this.input.subject
        ? ((isValid = false),
          (errMsg = "제목을 입력해주세요."),
          this.$refs.subject.focus())
        : !this.input.content
        ? ((isValid = false),
          (errMsg = "내용을 입력해주세요."),
          this.$refs.content.focus())
        : (isValid = true);
      if (!isValid) {
        alert(errMsg);
      } else {
        console.log(this.loginUser.userId);
        const payload = {
          qna: {
            qnaNo: this.$route.params.qnaNo,
            subject: this.input.subject,
            content: this.input.content,
            userId: this.loginUser.id,
          },
          callback: () => {
            console.log("callback!! 호출", this);

            this.moveList();
          },
        };
        if (this.type == "create") {
          this.createQna(payload);
        } else {
          //this.modifyBoard(payload);
        }
      }
    },

    moveList() {
      this.$router.push({ name: "QnaList" });
    },
  },
  computed: {
    ...mapGetters(["qna", "loginUser"]),
    input() {
      return {
        subject: "",
        content: "",
      };
    },
  },
};
</script>

<style scoped>
.list-container {
  min-height: 64.2vh;
}
</style>
