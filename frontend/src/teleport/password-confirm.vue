<template>
	<base-modal ref="baseModal">
		<div class="flex justify-center">
			<div class="w-full max-w-xs">
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
					<!-- <p class="text-center text-black text-xs my-3">
						현재 파티룸은 녹화가 허용되어 있습니다.
					</p> -->
					<div class="mb-6">
						<input
							v-model="state.inputPassword"
							class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
							id="roomPassword"
							type="password"
							placeholder="비밀번호"
						/>
					</div>
					<div class="flex items-center justify-center">
						<button
							@click="confirm()"
							class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold py-1 px-24 rounded-full focus:outline-none focus:shadow-outline"
							type="button"
						>
							입장하기
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
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import BaseModal from './base-modal.vue';
import { useStore } from 'vuex';
import { swal } from '@/assets/js/common';

export default {
	name: 'PasswordConfirm',
	components: {
		BaseModal,
	},

	setup() {
		const store = useStore();
		const baseModal = ref(null);
		let conferenceRoomId = '';
		const router = useRouter();
		const state = reactive({
			inputPassword: '',
		});

		const open = id => {
			console.log('pcopen');
			console.log(id);
			conferenceRoomId = id;
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
		};
		const confirm = () => {
			console.log(state.inputPassword);
			store
				.dispatch('root/passwordConfirm', {
					roomId: conferenceRoomId,
					password: state.inputPassword,
				})
				.then(res => {
					console.log(res);
					router.push({
						name: 'ConferenceDetail',
						params: {
							conferenceId: conferenceRoomId,
							userName: store.getters['auth/getUserName'],
						},
					});
				})
				.catch(err => {
					// 403 Forbidden Error => 비밀번호 오류
					if (err.response.status === 403) {
						swal(
							true,
							'top',
							2000,
							'error',
							'비밀번호를 다시 확인해주세요.',
							null,
						);
					}
					console.log(err);
				});
		};
		return { state, baseModal, conferenceRoomId, open, close, confirm };
	},
};
</script>
