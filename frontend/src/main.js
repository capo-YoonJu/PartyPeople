import { createApp, h } from 'vue';
import App from './App.vue';
import './assets/tailwind.css';
import '@themesberg/flowbite';
import router from './router/router';
import VueAxios from './api/axios';
import axios from './api/axios';
import store from './store/store';
import VueCountdown from '@chenfengyuan/vue-countdown';

const app = createApp({
	render: () => h(App),
});

app.use(router);
app.use(VueAxios, axios);
app.use(store);

app.mount('#app');
app.component(VueCountdown.name, VueCountdown);
