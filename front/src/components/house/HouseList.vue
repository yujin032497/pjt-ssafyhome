<template>
  <div class="main-div">
    <b-navbar>
      <div
        class="ml-auto pl-3 justify-content-between d-flex"
        style="width: 514px">
        <b-navbar-nav>
          <nav-item
            :class="{ selected: contentType === 'apt' }"
            :content="content[0]"
            :subContent="subContent"
            :typeName="'apt'"
            @changeType="changeType" />
          <nav-item
            :class="{ selected: contentType === 'house' }"
            :content="content[1]"
            :subContent="subContent"
            :typeName="'house'"
            @changeType="changeType" />
        </b-navbar-nav>

        <div class="test"></div>
      </div>
    </b-navbar>
  </div>
</template>

<script>
import NavItem from "@/components/house/import/navItem";
import KakaoMap from "@/components/house/KakaoMap";
import { mapActions } from "vuex";

export default {
  components: { NavItem },
  data() {
    return {
      content: ["아파트", "연립/다세대"],
      subContent: "매매/전월세",
      contentType: "apt",
    };
  },
  methods: {
    ...mapActions({ clear: "clearLocations" }),
    changeType(type) {
      this.contentType = type;
      this.clear();
    },
  },
};
</script>

<style scoped>
.main-div {
  min-height: 66vh;
}

.map {
  height: 89vh;
  width: 100%;
  margin-bottom: 20px;
}

.test {
  width: 20%;
  height: 100%;
  background-color: #ff0000;
}

.selected {
  color: var(--deep-skyblue);
}
</style>
