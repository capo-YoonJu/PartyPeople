import VueAxios from 'vue-axios';
import axios from 'axios';

const DEFAULT_ACCEPT_TYPE = 'application/json';

axios.defaults.headers['Content-Type'] = DEFAULT_ACCEPT_TYPE;

export default { VueAxios, axios };
