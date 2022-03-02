// 파티룸 생성 시 정보를 입력하는 모달

<template>
	<base-modal ref="baseModal">
		<div class="w-full max-w-2xl bg-main-300 shadow-md rounded-xl px-6 py-6">
			<!-- close button -->
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

			<div class="flex flex-col items-center w-full">
				<h1 class="text-xl mb-4">채팅방 생성</h1>
				<form
					method="post"
					enctype="multipart/form-data"
					class="flex flex-col justify-between items-center bg-main-300 w-11/12 sm:w-5/6 mx-auto rounded-lg"
				>
					<div
						class="flex flex-row h-40 justify-between justify-items-center w-full"
					>
						<div class="flex flex-col justify-between w-1/2">
							<!-- 방 이름 -->
							<div>
								<input
									v-model="state.title"
									class="p-2 w-full text-tc-200 rounded-sm"
									type="text"
									placeholder="방 이름"
									required
								/>
							</div>
							<!-- 최대 인원 -->
							<div>
								<input
									v-model="state.capacity"
									class="p-2 w-full text-tc-200 rounded-sm"
									type="number"
									max="8"
									min="1"
									placeholder="인원 수"
									required
								/>
							</div>
							<p class="text-xs text-red-600" v-if="state.capacityErr">
								* 최대 8명까지만 입장할 수 있어요.
							</p>
							<!-- 비밀번호 -->
							<div
								class="bg-tc-500 p-0 rounded-sm flex flex-row items-center border-tc-300 border-1"
							>
								<span class="p-2">
									<svg
										height="21"
										viewBox="0 0 21 21"
										width="21"
										xmlns="http://www.w3.org/2000/svg"
									>
										<g
											fill="none"
											fill-rule="evenodd"
											transform="translate(4 1)"
										>
											<path
												d="m2.5 8.5-.00586729-1.99475098c-.00728549-4.00349935 1.32800361-6.00524902 4.00586729-6.00524902 2.19134421 0 3.6093703 1.31907891 4 4m-8 4h8.0225317c1.0543618 0 1.9181652.81587779 1.9945143 1.8507377l.0054778.1548972-.0169048 6c-.0031058 1.1023652-.8976224 1.9943651-1.999992 1.9943651h-8.005627c-1.1045695 0-2-.8954305-2-2v-6c0-1.1045695.8954305-2 2-2z"
												stroke="currentColor"
												stroke-linecap="round"
												stroke-linejoin="round"
											/>
											<circle cx="6.5" cy="13.5" fill="currentColor" r="1.5" />
										</g>
									</svg>
								</span>
								<input
									v-model="state.password"
									class="m-0 p-0 w-full text-tc-200 border-none border-transparent focus:border-transparent focus:ring-0"
									type="password"
									placeholder="비밀번호 설정"
								/>
							</div>
						</div>
						<!-- 썸네일 -->
						<div class="w-1/2 h-40 ml-5 border-tc-300 border-1">
							<div class="flex items-center justify-center w-full h-full">
								<label
									class="flex flex-col justify-center items-center cursor-pointer rounded-sm w-full h-full bg-tc-500 hover:bg-gray-300"
								>
									<svg
										height="21"
										viewBox="0 0 21 21"
										width="21"
										xmlns="http://www.w3.org/2000/svg"
									>
										<g
											fill="none"
											fill-rule="evenodd"
											transform="translate(3 3)"
										>
											<g
												stroke="currentColor"
												stroke-linecap="round"
												stroke-linejoin="round"
											>
												<path
													d="m2.5.5h10c1.1045695 0 2 .8954305 2 2v10c0 1.1045695-.8954305 2-2 2h-10c-1.1045695 0-2-.8954305-2-2v-10c0-1.1045695.8954305-2 2-2z"
												/>
												<path d="m14.5 10.5-3-3-3 2.985" />
												<path d="m12.5 14.5-9-9-3 3" />
											</g>
											<circle cx="11" cy="4" fill="currentColor" r="1" />
										</g>
									</svg>
									<p
										class="pt-1 text-sm tracking-wider text-tc-200 group-hover:text-gray-600"
									>
										썸네일 등록
									</p>
									<p
										class="pt-1 text-xs tracking-wider text-tc-300 group-hover:text-gray-600"
									>
										1Mb 이하 이미지만 업로드 가능합니다.
									</p>
									<p>{{ state.fileName }}</p>
									<input
										id="thumbnail"
										type="file"
										class="opacity-0 h-0"
										@change="onUploadImage()"
									/>
								</label>
							</div>
						</div>
					</div>
					<!-- 방 소개 -->
					<textarea
						v-model="state.description"
						type="text"
						name="partyroom-introduce"
						placeholder="방 소개"
						class="p-2 mt-5 bg-white text-tc-200 rounded-sm h-36 w-full"
						id=""
					></textarea>
					<!-- 태그 -->
					<div class="w-full mt-5">
						<input
							v-model="state.hashtag"
							@keyup.space="addHash()"
							type="text"
							placeholder="태그입력"
							class="text-tc-200 w-full p-2 rounded-sm"
						/>
					</div>
					<!-- 파티룸 생성 버튼 -->
					<button
						type="button"
						@click="createRoom()"
						class="w-3/5 p-2 rounded-full bg-gradient-to-r from-main-200 to-sub-200 mt-7 text-tc-200"
					>
						생성하기
					</button>
				</form>
			</div>
		</div>
	</base-modal>
