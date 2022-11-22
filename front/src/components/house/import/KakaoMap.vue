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
            >검색 테스트 버튼 {{ contentType }}
          </b-button>
        </div>
        <div class="tag-bar py-1 d-flex">
          <div class="tag text-center">검색태그</div>
          <div class="tag text-center">검색태그</div>
        </div>
      </div>
      <div>
        <div class="search-items py-2 px-3" style="overflow: auto">
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
      markers: [],
      infoWindows: [],
      positions: [],
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
      const mapTypeControl = new kakao.maps.MapTypeControl();
      const zoomControl = new kakao.maps.ZoomControl();

      this.map = new kakao.maps.Map(container, options);
      this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    },

    search() {
      this.clear();

      // 마커 지우기
      this.clearMarkers();

      this.getLocations({
        type: this.contentType,
        dongCode: "1111011500",
      });
    },

    setKakaoMarkers(locations) {
      // 1. 카카오 키워드 검색을 위한 객체 생성.
      const geocoder = new kakao.maps.services.Geocoder();

      let bounds = new kakao.maps.LatLngBounds();

      // 2. 장소들을 하나씩 addressSearch 시작.
      for (let location of locations) {
        console.log("이게 먼저");
        geocoder.addressSearch(location.fullAddress, (data, status) => {
          if (status == kakao.maps.services.Status.OK) {
            const position = new kakao.maps.LatLng(data[0].y, data[0].x);

            this.displayMarker(position, location.aptName);
            bounds.extend(position);

            this.map.setBounds(bounds);
          }
        });
      }
    },

    // 3-2. 마커, 인포인도우 추가.
    displayMarker(position, name) {
      const marker = new kakao.maps.Marker({
        map: this.map,
        position: position,
      });

      const infoWindow = new kakao.maps.InfoWindow({
        position: position,
        content: `<div style="padding: 5px; overflow: hidden;">${name}<br></div>`,
      });

      infoWindow.open(this.map, marker);

      kakao.maps.event.addListener(marker, "click", () => {
        this.mapCenter(position);
      });

      this.markers.push(marker);
      this.infoWindows.push(infoWindow);
      this.positions.push(position);
    },
    detail(idx) {
      this.map.setCenter(this.positions[idx]);
    },

    clearMarkers() {
      for (let idx = 0; idx < this.markers.length; idx++) {
        const marker = this.markers[idx];
        marker.setMap(null);

        const window = this.infoWindows[idx];
        window.close();
      }
      this.markers = [];
      this.infoWindows = [];
      this.positions = [];
    },
  },
  name: "KakaoMap.vue",
  mounted() {
    if (!window.kakao || !window.kakao.maps) {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=94d0d68556035d42bd965204b9c607a1&autoload=false&libraries=services";
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
  watch: {
    locationList() {
      if (this.locationList.length > 0) {
        this.setKakaoMarkers(this.locationList);
      }
    },
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

  border-top: 1px solid rgba(0, 0, 0, 0.15);
}

.map-info {
  width: 514px;
  /*width: 30%;*/
  height: 100%;
  border: 1px solid rgba(0, 0, 0, 0.15);
}

.search-items {
  max-height: 79.9vh;
}
</style>
