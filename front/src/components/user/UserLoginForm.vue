<template>
  <section>
    <div class="d-flex justify-content-center h-100">
      <b-card title="로그인" class="h-auto w-auto mt-auto mb-auto">
        <b-card-body>
          <div class="input-group form-group">
            <div class="input-group-prepend">
              <span class="input-group-text"
                ><b-icon icon="person-fill"></b-icon
              ></span>
              <input
                type="text"
                id="userid"
                placeholder="아이디..."
                v-model="input.id" />
            </div>
          </div>
          <p class="text-danger text-alert" v-show="!isIdPos">
            아이디를 입력하세요
          </p>

          <div class="input-group form-group">
            <div class="input-group-prepend">
              <span class="input-group-text"
                ><b-icon icon="key-fill"></b-icon
              ></span>
              <input
                type="password"
                id="userpw"
                placeholder="비밀번호(6자리 이상)"
                v-model="input.pw" />
            </div>
          </div>
          <p class="text-danger text-alert" v-show="!isPwPos">
            비밀번호를 6자 이상 입력하세요
          </p>

          <div class="float-right">
            <button class="login_btn btn p-sm-1 mr-1" @click="moveRegist">
              회원가입
            </button>
            <button
              class="login_btn btn p-sm-1"
              id="loginBtn"
              type="button"
              @click="login">
              로그인
            </button>
          </div>
        </b-card-body>
      </b-card>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      isIdPos: true,
      isPwPos: true,
    };
  },
  methods: {
    ...mapActions({ getUser: "getUser" }),
    checkValidate() {
      this.isIdPos = this.input.id !== "";
      this.isPwPos = this.input.pw && this.input.pw.length >= 6;
      return this.isIdPos && this.isPwPos;
    },
    login() {
      if (this.checkValidate()) {
        // Vuex 호출
        this.getUser({
          user: { id: this.input.id, password: this.input.pw },
          callback: () => this.moveHome(),
        });
      }
    },
    moveHome() {
      this.$router.push({ name: "home" });
    },
    moveRegist() {
      this.$router.push({ path: "/user/regist" });
    },
  },
  computed: {
    ...mapGetters({ loginUser: "loginUser" }),
    input() {
      return {
        id: "",
        pw: "",
      };
    },
  },
};
</script>

<style scoped>
section {
  height: 64vh !important;
  align-content: center;
}

.input-group-prepend span {
  width: 41px;
  background-color: #535893;
  height: 40px;
  color: white;
  border: 1px !important;
}

input {
  width: 210px;
  padding-left: 0.8rem;
  border: 1px solid #ced4da !important;
}

.login_btn {
  color: white;
  background-color: #535893;
  width: 100px;
  border: 1px solid #ced4da !important;
}

.login_btn:hover {
  color: black;
  background-color: white;
}

.text-alert {
  font-size: 0.8rem;
}
</style>
