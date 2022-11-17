<template>
  <section>
    <div class="d-flex justify-content-center h-100">
      <b-card title="회원수정" class="h-auto w-auto mt-auto mb-auto">
        <user-form type="modify"></user-form>
      </b-card>
    </div>
  </section>
</template>

<script>
import UserForm from "@/components/user/item/UserForm";
export default {
  components: {
    UserForm,
  },
};
</script>

<style scoped>
section {
  height: 64vh !important;
  align-content: center;
}
</style>
