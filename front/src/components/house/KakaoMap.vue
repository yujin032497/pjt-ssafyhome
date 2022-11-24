<template>
  <div class="maps d-flex">
    <div class="map-in">
      <div id="map"></div>
    </div>

    <div class="map-info">
      <div class="search-window p-3 d-flex">
        <search-condition
          :contentType="contentType"
          @changeState="setDongType"></search-condition>
        <div class="search-btn-area">
          <b-button class="w-100 h-100 search-btn" @click="search"
            >검색
          </b-button>
        </div>
      </div>
      <div>
        <div class="search-items py-2 px-3" style="overflow: auto">
          <item-content
            v-for="(data, index) in detailLocations"
            :key="index"
            :item="data"
            :type="searchType"
            :contentType="contentType"
            :jeonwol="jeonwol"
            ref="itemContent"
            @detailIdx="detail"
            @categoryIdx="manageMarkers" />

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
import SearchCondition from "@/components/house/import/SearchCondition";
import markerFood from "@/assets/img/marker_food.png";
import markerCafe from "@/assets/img/marker_cafe.png";
import markerHospital from "@/assets/img/marker_medic.png";
import markerBank from "@/assets/img/marker_bank.png";

export default {
  data() {
    return {
      map: null,
      dongCode: "",
      markers: [],
      markersFood: [],
      markersBank: [],
      markersHospital: [],
      markersCafe: [],
      infoWindows: [],
      positions: [],
      searchType: 1,
      nearTarget: "",
      jeonwol: null,
      detailLocations: [],
    };
  },
  components: {
    SearchCondition,
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
      this.detailLocations = [];

      // 마커 지우기
      this.clearMarkers();
      this.clearContentMarkers();

      if (this.dongCode !== null && this.searchType !== null) {
        // 매매일 때는 전월을 null로 처리.
        if (this.searchType === 1) {
          this.jeonwol = null;
        }
        this.getLocations({
          gubn: this.contentType,
          type: this.searchType,
          jeonwol: this.jeonwol,
          dongCode: this.dongCode,
        });
      }
    },

    setDongType(dongCode, type, jeonwol) {
      this.dongCode = dongCode;
      this.searchType = type;
      this.jeonwol = jeonwol;
      console.log(dongCode, type, jeonwol);
    },

    // locations은 DB에서 가져온 결과
    setKakaoMarkers(locations) {
      // 1. 카카오 키워드 검색을 위한 객체 생성.
      const geocoder = new kakao.maps.services.Geocoder();

      // 2. 장소들을 하나씩 addressSearch 시작.
      let bounds = new kakao.maps.LatLngBounds();
      let idx = 0;
      for (let location of locations) {
        // 카카오 주소로 좌표 검색 API
        geocoder.addressSearch(location.fullAddress, (data, status) => {
          if (status == kakao.maps.services.Status.OK) {
            const position = new kakao.maps.LatLng(data[0].y, data[0].x);
            this.positions.push(position);

            location.position = position;
            location.idx = idx;

            console.log("kakaoMap", location.idx);

            this.detailLocations.push(location);

            this.displayMarker(position, location.aptName);
            bounds.extend(position);

            this.map.setBounds(bounds);
            idx++;
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
    },
    detail(idx, expanded) {
      this.map.setCenter(this.detailLocations[idx].position);
      console.log(this.detailLocations[idx].position);
      this.clearContentMarkers();
      if (!expanded) {
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

                console.log(idx, data);

                if (idx == 0) {
                  this.displayMarkerGroup(0);
                }
              }
            },
          );
        }
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
      const imgList = [markerFood, markerCafe, markerHospital, markerBank];
      const imgSrc = imgList[contentIdx];

      const now = contentList[contentIdx];
      for (let content of contents) {
        const img = new kakao.maps.MarkerImage(
          imgSrc,
          new kakao.maps.Size(45, 45),
        );
        const marker = new kakao.maps.Marker({
          position: new kakao.maps.LatLng(content.y, content.x),
          image: img,
          title: content.place_name,
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

        kakao.maps.event.addListener(marker, "mouseover", () => {
          window.open(this.map, marker);
        });
        kakao.maps.event.addListener(marker, "mouseout", () => {
          window.close();
        });

        const data = { marker: marker, window: window, position: content };
        now.push(data);
      }
    },

    displayMarkerGroup(...contentIdx) {
      const contentList = [
        this.markersFood,
        this.markersCafe,
        this.markersHospital,
        this.markersBank,
      ];
      const bounds = new kakao.maps.LatLngBounds();

      contentIdx.forEach((idx) => {
        const contents = contentList[idx];
        console.log("컨텐츠", contents);
        contents.forEach((data) => {
          data.marker.setMap(this.map);

          const pos = data.position;
          bounds.extend(new kakao.maps.LatLng(pos.y, pos.x));
        });
      });
      // console.log(bounds.toString());
      // this.map.setBounds(bounds);
    },

    hideMarkerGroup(...contentIdx) {
      const contentList = [
        this.markersFood,
        this.markersCafe,
        this.markersHospital,
        this.markersBank,
      ];

      console.log(contentIdx);
      contentIdx.forEach((idx) => {
        const contents = contentList[idx];
        contents.forEach((data) => {
          data.marker.setMap(null);
        });
      });
      // const markerList = contentList[contentIdx];
      // for (let i = 0; i < markerList.length; i++) {
      //   const marker = markerList[i].marker;
      //   const window = markerList[i].window;
      //
      //   marker.setMap(null);
      //   window.close();
      // }
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
      this.markersFood.forEach((data) => {
        data.marker.setMap(null);
        console.log(data.marker);
      });
      this.markersCafe.forEach((data) => {
        data.marker.setMap(null);
      });
      this.markersHospital.forEach((data) => {
        data.marker.setMap(null);
      });
      this.markersBank.forEach((data) => {
        data.marker.setMap(null);
      });

      this.markersFood = [];
      this.markersCafe = [];
      this.markersHospital = [];
      this.markersBank = [];
    },
    manageMarkers(idx, cIdx) {
      console.log(idx, cIdx);
      this.hideMarkerGroup(0, 1, 2, 3);
      this.displayMarkerGroup(cIdx);
    },
  },
  name: "KakaoMap.vue",
  mounted() {
    this.clear();
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
  height: 11vh;
  border-bottom: 1px solid rgba(0, 0, 0, 0.15);
}

.search-btn-area {
  width: 20%;
  height: auto;
}

.search-btn {
  font-size: 1.2rem;
  font-weight: bold;
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
