<template>
	<main-header />

	<div class="conference-list-wrap" style="overflow: auto">
		<infinite-scroll
			@infinite-scroll="infiniteHandler"
			:noResult="state.noResult"
			:message="state.message"
			class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 2xl:grid-cols-4"
		>
			<conference
				v-for="room in state.roomList"
				:key="room.id"
				class="conference-card mx-auto my-6 place-content-center col-span-1 content-center"
				@click="handleClick(room.id, room.capacity, room.sessions)"
				:room="room"
			/>
		</infinite-scroll>
	</div>

	<footer class="text-tc-500">not showing</footer>

	<!-- 로그인 모달 -->
	<login-modal ref="loginModal" />
	<!-- 비밀번호 확인 모달 -->
	<password-confirm ref="passwordConfirmModal" />
</template>
<style>
/* .conference-list-wrap {
	max-height: calc(70%);
} */

@media (min-width: 701px) and (max-width: 1269px) {
	.conference-list-wrap {
		min-width: 700px;
	}
}

@media (min-width: 1270px) {
	.conference-list-wrap {
		min-width: 1021px;
	}
}

.conference-list-wrap .conference-card {
	display: inline-block;
	cursor: pointer;
}
</style>
<script>
import { reactive, ref, computed, onBeforeMount } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import MainHeader from './components/main-header.vue';
import Conference from './components/conference.vue';
import InfiniteScroll from 'infinite-loading-vue3';
import PasswordConfirm from '@/teleport/password-confirm.vue';
import LoginModal from '@/teleport/login-modal.vue';
import Swal from 'sweetalert2';

export default {
	name: 'Home',

	components: {
		MainHeader,
		Conference,
		InfiniteScroll,
		PasswordConfirm,
		LoginModal,
	},

	setup() {
		const router = useRouter();
		const store = useStore();
		const passwordConfirmModal = ref(null);
		const loginModal = ref(null);
		const state = reactive({
			roomList: computed(() => store.getters['root/getRoomList']),
			noResult: false,
			message: '',
			listLoading: false,
			lastPage: false,
			message: '마지막 페이지입니다.',
		});

		onBeforeMount(() => {
			store.commit('root/setSearchValue', '');
			store.commit('root/setSearchOption', 'title');
			store.commit('root/setPage', 1);
			store
				.dispatch('root/requestRoomList')
				.then(res => {
					store.commit('root/setRoomList', res.data.contents.content);
				})
				.catch(err => {
					console.log(err);
				});
		});

		// 백엔드에 axios 요청 보내서 응답 받아올 부분
		const infiniteHandler = () => {
			if (state.listLoading) return;
			state.listLoading = true;
			var page = store.getters['root/getPage'];
			store.commit('root/setPage', page + 1);
			store
				.dispatch('root/requestRoomList')
				.then(res => {
					if (!res.data.contents.empty) {
						console.log('data : ', res.data.contents);
						store.commit('root/pushRoomList', res.data.contents.content);
						console.log('roomlist : ', state.roomList);
						state.noResult = false;
					} else {
						state.noResult = true;
						store.commit('root/setPage', page);
					}
					state.listLoading = false;
				})
				.catch(err => {
					console.log(err);
					state.listLoading = false;
				});
		};

		const clickConference = function (id) {
			router.push({
				name: 'ConferenceDetail',
				params: {
					conferenceId: id,
				},
			});
		};

		const handleClick = (id, capacity, sessions) => {
			let curConnCnt;

			if (sessions === undefined) curConnCnt = 0;
			else curConnCnt = sessions.length;

			if (curConnCnt >= capacity) {
				const Toast = Swal.mixin({
					toast: true,
					position: 'top',
					showConfirmButton: false,
					timer: 2000,
					timerProgressBar: true,
				});

				Toast.fire({
					icon: 'warning',
					title: '파티 룸이 꽉 찼습니다.',
				});

				return;
			}

			store
				.dispatch('root/roomLinkEntry', id)
				.then(res => {
					console.log(res.data);
					if (res.data.locked) {
						passwordConfirmModal.value.open(id);
					} else {
						store
							.dispatch('root/passwordConfirm', { roomId: id, password: '' })
							.then(res => {
								console.log(res);
								router.push({
									name: 'ConferenceDetail',
									params: {
										conferenceId: id,
										userName: store.getters['auth/getUserName'],
									},
								});
							})
							.catch(err => {
								console.log(err);
							});
					}
				})
				.catch(err => {
					// 401 Unauthorized Error => 로그인 모달 띄워주기
					// FIX: 로그인 모달 동시에 뜨지 못하게 하기
					if (err.response.status === 401) {
						loginModal.value.open();

						const Toast = Swal.mixin({
							toast: true,
							position: 'top',
							showConfirmButton: false,
							timer: 2000,
							timerProgressBar: true,
						});

						Toast.fire({
							icon: 'warning',
							title: '로그인 후 입장해주세요.',
						});
					}
					console.log(err);
				});
		};
		return {
			state,
			store,
			infiniteHandler,
			clickConference,
			handleClick,
			passwordConfirmModal,
			loginModal,
		};
	},
};
</script>
