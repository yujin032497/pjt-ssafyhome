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
    path: "/board", // 게시판
    name: "Board",
    redirect: "/board",
    component: () => import("@/views/CommonView.vue"),
    children: [
      {
        path: "/faq",
        name: "FAQ",
        redirect: "/board/faq/list",
        component: () => import("@/views/CommonView.vue"),
        children: [
          //CRUD
        ],
      },
      {
        path: "/qna",
        name: "QNA",
        redirect: "/board/qna/list",
        component: () => import("@/views/CommonView.vue"),
        children: [
          //CRUD
        ],
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
