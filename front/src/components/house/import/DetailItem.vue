<template>
  <div class="item-component">
    <div class="dropdown-divider"></div>
    <p>{{ detailItem.floor }}층</p>
    <p>{{ detailItem.dealAmount }}만원</p>
    <p>{{ detailItem.area }}</p>
    <p>{{ detailItem.dealYmd }}</p>
  </div>
</template>

<script>
export default {
  name: "DetailItem",
  props: {
    detailItem: {
      type: Object,
      dealAmount: { type: String, default: "0" },
      floor: { type: Number, default: 0 },
      dealYmd: { type: String },
    },
    expanded: {
      type: Boolean,
      default: false,
    },
  },
  mounted() {
    console.log(this.detailItem);
  },
  filters: {
    filterPrice: (value) => {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style scoped>
.item-component {
  width: 100%;
  height: auto;
  margin-left: 1rem;
  margin-top: 10px;
}
</style>
