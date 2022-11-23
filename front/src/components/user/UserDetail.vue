<template>
  <section class="d-flex justify-content-center h-100">
    <b-card title="회원정보" class="h-auto w-auto mt-auto mb-auto">
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
              v-model="input.id"
              readonly="readonly" />
          </div>
        </div>

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"
              ><b-icon icon="emoji-smile"></b-icon
            ></span>
            <input
              type="text"
              id="username"
              placeholder="이름..."
              v-model="input.name"
              readonly="readonly" />
          </div>
        </div>

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"
              ><b-icon icon="house-fill"></b-icon
            ></span>
            <input
              type="text"
              id="address"
              placeholder="주소..."
              v-model="input.address"
              readonly="readonly" />
          </div>
        </div>

        <div class="input-group form-group">
          <div class="input-group-prepend">
            <span class="input-group-text"
              ><b-icon icon="telephone-fill"></b-icon
            ></span>
            <input
              type="text"
              id="usertel"
              placeholder="전화번호(-포함)"
              v-model="input.phone"
              readonly="readonly" />
          </div>
        </div>

        <div class="b-card-nav w-100">
          <button class="login_btn btn p-sm-1" @click="moveModify">수정</button>
          <button class="login_btn btn p-sm-1" @click="moveDelete">삭제</button>
          <button class="login_btn btn p-sm-1" @click="moveHome">홈으로</button>
        </div>
      </b-card-body>
    </b-card>
  </section>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  methods: {
    ...mapActions({
      selectAccount: "selectAccount",
      deleteAccount: "deleteAccount",
      logout: "logout",
    }),
    moveModify() {
      this.$router.push({ path: "/user/modify/" + this.input.id });
    },
    moveDelete() {
      const payload = {
        id: this.input.id,
        cb: () => {
          if (this.loginUser.id === "ssafyadmin") {
            alert("삭제되었습니다.");
            this.$router.push({ name: "home" });
            return;
          }
          this.logout({
            callback: () => {
              alert("삭제되었습니다. 이용해주셔서 감사합니다.");
              this.$router.push({ name: "home" });
            },
          });
        },
      };
      this.deleteAccount(payload);
    },
    moveHome() {
      this.$router.push({ name: "home" });
    },
  },
  created() {
    const userId = this.$route.params.id;
    const payload = {
      data: {
        userId,
      },
      err: () => {
        this.$router.push({ name: "home" });
      },
    };
    this.selectAccount(payload);
  },
  computed: {
    ...mapGetters({ account: "account", loginUser: "loginUser" }),
    input() {
      return { ...this.account };
    },
  },
};
</script>

<style scoped>
section {
  height: 66vh !important;
  align-content: center;
}

.input-group-prepend span {
  width: 41px;
  background-color: #535893;
  height: 40px;
  color: white;
  border: 1px !important;
}

.input-group-prepend {
  width: 100%;
}

input {
  width: 100% !important;
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
