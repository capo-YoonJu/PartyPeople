// API
import $axios from 'axios';

const backAxios = $axios.create({
	baseURL: '/api/v1',
});
const ovAxios = $axios.create({
	baseURL: '/openvidu/api',
	headers: {
		Authorization: 'Basic a106ssafy0183',
	},
});

export function requestRoomList({ state }) {
	console.log('requestRoomList', state);
	if (state.searchValue == null) state.searchValue = '';
	const url = `/list/roomsearch`;

	return new Promise((resolve, reject) => {
		backAxios
			.get(url, {
				params: {
					page: state.page,
					size: 8,
					word: state.searchValue,
					include: state.searchOption,
				},
			})
			.then(res => resolve(res))
			.catch(err => reject(err));
	});
}

export async function requestRoomUserList({ state }, payload) {
	console.log('requestRoomList', state, payload);
	const url = `/rooms/users/${payload.roomId}`;
	let token = localStorage.getItem('access_token');
	console.log(token);

	return backAxios({
		method: 'GET',
		url: url,
		headers: { Authorization: 'Bearer ' + token },
	});
}

export function roomSearch({ state }, payload) {
	console.log('roomSearch', state, payload);
	const url = `/list/roomsearch?include=${payload.include}&word=${payload.word}`;

	return backAxios.get(url);
}

export function createRoom({ state }, payload) {
	console.log('createRoom', state, payload);
	const url = '/rooms';
	let token = localStorage.getItem('access_token');
	console.log(token);
	return backAxios({
		method: 'POST',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
			'Content-Type': 'multipart/form-data',
		},
		data: payload,
	});
}

export function roomLinkEntry({ state }, roomId) {
	console.log('roomLinkEntry', state);
	const url = `/rooms/${roomId}`;
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'GET',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
	});
}

export function passwordConfirm({ state }, payload) {
	const url = `/rooms/${payload.roomId}`;
	console.log(payload.password);
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'POST',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
		data: { password: payload.password },
	});
}

export function leaveSession({ state }, roomId) {
	console.log('leaveSession', state, roomId);
	const url = `/rooms/exit/${roomId}`;
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'PATCH',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
	});
}

export async function requestSuggestionList({ state }, payload) {
	const url = `/list/suggestion`;
	let token = localStorage.getItem('access_token');

	return backAxios({
		method: 'GET',
		url: url,
		headers: { Authorization: 'Bearer ' + token },
		params: { include: payload.include, word: payload.word },
	});
}

export function requestUserData({ state }) {
	console.log('requestMyData');
	const url = 'users/me';
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'GET',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
	});
}

export function requestNickname({ state }, accountId) {
	console.log('request users nickname Data');
	const url = `users/${accountId}`;
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'GET',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
	});
}
