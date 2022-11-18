import jwtDecode from "jwt-decode";
import { login } from "@/api/user";

const userStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,
  },
  getters: {},
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];

            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        },
      );
    },
  },
  async getUserInfo({ commit, dispatch }, token) {
    let decodeToken = jwtDecode(token);
    // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
    await findById(
      decodeToken.userid,
      ({ data }) => {
        if (data.message === "success") {
          commit("SET_USER_INFO", data.userInfo);
          // console.log("3. getUserInfo data >> ", data);
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      async (error) => {
        console.log(
          "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
          error.response.status,
        );
        commit("SET_IS_VALID_TOKEN", false);
        await dispatch("tokenRegeneration");
      },
    );
  },
};

export default userStore;
