<template>
  <div class="nav-item mx-3">
    <p class="content">{{ content }}</p>
    <p class="sub-content">{{ subContent }}</p>
  </div>
</template>

<script>
export default {
  name: "navItem.vue",
  props: {
    content: { type: String, default: "" },
    subContent: { type: String, default: "" },
    url: { type: String, default: "" },
  },
};
</script>

<style scoped>
.nav-item > .content {
  font-size: 1.5rem;
  margin-bottom: -2px;
  font-weight: bold;
}

.nav-item > .sub-content {
  font-size: 1rem;
}
</style>
