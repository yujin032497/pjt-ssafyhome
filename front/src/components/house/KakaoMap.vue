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
            :type="searchType"
            :contentType="contentType"
            ref="itemContent"
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
      markersFood: [],
      markersBank: [],
      markersHospital: [],
      markersCafe: [],
      infoWindows: [],
      positions: [],
      searchType: 1,
      nearTarget: "",
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

      this.map = new kakao.maps.Map(container, options);
      this.bounds = new kakao.maps.LatLngBounds();
    },

    search() {
      this.clear();

      // 마커 지우기
      this.clearMarkers();

      this.getLocations({
        gubn: this.contentType,
        type: this.searchType,
        dongCode: "1111011500",
      });
    },

    setKakaoMarkers(locations) {
      // 1. 카카오 키워드 검색을 위한 객체 생성.
      const geocoder = new kakao.maps.services.Geocoder();

      // 2. 장소들을 하나씩 addressSearch 시작.
      let bounds = new kakao.maps.LatLngBounds();

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
        this.map.setCenter(position);
      });

      this.markers.push(marker);
      this.infoWindows.push(infoWindow);
      this.positions.push(position);
    },
    detail(idx) {
      this.map.setCenter(this.positions[idx]);
      this.clearContentMarkers();
      // 클릭시 주소를 가져온다.
      this.nearTarget = this.locationList[idx].fullAddress;

      // 주변 조회를 태운다.
      // 1. 카카오 키워드 검색을 위한 객체 생성.
      const ps = new kakao.maps.services.Places();

      const keyword = ["맛집", "카페", "병원", "은행"];

      for (let idx = 0; idx < 4; idx++) {
        ps.keywordSearch(
          this.nearTarget + " " + keyword[idx],
          (data, status) => {
            if (status == kakao.maps.services.Status.OK) {
              this.setMarkerGroup(idx, data);

              if (idx == 0) {
                this.displayMarkerGroup(0);
              }
            }
          },
        );
      }
    },

    setMarkerGroup(contentIdx, contents) {
      const contentList = [
        this.markersFood,
        this.markersCafe,
        this.markersHospital,
        this.markersBank,
      ];

      const keyword = ["맛집", "카페", "병원", "은행"];

      const img = new kakao.maps.MarkerImage(
        "@/assets/img/marker_location.png",
        new kakao.maps.Size(30, 40),
        { offset: new kakao.maps.Point(15, 29) },
      );

      const now = contentList[contentIdx];
      for (let content of contents) {
        const marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(content.y, content.x),
          title: content.place_name,
          img: img,
          clickable: true,
        });
        const link = `https://map.kakao.com/?q=${content.place_name}`;
        const windowTemplate = `
            <div style="padding: 4px;">
              <p style="font-weight: bold; margin-bottom: 0;">${content.place_name}</p>
              <p style="margin-bottom: 0;">${content.phone}</p>
              <p style="margin-bottom: 0;">${keyword[contentIdx]}</p>
              <p><a href="${link}">카카오맵에서 보기</a></p>
            </div>
            `;

        const window = new kakao.maps.InfoWindow({
          position: new kakao.maps.LatLng(content.y, content.x),
          content: windowTemplate,
        });

        kakao.maps.event.addListener(marker, "click", () => {
          window.open(this.map, marker);
        });

        const data = { marker: marker, window: window };
        now.push(data);
      }
    },

    displayMarkerGroup(contentIdx) {
      const contentList = [
        this.markersFood,
        this.markersCafe,
        this.markersHospital,
        this.markersBank,
      ];

      const markerList = contentList[contentIdx];
      for (let i = 0; i < markerList.length; i++) {
        const marker = markerList[i].marker;

        marker.setMap(this.map);
      }
    },

    hideMarkerGroup(contentIdx) {
      const contentList = [
        this.markersFood,
        this.markersCafe,
        this.markersHospital,
        this.markersBank,
      ];

      const markerList = contentList[contentIdx];
      for (let i = 0; i < markerList.length; i++) {
        const marker = markerList[i].marker;
        const window = markerList[i].window;

        marker.setMap(null);
        window.close();
      }
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

    clearContentMarkers() {
      const contentList = [
        this.markersFood,
        this.markersCafe,
        this.markersHospital,
        this.markersBank,
      ];

      for (let content of contentList) {
        console.log(content.length);
        for (let idx = 0; idx < content.length; idx++) {
          const marker = content[idx].marker;
          const window = content[idx].window;

          marker.setMap(null);
          window.close();

          content = [];
        }
      }
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
