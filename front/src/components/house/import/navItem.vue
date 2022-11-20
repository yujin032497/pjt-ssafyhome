<template>
  <button class="nav-item mx-3" @click="update">
    <p class="content">{{ content }}</p>
    <p class="sub-content">{{ subContent }}</p>
  </button>
</template>

<script>
export default {
  name: "navItem.vue",
  props: {
    content: { type: String, default: "" },
    subContent: { type: String, default: "" },
    typeName: { type: String },
  },
  methods: {
    update() {
      console.log("this");
      this.$emit("changeType", this.typeName);
    },
  },
};
</script>

<style scoped>
button {
  background: white;
  text-align: left;
  border: 0;
}
.nav-item > .content {
  font-size: 1.5rem;
  margin-bottom: -2px;
  font-weight: bold;
}

.nav-item > .sub-content {
  font-size: 1rem;
}
</style>
