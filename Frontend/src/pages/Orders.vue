<template>
  <div class="orders">
    <div class="container">
      <table class="table table-bordered">
        <thead>
        <tr>
          <td>번호</td>
          <td>주문자명</td>
          <td>주소</td>
          <td>결제 수단</td>
          <td>구입 항목</td>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(o, idx1) in state.orders" :key="idx1">
          <td>{{ state.orders.length - idx1 }}</td>
          <td>{{ o.name }}</td>
          <td>{{ o.address }}</td>
          <td>{{ o.payment }}</td>
          <td>
            <div v-for="(i, idx2) in o.items" :key="idx2">{{ i.name }}</div>
          </td>
        </tr>
        </tbody>
      </table>
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
      orders: [],
    })

    axios.get("/api/orders").then(({data}) => {
          state.orders = [];

          for (let d of data) {
            if (d.items) { //d에 items가 있을 경우에만...
              d.items = JSON.parse(d.items); //JSON을 다시 배열로 바꾸기.....?
            }
            state.orders.push(d);
          }
        }
    )
    return {state, lib,}
  }
}
</script>

<style scoped>
.table {
  margin-top: 30px;
}

.table > tbody {
  border-top: 1px solid #eee;
}
</style>
<!--table tdoby라고 했을 땐 우선순위에서 밀렸는데 지금 같이 클래스로 주니 해결됨-->