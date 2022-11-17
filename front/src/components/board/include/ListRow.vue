<template>
  <b-tr class="text-center">
    <b-td>{{ articleNo }}</b-td>
    <b-th>
      <router-link
        :to="{ name: 'BoardDetail', params: { articleNo: articleNo } }"
        >{{ subject }}</router-link
      >
    </b-th>
    <b-td>{{ userName }}</b-td>
    <b-td>{{ hit }}</b-td>
    <b-td>{{ registerTime | dateFormat }}</b-td>
  </b-tr>
</template>

<script>
import moment from "moment";
export default {
  // 부모 component로 부터 전달받은 도서정보
  props: {
    articleNo: Number,
    subject: String,
    hit: Number,
    registerTime: String,
    userId: String,
    userName: String,
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
};
</script>

<style scope></style>
