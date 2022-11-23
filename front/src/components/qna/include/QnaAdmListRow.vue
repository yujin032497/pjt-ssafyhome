<template>
  <b-tr class="text-center">
    <b-td class="center font-bold">
      <router-link :to="{ name: 'QnaDetail', params: { qnaNo: qnaNo } }">{{
        subject
      }}</router-link>
    </b-td>
    <b-td class="center text-center">{{ userId }}</b-td>
    <b-td class="center text-center">{{ registerTime | dateFormat }}</b-td>
    <b-td :class="state === '1' ? 'wait' : 'completed'"
      ><b-alert show class="state">{{
        state === "1" ? "답변미완료" : "답변완료"
      }}</b-alert></b-td
    >
  </b-tr>
</template>

<script>
import moment from "moment";
export default {
  // 부모 component로 부터 전달받은 도서정보
  props: {
    qnaNo: Number,
    subject: String,
    userId: String,
    userName: String,
    registerTime: String,
    state: String,
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
};
</script>

<style scope>
.center {
  vertical-align: middle !important;
}
.wait {
  color: #000;
}
.completed {
  color: blue;
}
.state {
  background-color: var(--skyblue);
}
.font-bold {
  font-weight: bold;
  text-align: left;
  padding-left: 3rem !important;
}
</style>
