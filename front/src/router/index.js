import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/board", // FAQ
    name: "Board",
    redirect: "/board/list",
    component: () => import("@/views/CommonView.vue"),
    children: [
      {
        path: "list",
        name: "BoardList",
        component: () => import("@/components/board/BoardList.vue"),
      },
      {
        path: "detail/:articleNo",
        name: "BoardDetail",
        component: () => import("@/components/board/BoardDetail.vue"),
      },
      {
        path: "write",
        name: "BoardWrite",
        component: () => import("@/components/board/BoardWrite.vue"),
      },
      {
        path: "modify/:articleNo",
        name: "BoardModify",
        component: () => import("@/components/board/BoardModify.vue"),
      },
    ],
  },
  {
    path: "/user",
    name: "UserView",
    redirect: "/user/list",
    component: () => import("@/views/CommonView.vue"),
    children: [
      {
        path: "list",
        name: "UserList",
        component: () => import("@/components/user/UserList.vue"),
      },
      {
        path: "detail/:id",
        name: "UserDetail",
        component: () => import("@/components/user/UserDetail.vue"),
      },
      {
        path: "modify/:id/:mode",
        name: "UserModifyAdm",
        component: () => import("@/components/user/UserModify.vue"),
      },
      {
        path: "modify/:id",
        name: "UserModify",
        component: () => import("@/components/user/UserModify.vue"),
      },
      {
        path: "loginForm",
        name: "UserLoginForm",
        component: () => import("@/components/user/UserLoginForm.vue"),
      },
      {
        path: "regist",
        name: "UserRegist",
        component: () => import("@/components/user/UserRegist.vue"),
      },
      {
        path: "find",
        name: "UserFind",
        component: () => import("@/components/user/UserFind.vue"),
      },
    ],
  },
  {
    path: "/qna", // 1:1문의하기
    name: "Qna",
    redirect: "/qna/list",
    component: () => import("@/views/CommonView.vue"),
    children: [
      {
        path: "list",
        name: "QnaList",
        component: () => import("@/components/qna/QnaList.vue"),
      },
      {
        path: "detail/:articleNo",
        name: "QnaDetail",
        component: () => import("@/components/qna/QnaDetail.vue"),
      },
      {
        path: "write",
        name: "QnaWrite",
        component: () => import("@/components/qna/QnaWrite.vue"),
      },
      {
        path: "modify/:articleNo",
        name: "QnaModify",
        component: () => import("@/components/qna/QnaModify.vue"),
      },
    ],
  },
  {
    path: "/house",
    name: "HouseView",
    redirect: "/house/list",
    component: () => import("@/views/CommonView.vue"),
    children: [
      {
        path: "list",
        name: "HouseList",
        component: () => import("@/components/house/HouseList.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
