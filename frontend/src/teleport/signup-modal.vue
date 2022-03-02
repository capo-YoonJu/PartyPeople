// 회원 가입 모달 컴포넌트
<template>
	<base-modal ref="baseModal">
		<div class="flex justify-center">
			<div class="w-full max-w-xs">
				<form class="bg-main-300 rounded-xl px-8 pt-6 pb-8 mb-4">
					<div class="flex justify-between items-start rounded-t bg-main-300">
						<button
							type="button"
							@click="close()"
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
						class="w-40 h-24 mb-4 mx-auto"
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
					<div class="mb-4">
						<input
							class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="username"
							type="text"
							placeholder="닉네임"
							v-model="state.form.nickname"
						/>
					</div>
					<div class="mb-4">
						<input
							class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="password"
							type="password"
							placeholder="비밀번호"
							v-model="state.form.password"
						/>
					</div>
					<div class="mb-4">
						<input
							class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="password"
							type="password"
							placeholder="비밀번호 확인"
							v-model="state.form.passwordconfirm"
						/>
					</div>
					<div class="mb-4">
						<input
							class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="username"
							type="text"
							placeholder="이메일"
							v-model="state.form.email"
						/>
					</div>
					<div class="mb-6">
						<input
							class="shadow appearance-none border rounded-full w-full py-2 px-3 mb-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="username"
							type="text"
							placeholder="전화번호"
							v-model="state.form.tel"
						/>
					</div>
					<div class="flex items-center justify-center">
						<button
							class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold h-10 py-1 px-24 rounded-full focus:outline-none focus:shadow-outline"
							type="button"
							@click="signup()"
						>
							가입하기
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
import { reactive, ref } from '@vue/reactivity';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import BaseModal from './base-modal.vue';
import { swal } from '@/assets/js/common';

export default {
	name: 'SingupModal',
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
				nickname: '',
				password: '',
				passwordconfirm: '',
				email: '',
				tel: '',
			},
		});
		const open = () => {
			console.log('signupopen');
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
		};
		const signup = () => {
			console.log(state.form);
			store
				.dispatch('auth/requestSignup', {
					accountId: state.form.accountId,
					nickname: state.form.nickname,
					password: state.form.password,
					// passwordconfirm: state.form.passwordconfirm,
					email: state.form.email,
					tel: state.form.tel,
				})
				.then(result => {
					console.log(result);
					state.form.accountId = '';
					state.form.nickname = '';
					state.form.password = '';
					state.form.passwordconfirm = '';
					state.form.email = '';
					state.form.tel = '';
					close();

					swal(
						true,
						'top',
						1500,
						'success',
						'회원가입되었습니다. 로그인 후 빠뤼피플을 즐겨보세요!',
						null,
					);

					// 회원가입 시 페이지 초기화 후 파티룸 리스트 다시 불러오기
					store.commit('root/setPage', 1);
					store
						.dispatch('root/requestRoomList')
						.then(res => {
							store.commit('root/setRoomList', res.data.contents.content);
						})
						.catch(err => {
							console.log(err);
						});
					// router.push({ name: 'Home' });
				})
				.catch(err => {
					console.log(err);
				});
		};
		return { state, baseModal, open, close, signup, router };
	},
};
</script>
