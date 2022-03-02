import $axios from 'axios';

const backAxios = $axios.create({
	baseURL: '/api/v1',
});

export default {
	namespaced: true,

	state: () => {
		false;
		('');
	},

	getters: {
		getLoginState(state) {
			return state.loginState;
		},
		getUserName(state) {
			return state.userName;
		},
		getUserNickname(state) {
			return state.userNickname;
		},
	},

	mutations: {
		setLoginState(state, setLogin) {
			console.log(setLogin);
			state.loginState = setLogin;
			console.log(state.loginState);
		},
		setUserName(state, userName) {
			state.userName = userName;
		},
		setUserNickname(state, nickname) {
			state.userNickname = nickname;
		},
	},

	actions: {
		requestLogin({ state }, payload) {
			console.log('requestLogin', state, payload);
			const url = '/auth/login';
			let body = payload;
			console.log(url);
			console.log(payload);
			return backAxios.post(url, body);
		},

		requestSignup({ state }, payload) {
			console.log('requestSignup', state, payload);
			const url = '/users';
			let body = payload;
			return backAxios.post(url, body);
		},
	},
};
