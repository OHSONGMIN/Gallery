<template>
  <div class="card shadow-sm">
    <span class="img" :style="{backgroundImage: `url(${item.imgPath})`}" />
<!--    <img :src ="item.imgPath" /> -->
    <div class="card-body">
      <p class="card-text">{{ item.name }}</p>
      <div class="d-flex justify-content-between align-items-center">
        <button class="btn btn-primary">구입하기</button>
        <small class="text-body-secondary">
          {{ item.price }}원
        </small>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Card",
  props: {
    item: Object
  }
}
</script>

<style scoped>
.card .img{
  display:inline-block;
  width:100%;
  height:250px;
  background-size: cover;
  background-position: center;
}
</style>