<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col class="text-center">
        <h3 v-if="type == 'create'" class="underline">글쓰기</h3>
        <h3 v-else class="underline">글수정</h3>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-form>
          <b-form-group
            label-cols="12"
            id="subject-group"
            label="제목:"
            label-for="subject"
            description="제목을 입력하세요.">
            <b-form-input
              v-if="type == 'create'"
              id="subject"
              ref="subject"
              v-model="input.subject"
              type="text"
              required
              placeholder="제목 입력..." />
            <b-form-input
              v-if="type == 'modify'"
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
            label="내용:"
            label-for="content"
            description="내용을 입력하세요.">
            <b-form-textarea
              id="content"
              v-model="input.content"
              placeholder="내용 입력..."
              rows="10"
              max-rows="15"
              ref="content"></b-form-textarea>
          </b-form-group>

          <b-button
            v-if="type == 'create'"
            variant="primary"
            class="m-1"
            @click="validate"
            >글등록
          </b-button>
          <b-button v-else variant="success" class="m-1" @click="validate"
            >글수정
          </b-button>
          <b-button variant="primary" class="m-1" @click="moveList"
            >목록
          </b-button>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  props: {
    // 부모 component로 부터 전달받은 type 정보를 받아옴
    type: { type: String },
  },
  methods: {
    ...mapActions(["createBoard", "modifyBoard", "getBoards"]),
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
        const payload = {
          board: {
            articleNo: this.$route.params.articleNo,
            subject: this.input.subject,
            content: this.input.content,
            userId: "admin",
          },
          callback: () => {
            console.log("callback!! 호출", this);

            this.moveList();
          },
        };
        if (this.type == "create") {
          this.createBoard(payload);
        } else {
          this.modifyBoard(payload);
        }
      }
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
    created() {
      if (this.type === "modify") {
        const articleNo = this.$route.params;
        this.getBoard(articleNo);
      }
    },
  },
  computed: {
    ...mapGetters(["board", "loginUser"]),
    input() {
      if (this.type == "modify") {
        // 브라우저 새로고침을 하게되면 state에 저장된 데이터가 사라짐
        // Vuex의 state.book 객체가 비어있다면 서버에 요청하기
        //this.getBook(this.$route.params.isbn);

        // 수정화면일 경우
        return { ...this.board }; // vuex의 state.book 객체에 직접 접근하는걸 막기 위해 Deep Copy
      } else {
        // 등록화면일 경우
        return {
          userId: "admin",
          subject: "",
          content: "",
        };
      }
    },
  },
};
</script>
