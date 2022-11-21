<template>
  <b-tr class="text-center">
    <b-td>{{ articleNo }}</b-td>
    <b-th>
      <router-link
        :to="{ name: 'QnaDetail', params: { articleNo: articleNo } }"
        >{{ subject }}</router-link
      >
    </b-th>
    <b-td>{{ registerTime | dateFormat }}</b-td>
    <b-td>{{ state }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";
export default {
  // 부모 component로 부터 전달받은 도서정보
  props: {
    article_no: Number,
    userId: String,
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

<style scope></style>
