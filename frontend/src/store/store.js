import { createStore } from 'vuex';
import root from './index';
import auth from './auth';
import createPersistedState from 'vuex-persistedstate';

export default createStore({
	modules: { root, auth },
	plugins: [
		createPersistedState({
			paths: ['auth'],
		}),
	],
});
