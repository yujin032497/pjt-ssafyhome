import Vue from "vue";
import Vuex from "vuex";
import http from "@/util/http-common";
//import moment from "moment";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    loginUser: sessionStorage.getItem("loginUser")
      ? JSON.parse(sessionStorage.getItem("loginUser"))
      : {},
    account: {},
    accounts: [],
    boards: [],
    board: {},
    qnas: [],
    qna: {},
    locations: [],
    sidos: [{ value: null, text: "시/도 선택" }],
    guguns: [{ value: null, text: "구/군 선택" }],
    dongs: [{ value: null, text: "동 선택" }],
  },
  getters: {
    loginUser(state) {
      return state.loginUser;
    },
    account(state) {
      return state.account;
    },
    accounts(state) {
      return state.accounts;
    },
    boards(state) {
      return state.boards;
    },
    board(state) {
      return state.board;
    },
    qnas(state) {
      return state.qnas;
    },
    qna(state) {
      return state.qna;
    },
    locations(state) {
      return state.locations;
    },
  },
  mutations: {
    USER(state, payload) {
      state.loginUser = payload.loginUser;
    },
    ACCOUNT(state, payload) {
      state.account = payload.account;
    },
    ACCOUNTS(state, payload) {
      let idx = 0;
      payload.accounts.forEach((account) => {
        account.idx = idx;
        idx++;
      });
      console.log(payload);
      state.accounts = payload.accounts;
    },

    BOARDS(state, payload) {
      state.boards = payload.boards;
    },
    BOARD(state, payload) {
      state.board = payload.board;
    },

    CLEAR_LOCATIONS(state) {
      state.locations = [];
    },

    QNAS(state, payload) {
      state.qnas = payload.qnas;
    },
    QNA(state, payload) {
      state.qna = payload.qna;
    },
    SET_LOCATIONS(state, payload) {
      if (payload.locations.length !== 0) {
        let idx = 0;
        payload.locations.forEach((location) => {
          location.idx = idx;
          idx++;
        });
      }
      state.locations = payload.locations;
    },
    CLEAR_SIDO_LIST(state) {
      state.sidos = [{ value: null, text: "시/도 선택" }];
    },
    CLEAR_GUGUN_LIST(state) {
      state.guguns = [{ value: null, text: "구/군 선택" }];
    },
    CLEAR_DONG_LIST(state) {
      state.guguns = [{ value: null, text: "동 선택" }];
    },
    SET_SIDO_LIST(state, sidos) {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST(state, guguns) {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST(state, dongs) {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
  },
  actions: {
    getUser(context, payload) {
      http
        .post("/user/login", payload.user)
        .then((response) => {
          console.log(response.status);
          switch (response.status) {
            case 200:
              console.log(response.data);
              sessionStorage.setItem(
                "loginUser",
                JSON.stringify(response.data),
              );
              context.commit({ type: "USER", loginUser: response.data });
              payload.callback();
              break;
          }
        })
        .catch((err) => {
          console.log(err);
          switch (err.response.status) {
            case 401:
              alert("로그인 실패! 아이디와 비밀번호를 확인하세요.");
              break;
            case 500:
              alert("내부 서버 에러!");
              payload.callback();
              break;
          }
        });
    },

    logout(context, payload) {
      sessionStorage.removeItem("loginUser");
      context.commit({ type: "USER", loginUser: {} });
      payload.callback();
    },

    createAccount(context, payload) {
      console.log("axios 통신 작업");
      console.log(payload);
      http.post("/user/regist", payload.account).then((response) => {
        console.log(response);
        switch (response.status) {
          case 201:
            payload.cb();
            break;
          case 500:
            alert("내부 서버 오류");
            payload.err();
            break;
        }
      });
    },

    selectAccount(context, payload) {
      http.get(`/user/detail/${payload.data.userId}`).then((response) => {
        switch (response.status) {
          case 200:
            context.commit({ type: "ACCOUNT", account: response.data });
            break;
          case 500:
            alert("내부 서버 에러");
            payload.err();
            break;
        }
      });
    },

    selectAccountList(context, payload) {
      console.log(payload);
      http
        .get(`/user/list?key=${payload.key}&value=${payload.value}`)
        .then((response) => {
          console.log(response);
          switch (response.status) {
            case 200:
              context.commit({ type: "ACCOUNTS", accounts: response.data });
              break;
            case 204:
              context.commit({ type: "ACCOUNTS", accounts: [] });
              break;
            case 500:
              alert("내부 서버 에러");
              break;
          }
        });
    },

    updateAccount(context, payload) {
      console.log("axios 통신 작업");
      console.log(payload);
      http.put("/user/modify", payload.account).then((response) => {
        console.log(response);
        switch (response.status) {
          case 200:
            payload.cb();
            break;
          case 500:
            alert("내부 서버 오류");
            payload.err();
            break;
        }
      });
    },

    deleteAccount(context, payload) {
      http.delete(`/user/delete/${payload.id}`).then((response) => {
        console.log(response);
        switch (response.status) {
          case 200:
            alert("삭제되었습니다!");
            payload.cb();
            break;
          case 500:
            alert("내부 서버 오류");
            payload.cb();
            break;
        }
      });
    },

    getBoards(context, payload) {
      console.log(payload);
      http
        .get(
          `/board/list?key=${payload.key ? payload.key : null}&value=${
            payload.value ? payload.value : ""
          }&pgno=${payload.pgno}&spp=${payload.spp}`,
          payload,
        )
        .then((response) => {
          console.log(response.data);
          switch (response.status) {
            case 200:
              console.log(response.data);
              context.commit({ type: "BOARDS", boards: response.data }); // payload
              break;
            case 204:
              context.commit({ type: "BOARDS", boards: {} }); // payload
              break;
            case 400:
              alert("잘못된 요청입니다.");
              break;
            case 500:
              alert("서버 오류!!!");
              break;
          }
        });
    },

    getBoard(context, payload) {
      console.log(payload);
      http.get(`/board/view/${payload.articleNo}`).then((response) => {
        console.log(response.data);
        switch (response.status) {
          case 200:
            context.commit({ type: "BOARD", board: response.data }); // payload
            break;

          case 400:
            alert("잘못된 요청입니다.");
            break;
          case 500:
            alert("서버 오류!!!");
            break;
        }
      });
    },

    createBoard(context, payload) {
      console.log(payload.board);
      http.post("/board/write", payload.board).then((response) => {
        switch (response.status) {
          case 200:
            alert("등록 되었습니다.");
            payload.callback();
            break;
          case 400:
            alert("잘못된 요청입니다.");
            break;
          case 500:
            alert("서버 오류!!!");
            break;
        }
      });
    },

    modifyBoard(context, payload) {
      http
        .put(`/board/modify/${payload.board.articleNo}`, payload.board)
        .then((response) => {
          switch (response.status) {
            case 200:
              alert("수정 되었습니다.");
              payload.callback();
              break;

            case 400:
              alert("잘못된 요청입니다.");
              break;

            case 500:
              alert("서버 오류!!!");
              break;
          }
        });
    },

    deleteBoard(context, payload) {
      console.log(payload);
      http.delete(`/board/${payload.articleNo.articleNo}`).then((response) => {
        switch (response.status) {
          case 200:
            alert("삭제 되었습니다.");
            payload.callback();
            break;

          case 400:
            alert("잘못된 요청입니다.");
            break;

          case 500:
            alert("서버 오류!!!");
            break;
        }
      });
    },

    getQnas(context, payload) {
      console.log(payload);
      http
        .get(
          `/qna/list?key=${payload.key ? payload.key : null}&value=${
            payload.value ? payload.value : ""
          }&pgno=${payload.pgno}&spp=${payload.spp}`,
          payload,
        )
        .then((response) => {
          console.log(response.data);
          switch (response.status) {
            case 200:
              console.log(response.data);
              context.commit({ type: "QNAS", qnas: response.data }); // payload
              break;
            case 204:
              context.commit({ type: "QNAS", qnas: {} }); // payload
              break;
            case 400:
              alert("잘못된 요청입니다.");
              break;
            case 500:
              alert("서버 오류!!!");
              break;
          }
        });
    },

    getLocations(context, payload) {
      // 장소를 가져오는 API
      context.commit({ type: "CLEAR_LOCATIONS" });

      http
        .get(
          `/map?gubn=${payload.gubn}&type=${payload.type}&dongCode=${payload.dongCode}`,
        )
        .then((response) => {
          switch (response.status) {
            case 200:
              console.log(response.data);
              context.commit({
                type: "SET_LOCATIONS",
                locations: response.data,
              }); // payload

              break;
          }
        });
    },

    clearLocations(context) {
      context.commit({ type: "CLEAR_LOCATIONS" });
    },

    getSido(context) {
      http
        .get(`/map/sido`)
        .then(({ data }) => {
          context.commit("SET_SIDO_LIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getGugun(context, sido) {
      http
        .get(`/map/gugun?sido=${sido}`)
        .then(({ data }) => {
          context.commit("SET_GUGUN_LIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getDong(context, gugun) {
      http
        .get(`/map/dong?gugun=${gugun}`)
        .then(({ data }) => {
          context.commit("SET_DONG_LIST", data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },

  modules: {},
});
