<template>
  <div class="list-container">
    <write-form type="create"></write-form>
  </div>
</template>

<script>
export default {
  // Dynamic module loading을 통해 component를 불러옴
  components: {
    "write-form": () => import("@/components/qna/include/QnaWriteForm.vue"),
  },
};
</script>
<style scoped>
.list-container {
  min-height: 62.5vh;
}
</style>
