<template>
  <section>
    <div class="d-flex justify-content-center h-100">
      <b-card title="회원수정" class="h-auto w-auto mt-auto mb-auto">
        <user-form type="modify" :mode="mode"></user-form>
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
  data() {
    return {
      mode: "",
    };
  },
  created() {
    this.mode = this.$route.params.mode;
  },
};
</script>

<style scoped>
section {
  height: 66vh !important;
  align-content: center;
}
</style>
