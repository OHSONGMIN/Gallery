<template>
  <div class="cart">
    <div class="container">
      <ul>
        <li v-for="(i, idx) in state.items" :key="idx">
          <img :src="i.imgPath"/>
          <span class="name">{{ i.name }}</span>
          <span class="price">{{ lib.getNumberFormatted(i.price - i.price * i.discountPer / 100) }}원</span>
          <i class="fa fa-trash" @click="remove(i.id)"></i>
        </li>
      </ul>
<!--      <button class="btn btn-primary">구입하기</button>-->
      <router-link to="/order" class="btn btn-primary">구입하기</router-link>
    </div>
  </div>
</template>

<script>

import axios from "axios";
import {reactive} from "vue";
import lib from "@/scripts/lib";

export default {
  setup() {
    const state = reactive({
      items: []
    })

    //함수화 한 이유는... 아래 remove 메서드에서 갖다 쓰고싶어서
    const load = () => {

      axios.get("/api/cart/items").then(({data}) => {
        console.log(data);
        state.items = data;
      })
    };

    const remove = (itemId) => {

      axios.delete(`/api/cart/items/${itemId}`).then(() => {
        //아이템 삭제가 성공하면
        load();
      })
    }

    load(); //처음에도 load()를 실행해야겠지~~

    return {state, lib, remove}
  }
}
</script>

<style scoped>
.cart ul {
  list-style: none;
  margin: 0;
  padding: 0;
}

.cart ul li {
  border: 1px solid #eee;
  margin-top: 25px;
  margin-bottom: 25px;
}

.cart ul li img {
  width: 150px;
  height: 150px;
}

.cart ul li .name {
  margin-left: 25px;
}

.cart ul li .price {
  margin-left: 25px;
}

.cart ul li i {
  float: right;
  font-size: 20px;
  margin-top: 65px;
  margin-right: 50px;
}

.cart .btn {
  width: 300px;
  display: block;
  padding: 30px 50px;
  margin: 0 auto;
  font-size: 20px;
}

</style>