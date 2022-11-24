<template>
  <section>
    <div class="d-flex justify-content-center h-100">
      <b-card class="h-auto w-25 mt-auto mb-auto">
        <b-card-body>
          <div>
            <p class="text-left heading">아이디 찾기</p>
            <p>이름</p>
            <b-input v-model="findIdName" ref="idName"></b-input>
            <p v-if="checkIdName" class="text-alert">이름을 확인하세요.</p>
            <br />
            <p>전화번호</p>
            <b-input v-model="findIdPhone" ref="idPhone"></b-input>
            <p v-if="checkIdPhone" class="text-alert">
              전화번호를 010-XXXX-XXXX 형식으로 입력하세요.
            </p>
            <br />
            <div class="text-center">
              <b-button @click="findId">아이디 찾기</b-button>

              <b-modal id="idModal" hide-footer>
                <template #modal-title> 아이디 조회 결과 </template>
                <div class="d-block text-center">
                  <span
                    >아이디 찾기 결과는
                    <span style="font-weight: bold">{{ res }} </span
                    >입니다.</span
                  >
                </div>
                <br />
                <div class="w-100 d-flex">
                  <b-button class="ml-auto" block @click="moveLogin"
                    >로그인하기</b-button
                  >
                </div>
              </b-modal>
            </div>
          </div>
          <br />
          <div>
            <p class="text-left heading">비밀번호 찾기</p>
            <p>이름</p>
            <b-input v-model="findPwName" ref="pwName"></b-input>
            <p v-if="checkPwName" class="text-alert">이름을 입력하세요.</p>
            <br />
            <p>아이디</p>
            <b-input v-model="findPwId" ref="pwId"></b-input>
            <p v-if="checkPwId" class="text-alert">아이디를 입력하세요.</p>
            <br />
            <div class="text-center">
              <b-button @click="findPw">비밀번호 찾기</b-button>
              <b-modal id="pwModal" hide-footer>
                <template #modal-title> 비밀번호 조회 결과 </template>
                <div class="d-block text-center">
                  <span>새로운 비밀번호를 입력하세요.</span>
                  <b-form-input
                    v-model="password"
                    ref="pwd"
                    :type="'password'"></b-form-input>
                  <p v-if="checkPwd" class="text-alert">
                    비밀번호를 6자 이상 입력하세요.
                  </p>
                </div>
                <br />
                <div class="w-100 d-flex">
                  <b-button class="ml-auto" block @click="checkPw"
                    >확인</b-button
                  >
                </div>
              </b-modal>
            </div>
          </div>
        </b-card-body>
      </b-card>
    </div>
  </section>
</template>

<script>
import http from "@/util/http-common";
export default {
  name: "UserFind",
  data() {
    return {
      findIdName: "",
      findIdPhone: "",
      findPwName: "",
      findPwId: "",
      checkIdName: false,
      checkIdPhone: false,
      checkPwName: false,
      checkPwId: false,
      password: "",
      checkPwd: false,
      res: "",
    };
  },
  mounted() {
    this.findIdName = "";
    this.findIdPhone = "";
    this.findPwName = "";
    this.findPwId = "";
  },
  methods: {
    findId() {
      let cnt = 0;
      let flag = false;
      for (let i = 0; i < this.findIdPhone.length; i++) {
        if (this.findIdPhone.charAt(i) === "-") {
          cnt++;
        }
      }
      if (cnt !== 2) {
        flag = true;
      }
      if (!this.findIdName) {
        this.checkIdName = true;
        this.$refs.idName.focus();
      } else if (!this.findIdPhone && this.findIdPhone.length >= 14) {
        this.checkIdPhone = true;
        this.$refs.idPhone.focus();
      } else if (flag) {
        this.checkIdPhone = true;
        this.$refs.idPhone.focus();
      } else {
        http
          .post(`/user/find/id`, {
            name: this.findIdName,
            phone: this.findIdPhone,
          })
          .then(({ data }) => {
            this.$bvModal.show("idModal");
            this.res = data;
            console.log(data);
          })
          .catch(() => {
            this.checkIdName = true;
            this.checkIdPhone = true;
          });
      }
    },
    findPw() {
      if (!this.findPwName) {
        this.checkPwName = true;
        this.$refs.pwName.focus();
      } else if (!this.findPwId) {
        this.checkPwId = true;
        this.$refs.pwId.focus();
      } else {
        http
          .post(`/user/find/pw`, {
            name: this.findPwName,
            id: this.findPwId,
          })
          .then(({ data }) => {
            this.res = data;
            this.$bvModal.show("pwModal");
          })
          .catch(() => {
            this.checkPwName = true;
            this.checkPwId = true;
          });
      }
    },
    moveLogin() {
      this.$router.push({ name: "UserLoginForm" });
    },
    checkPw() {
      if (!this.password || this.password.length < 6) {
        this.checkPwd = true;
        this.$refs.pwd.focus();
      } else {
        http
          .put(`/user/updatePw`, {
            id: this.res,
            password: this.password,
          })
          .then(() => {
            alert("비밀번호가 변경되었습니다. 다시 로그인해주세요.");
            this.moveLogin();
          });
      }
    },
  },
};
</script>

<style scoped>
section {
  height: 66vh !important;
  align-content: center;
}

.heading {
  font-weight: bold;
  font-size: 1.5rem;
}

button {
  color: white;
  background-color: #535893;
  width: 120px;
  border: 1px solid #ced4da !important;
}

button:hover {
  color: black;
  background-color: white;
}

.text-alert {
  font-size: 0.8rem;
  color: darkred;
}

a:hover {
  cursor: pointer;
}
</style>
