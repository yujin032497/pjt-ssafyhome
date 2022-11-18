<template>
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
    <p class="text-danger text-alert" v-show="!isIdPos">아이디를 입력하세요</p>
    <div class="input-group form-group">
      <div class="input-group-prepend">
        <span class="input-group-text"><b-icon icon="key-fill"></b-icon></span>
        <input
          type="password"
          id="userpw"
          placeholder="비밀번호(6자리 이상)"
          v-model="input.password" />
      </div>
    </div>
    <p class="text-danger text-alert" v-show="!isPwPos">
      비밀번호를 6자 이상 입력하세요
    </p>

    <div class="input-group form-group">
      <div class="input-group-prepend">
        <span class="input-group-text"
          ><b-icon icon="emoji-smile"></b-icon
        ></span>
        <input
          type="text"
          id="username"
          placeholder="이름..."
          v-model="input.name" />
      </div>
    </div>
    <p class="text-danger text-alert" v-show="!isNamePos">이름을 입력하세요</p>

    <div class="input-group form-group">
      <div class="input-group-prepend">
        <span class="input-group-text"
          ><b-icon icon="house-fill"></b-icon
        ></span>
        <input
          type="text"
          id="address"
          placeholder="주소..."
          v-model="input.address" />
      </div>
    </div>
    <p class="text-danger text-alert" v-show="!isAddrPos">주소를 입력하세요</p>

    <div class="input-group form-group">
      <div class="input-group-prepend">
        <span class="input-group-text"
          ><b-icon icon="telephone-fill"></b-icon
        ></span>
        <input
          type="text"
          id="usertel"
          placeholder="전화번호(-포함)"
          v-model="input.phone" />
      </div>
    </div>
    <p class="text-danger text-alert" v-show="!isTelPos">
      전화번호를 입력하세요
    </p>

    <div class="float-right">
      <button class="login_btn btn p-sm-1 mr-1" @click="submitAccount">
        {{ type === "regist" ? "회원가입" : "회원수정" }}
      </button>
      <button class="login_btn btn p-sm-1" @click="moveHome">취소</button>
    </div>
  </b-card-body>
</template>

<script>
import { mapActions, mapGetters } from "vuex";

export default {
  name: "UserForm.vue",
  data() {
    return {
      isIdPos: true,
      isPwPos: true,
      isNamePos: true,
      isAddrPos: true,
      isTelPos: true,
    };
  },
  props: {
    type: { type: String },
  },
  methods: {
    ...mapActions({
      createAccount: "createAccount",
      selectAccount: "selectAccount",
      updateAccount: "updateAccount",
      logout: "logout",
    }),
    moveHome() {
      this.$router.push({ name: "home" });
    },
    checkValidate() {
      this.isIdPos = this.input.id !== "";
      this.isPwPos = this.input.password && this.input.password.length >= 6;
      this.isNamePos = this.input.name !== "";
      this.isAddrPos = this.input.address !== "";
      this.isTelPos = this.input.phone.length >= 11;

      return (
        this.isIdPos &&
        this.isPwPos &&
        this.isNamePos &&
        this.isAddrPos &&
        this.isTelPos
      );
    },

    submitAccount() {
      if (this.checkValidate()) {
        if (this.type === "regist") {
          this.createAccount({
            account: this.input,
            cb: () => {
              alert("회원가입되었습니다.");
              this.$router.push({ name: "home" });
            },
          });
        } else {
          this.updateAccount({
            account: this.input,
            cb: () => {
              this.logout({
                callback: () => {
                  alert(
                    "회원정보가 수정되어 로그아웃되었습니다. 다시 로그인 해주세요.",
                  );
                  this.$router.push({ name: "home" });
                },
              });
            },
          });
        }
      }
    },
  },
  created() {
    if (this.type === "modify") {
      const userId = this.$route.params.id;
      const payload = {
        data: { userId: userId },
        err: () => {
          this.$router.push({ name: "home" });
        },
      };
      this.selectAccount(payload);
    }
  },
  computed: {
    ...mapGetters({ account: "account" }),
    input() {
      if (this.type === "modify") {
        return { ...this.account };
      } else {
        return {
          id: "",
          pw: "",
          name: "",
          address: "",
          tel: "",
        };
      }
    },
  },
};
</script>

<style scoped>
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
