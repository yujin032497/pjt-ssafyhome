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
    path: "/board",
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
    ],
  },
  {
    path: "/apt",
    name: "AptView",
    redirect: "/apt/list",
    component: () => import("@/views/CommonView.vue"),
    children: [
      {
        path: "list",
        name: "AptList",
        component: () => import("@/components/apt/AptList.vue"),
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