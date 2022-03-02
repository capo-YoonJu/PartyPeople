// 로그인 모달 컴포넌트
<template>
	<base-modal ref="baseModal">
		<div class="flex justify-center">
			<div class="w-full max-w-xs">
				<!-- 로그인 시 아이디, 비밀번호 데이터를 입력받는 form -->
				<form class="bg-main-300 shadow-md rounded-xl px-8 pt-6 pb-8 mb-4">
					<div class="flex justify-between items-start rounded-t bg-main-300">
						<button
							@click="close()"
							type="button"
							class="text-tc-500 bg-alert-200 hover:bg-alert-100 hover:text-tc-500 rounded-full text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white"
						>
							<svg
								class="w-5 h-5"
								fill="currentColor"
								viewBox="0 0 20 20"
								xmlns="http://www.w3.org/2000/svg"
							>
								<path
									fill-rule="evenodd"
									d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
									clip-rule="evenodd"
								></path>
							</svg>
						</button>
					</div>
					<img
						class="w-40 h-24 mb-4 rounded mx-auto"
						alt="Vue logo"
						src="@/assets/images/partyPeopleLanding.svg"
					/>
					<div class="mb-4">
						<input
							class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="username"
							type="text"
							placeholder="유저아이디"
							v-model="state.form.accountId"
						/>
					</div>
					<div class="mb-1">
						<input
							class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
							id="password"
							type="password"
							placeholder="비밀번호"
							v-model="state.form.password"
						/>
					</div>
					<p class="mb-6 text-red-600" v-if="state.loginErr">
						* 아이디나 비밀번호를 확인해주세요.
					</p>
					<div class="flex items-center justify-center">
						<button
							@click="login"
							class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold h-10 py-1 px-24 rounded-full focus:outline-none focus:shadow-outline"
							type="button"
						>
							로그인
						</button>
					</div>
				</form>
				<!-- <p class="text-center text-gray-500 text-xs">
					&copy;2022 PartyPeople Corp. All rights reserved.
				</p> -->
			</div>
		</div>
	</base-modal>
</template>
<style></style>
<script>
import { reactive, ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import BaseModal from './base-modal.vue';
import { swal } from '@/assets/js/common';

export default {
	name: 'LoginModal',
	components: {
		BaseModal,
	},
	setup() {
		const store = useStore();
		const router = useRouter();
		const baseModal = ref(null);
		const state = reactive({
			form: {
				accountId: '',
				password: '',
			},
			loginErr: false,
		});
		const open = () => {
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
		};
		// 로그인 액션과 유저데이터를 연속적으로 호출하여 localstorage에 로그인 상태, 아이디, 닉네임을 저장
		const login = function () {
			store
				.dispatch('auth/requestLogin', {
					accountId: state.form.accountId,
					password: state.form.password,
				})
				.then(result => {
					localStorage.setItem('access_token', result.data.accessToken);
					store.commit('auth/setLoginState', true);
					store.commit('auth/setUserName', state.form.accountId);
					store
						.dispatch('root/requestUserData')
						.then(res => {
							store.commit('auth/setUserNickname', res.data.nickname);
						})
						.catch(err => {
							console.log(err);
						});
					state.form.accountId = '';
					state.form.password = '';
					state.loginErr = false;
					router.push({ name: 'Home' });
					close();

					// 로그인시 페이지 초기화 후 파티룸 리스트 다시 불러오기
					store.commit('root/setPage', 1);
					store
						.dispatch('root/requestRoomList')
						.then(res => {
							store.commit('root/setRoomList', res.data.contents.content);
						})
						.catch(err => {
							console.log(err);
						});

					swal(true, 'top', 1500, 'success', '로그인에 성공했습니다.', null);
				})
				.catch(err => {
					swal(true, 'top', 1500, 'error', '로그인에 실패했습니다.', null);
					state.loginErr = true;
				});
		};

		return { state, login, open, close, baseModal };
	},
};
</script>
