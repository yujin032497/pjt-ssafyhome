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
    QNAS(state, payload) {
      state.qnas = payload.qnas;
    },
    QNA(state, payload) {
      state.qna = payload.qna;
    },
    SET_LOCATIONS(state, payload) {
      console.log(payload.locations);
      if (payload.locations.length !== 0) {
        let idx = 0;
        payload.locations.forEach((location) => {
          location.idx = idx;
          idx++;
        });
      }
      console.log(payload.locations);
      state.locations = payload.locations;
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
              alert("????????? ??????! ???????????? ??????????????? ???????????????.");
              break;
            case 500:
              alert("?????? ?????? ??????!");
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
      console.log("axios ?????? ??????");
      console.log(payload);
      http.post("/user/regist", payload.account).then((response) => {
        console.log(response);
        switch (response.status) {
          case 201:
            payload.cb();
            break;
          case 500:
            alert("?????? ?????? ??????");
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
            alert("?????? ?????? ??????");
            payload.err();
            break;
        }
      });
    },

    selectAccountList(context) {
      http.get("/user/list").then((response) => {
        console.log(response);
        switch (response.status) {
          case 200:
            context.commit({ type: "ACCOUNTS", accounts: response.data });
            break;
          case 500:
            alert("?????? ?????? ??????");
            break;
        }
      });
    },

    updateAccount(context, payload) {
      console.log("axios ?????? ??????");
      console.log(payload);
      http.put("/user/modify", payload.account).then((response) => {
        console.log(response);
        switch (response.status) {
          case 200:
            payload.cb();
            break;
          case 500:
            alert("?????? ?????? ??????");
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
            alert("?????????????????????!");
            payload.cb();
            break;
          case 500:
            alert("?????? ?????? ??????");
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
              alert("????????? ???????????????.");
              break;
            case 500:
              alert("?????? ??????!!!");
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
            alert("????????? ???????????????.");
            break;
          case 500:
            alert("?????? ??????!!!");
            break;
        }
      });
    },

    createBoard(context, payload) {
      console.log(payload.board);
      http.post("/board/write", payload.board).then((response) => {
        switch (response.status) {
          case 200:
            alert("?????? ???????????????.");
            payload.callback();
            break;
          case 400:
            alert("????????? ???????????????.");
            break;
          case 500:
            alert("?????? ??????!!!");
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
              alert("?????? ???????????????.");
              payload.callback();
              break;

            case 400:
              alert("????????? ???????????????.");
              break;

            case 500:
              alert("?????? ??????!!!");
              break;
          }
        });
    },

    deleteBoard(context, payload) {
      console.log(payload);
      http.delete(`/board/${payload.articleNo.articleNo}`).then((response) => {
        switch (response.status) {
          case 200:
            alert("?????? ???????????????.");
            payload.callback();
            break;

          case 400:
            alert("????????? ???????????????.");
            break;

          case 500:
            alert("?????? ??????!!!");
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
              alert("????????? ???????????????.");
              break;
            case 500:
              alert("?????? ??????!!!");
              break;
          }
        });
    },

    getLocations(context, payload) {
      console.log("??????????????????" + payload);
      // ????????? ???????????? API
      context.commit({ type: "SET_LOCATIONS", locations: [] });

      let response = {
        status: 200,
        locations: [
          {
            title: "??????1",
            price: 9999,
            address: "????????? ????????? ?????????",
          },
          {
            title: "??????2",
            price: 9999,
            address: "????????? ????????? ?????????",
          },
          {
            title: "??????3",
            price: 9999,
            address: "????????? ????????? ?????????",
          },
          {
            title: "??????4",
            price: 9999,
            address: "????????? ????????? ?????????",
          },
        ],
      };

      switch (response.status) {
        case 200:
          // payload
          // console.log(response.data);
          context.commit({
            type: "SET_LOCATIONS",
            locations: response.locations,
          }); // payload
          break;
      }
    },

    clearLocations(context) {
      context.commit({ type: "SET_LOCATIONS", locations: [] });
    },
  },

  modules: {},
});