</template>

<style>
.border-1 {
	border-width: 1px;
}

input:focus,
textarea:focus {
	border: none;
	outline: none;
}
</style>

<script>
import { reactive, ref, computed } from 'vue';
import BaseModal from './base-modal.vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import { swal } from '@/assets/js/common';

export default {
	name: 'ConferenceCreateModal',
	components: {
		BaseModal,
	},
	setup() {
		const router = useRouter();
		const store = useStore();
		const baseModal = ref(null);

		const state = reactive({
			capacity: '',
			description: '',
			password: '',
			title: '',
			thumbnailImg: null,
			fileName: '',
			hashtag: '#',
			capacityErr: computed(() => (state.capacity > 8 ? true : false)),
			roomCreateErr: false,
		});

		const open = () => {
			console.log('rcopen');
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
		};

		const onUploadImage = () => {
			state.thumbnailImg = document.getElementById('thumbnail').files[0];
			state.fileName = state.thumbnailImg.name;
		};

		const createRoom = () => {
			state.hashtag = state.hashtag.replace(/ /g, '');

			checkCreateValidation();

			if (state.roomCreateErr) {
				return popUpfailToast();
			}

			const room = {
				capacity: state.capacity,
				description: state.description,
				password: state.password,
				title: state.title,
				hashtag: state.hashtag,
			};
			console.log('room : ', room);
			// 방 생성 시 필요한 데이터를 form 형태로 전달
			const roomData = new FormData();
			roomData.append('thumbnail', state.thumbnailImg);
			roomData.append(
				'room',
				new Blob([JSON.stringify(room)], { type: 'application/json' }),
			);
			// createRoom을 비동기 호출한 뒤 바로 passwordConfirm도 호출하여 생성과 동시에 입장
			store
				.dispatch('root/createRoom', roomData)
				.then(res => {
					console.log(res);

					store
						.dispatch('root/passwordConfirm', {
							roomId: res.data.id,
							password: state.password,
						})
						.then(() => {
							console.log(res);
							state.capacity = '';
							state.description = '';
							state.password = '';
							state.title = '';
							state.thumbnailImg = null;
							state.fileName = '';
							state.hashtag = '#';
							state.roomCreateErr = false;
							router.push({
								name: 'ConferenceDetail',
								params: {
									conferenceId: res.data.id,
									userName: store.getters['auth/getUserName'],
								},
							});
						})
						.catch(err => {
							console.log(err);
						});
					close();
				})
				.catch(err => {
					console.log('실패');
					console.log(err);
					popUpfailToast();
				});
		};

		const checkCreateValidation = () => {
			state.roomCreateErr = false;

			if (state.capacity <= 0) {
				state.roomCreateErr = true;
			}

			if (state.capacity > 8) {
				state.capacity = 8;
				state.roomCreateErr = true;
			}

			if (state.title.length === 0 || state.title.length === null) {
				state.roomCreateErr = true;
			}
		};

		const popUpfailToast = () => {
			swal(
				true,
				'top',
				2000,
				'error',
				'파티룸 생성에 실패했습니다. 입력 값을 다시 확인해주세요.',
				null,
			);
		};

		const addHash = () => {
			state.hashtag += '#';
		};

		return {
			state,
			open,
			close,
			onUploadImage,
			createRoom,
			checkCreateValidation,
			popUpfailToast,
			baseModal,
			addHash,
		};
	},
};
</script>
