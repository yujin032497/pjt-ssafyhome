<template>
  <div class="maps d-flex">
    <div class="map-in">
      <div id="map"></div>
    </div>

    <div class="map-info">
      <div class="search-window p-3">
        <div
          class="search-bar p-auto d-flex align-content-center justify-content-center">
          <b-button @click="search"
            >검색 테스트 버튼 {{ contentType }}</b-button
          >
        </div>
        <div class="tag-bar py-1 d-flex">
          <div class="tag text-center">검색태그</div>
          <div class="tag text-center">검색태그</div>
        </div>
      </div>
      <div>
        <div class="search-items py-2 px-3">
          <item-content
            v-for="(data, index) in locationList"
            :key="index"
            :item="data"
            @detailIdx="detail" />

          <div v-if="locationList.length === 0" class="text-center">
            표시할 컨텐츠가 없습니다.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ItemContent from "@/components/house/ItemContent";
import { mapActions, mapGetters } from "vuex";

export default {
  data() {
    return {
      map: null,
      tag: {
        type: "",
      },
    };
  },
  components: {
    ItemContent,
  },
  props: {
    contentType: String,
  },
  methods: {
    ...mapActions({ clear: "clearLocations", getLocations: "getLocations" }),
    initMap() {
      const container = document.querySelector("#map");
      const options = {
        center: new kakao.maps.LatLng(35.095718, 128.854836),
        level: 3,
      };

      const makerPos = new kakao.maps.LatLng(35.095718, 128.854836);

      const marker = new kakao.maps.Marker({
        position: makerPos,
      });

      this.map = new kakao.maps.Map(container, options);
      marker.setMap(this.map);

      const iwContent = `<div style="padding: 5px">SSAFY 부울경 4반</div>`,
        iwPos = new kakao.maps.LatLng(35.095718, 128.854836);

      const infoWindow = new kakao.maps.InfoWindow({
        position: iwPos,
        content: iwContent,
      });

      infoWindow.open(this.map, marker);
    },
    search() {
      this.clear();
      this.getLocations({});
    },

    detail(idx) {
      console.log(idx + "디테일 처리");
    },
  },
  name: "KakaoMap.vue",
  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=94d0d68556035d42bd965204b9c607a1&autoload=false";
      script.type = "text/javascript";

      document.head.appendChild(script);

      /* global kakao */
      script.addEventListener("load", () => {
        kakao.maps.load(this.initMap);
      });
    } else {
      this.initMap();
    }
  },

  computed: {
    ...mapGetters({ locationList: "locations" }),
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 100%;
}

.maps {
  width: 100%;
  height: 100%;
}

.search-window {
  width: 100%;
  height: 9vh;
  /*background-color: var(--pink);*/
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.search-bar {
  height: 53%;
  border: 1px solid rgba(0, 0, 0, 0.15);
}

.tag-bar {
  height: 47%;
  background-color: var(--pink);
}

.tag {
  width: 100px;
  height: 100%;
  border-radius: 50px;
  margin-right: 5px;
  background-color: var(--deep-skyblue);
}

.map-in {
  width: calc(100% - 514px);
  height: 100%;
  z-index: -1;
  border-top: 1px solid rgba(0, 0, 0, 0.15);
}

.map-info {
  width: 514px;
  /*width: 30%;*/
  height: 100%;
  border: 1px solid rgba(0, 0, 0, 0.15);
}
</style>
