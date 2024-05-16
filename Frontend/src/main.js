import { createApp } from 'vue'
import store from "@/scripts/store"
import router from "@/scripts/router"
import App from './App.vue'

createApp(App).use(router).use(store).mount('#app')
