<template>
  <div class="main-div">
    <b-navbar>
      <b-navbar-nav>
        <nav-item :content="content[0]" :subContent="subContent" :url="''" />
        <nav-item :content="content[1]" :subContent="subContent" :url="''" />
      </b-navbar-nav>
    </b-navbar>
    <div class="d-flex">
      <div id="map"></div>
      <div id="map-nav"></div>
    </div>
    <section></section>
  </div>
</template>

<script>
import NavItem from "@/components/house/import/navItem";
export default {
  components: { NavItem },
  data() {
    return {
      content: ["아파트", "연립/다세대"],
      subContent: "매매/전월세",
    };
  },
};
</script>

<style scoped>
.main-div {
  min-height: 66vh;
}

#map {
  width: 70%;
  height: 66vh;
  background-color: #2eca6a;
}

#map-nav {
  width: 30%;
  height: 66vh;
  background-color: var(--deep-skyblue);
}
</style>
