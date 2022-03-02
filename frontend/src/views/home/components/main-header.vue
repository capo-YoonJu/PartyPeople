<template>
	<nav class="bg-white border-gray-200 px-4 py-5">
		<div class="flex flex-wrap justify-between items-center w-full my-0">
			<a href="#" class="flex-none ml-2 mr-6">
				<img class="w-10 h-10" src="@/assets/images/logo.svg" />
			</a>
			<div class="flex-auto">
				<div class="relative mr-3">
					<div
						class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none"
					>
						<svg
							class="w-5 h-5 text-tc-400 fill-current"
							viewBox="0 0 20 20"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								fill-rule="evenodd"
								d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
								clip-rule="evenodd"
							></path>
						</svg>
					</div>
					<!-- TODO : 검색 필터링 조건이 hashtag이고 띄어쓰기, 엔터, # 입력 시 현재 value를 list에 추가하여 list를 axios 요청보내도록 -->
					<input
						ref="searchInput"
						v-model="state.searchValue"
						@input="realtimeSuggestion"
						@keyup.enter="roomSearch()"
						@keyup.space="addHash()"
						@focusin="displaySuggestion"
						@focusout="hideSuggestion"
						autocomplete="off"
						type="text"
						id="party-room-search"
						class="block border-0 appearance-none rounded-full shadow-md h-10 p-2 pl-10 w-full text-tc-200 bg-main-300 sm:text-sm focus:outline-none focus:border-main-100 focus:ring-2 focus:ring-main-100"
						placeholder="Search for party room"
					/>
					<div v-show="state.showSuggestion" class="absolute text-left w-full">
						<ul class="ml-8 mr-4 rounded-b-lg p-1 mt-1.5 bg-main-300">
							<li
								v-for="suggestion in state.suggestion"
								v-bind:key="suggestion"
							>
								{{ suggestion }}
							</li>
						</ul>
					</div>
				</div>
			</div>

			<!-- 검색 옵션 -->
			<div class="inline-block flex-none relative w-20 ml-2">
				<select
					v-model="state.searchOption"
					@change="changeOption"
					class="pl-4 cursor-pointer w-24 font-bold px-3 text-md form-select rounded-md shadow-lg bg-main-200 text-tc-500 h-10 border-transparent focus:border-transparent focus:ring-0 appearance-none"
				>
					<option
						class="w-24 h-16 font-bold bg-tc-500 text-main-100"
						value="title"
						selected="selected"
					>
						제목
					</option>
					<option
						class="w-24 h-16 font-bold bg-tc-500 text-main-100"
						value="des"
					>
						내용
					</option>
					<option
						class="w-24 h-16 font-bold bg-tc-500 text-main-100"
						value="hashtag"
					>
						태그
					</option>
				</select>
			</div>

			<div class="flex-none hidden md:block w-1/6"></div>
			<div v-if="!state.loginState" class="flex-none hidden md:block">
				<button
					@click="clickLogin()"
					class="rounded-full w-32 h-10 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
					type="button"
				>
					Login
				</button>
				<button
					@click="clickSignup()"
					class="rounded-full w-32 h-10 ml-4 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
					type="button"
				>
					Sign-Up
				</button>
			</div>
			<div v-else class="flex-none hidden md:block">
				<button
					@click="createRoom()"
					class="rounded-full w-32 h-10 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
					type="button"
				>
					Add+
				</button>
				<button
					@click="clickLogout()"
					class="rounded-full w-32 h-10 ml-4 font-bold shadow-lg bg-alert-200 text-tc-500 hover:bg-alert-100"
					type="button"
				>
					Log-Out
				</button>
			</div>
		</div>
	</nav>

	<!-- Login modal -->
	<login-modal ref="loginModal" />
	<!-- Signup modal -->
	<signup-modal ref="signupModal" />
	<!-- Room create modal -->
	<conference-create-modal ref="conferenceCreateModal" />
</template>
<script>
import { computed, reactive, ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import SignupModal from '@/teleport/signup-modal.vue';
import LoginModal from '@/teleport/login-modal.vue';
import ConferenceCreateModal from '@/teleport/conference-create-modal.vue';
import { swal } from '@/assets/js/common';

export default {
	name: 'main-header',

	components: {
		LoginModal,
		SignupModal,
		ConferenceCreateModal,
	},
	setup() {
		const store = useStore();
		const router = useRouter();
		const searchInput = ref(null);
		const signupModal = ref(null);
		const loginModal = ref(null);
		const conferenceCreateModal = ref(null);
		const state = reactive({
			loginState: computed(() => store.getters['auth/getLoginState']),
			searchValue: '',
			searchOption: 'title',
			suggestion: [],
			showSuggestion: false,
		});

		const clickLogin = () => {
			console.log('clicklogin');
			console.log(loginModal.value);
			loginModal.value.open();
		};

		const clickLogout = () => {
			localStorage.removeItem('access_token');
			store.commit('auth/setLoginState', false);
			router.push({ name: 'Home' });

			swal(true, 'top', 1500, 'success', '로그아웃되었습니다.', null);

			// 로그아웃 시 페이지 초기화 후 파티룸 리스트 다시 불러오기
			store.commit('root/setPage', 1);
			store
				.dispatch('root/requestRoomList')
				.then(res => {
					store.commit('root/setRoomList', res.data.contents.content);
				})
				.catch(err => {
					console.log(err);
				});
		};

		const clickSignup = () => {
			console.log('clicksignup');
			console.log(signupModal.value);
			signupModal.value.open();
		};

		const createRoom = () => {
			console.log('createRoom');
			console.log(conferenceCreateModal.value);
			conferenceCreateModal.value.open();
		};

		// 검색 필터링 조건 변경시 실행되는 함수
		const changeOption = () => {
			console.log('선택된 옵션:', state.searchOption);
			if (state.searchOption !== 'hashtag') {
				return;
			}

			// 이미 입력된 내용의 공백을 ' #'으로 치환
			state.searchValue = state.searchValue.replace(/ /g, ' #');

			if (state.searchValue.trimlength !== 0) {
				if (state.searchValue.charAt(0) !== '#')
					// 이미 입력된 내용이 있고 '#'로 시작하지 않으면 공백제거하여 맨 앞에 '#' 붙임
					state.searchValue = '#' + state.searchValue;
			} else {
				// 입력된 내용이 없으면 '#'로 시작
				state.searchValue += '#';
			}
		};

		const addHash = () => {
			if (state.searchOption !== 'hashtag') return;
			state.searchValue += '#';
		};

		// 파티룸 검색 시 백엔드 요청(키워드 배열 형태로 요청)
		const roomSearch = () => {
			if (state.searchOption === 'hashtag') {
				state.searchValue = state.searchValue.replace(/ /g, '');
			}
			store.commit('root/setSearchValue', state.searchValue);
			store.commit('root/setSearchOption', state.searchOption);
			store.commit('root/setPage', 1);
			store
				.dispatch('root/requestRoomList')
				.then(res => {
					store.commit('root/setRoomList', res.data.contents.content);
				})
				.catch(err => {
					console.log(err);
				});
			// 해시태그 옵션이 선택된 경우, 검색 이후 다시 '#'으로 초기화
			if (state.searchOption === 'hashtag') {
				state.searchValue = '#';
			} else {
				state.searchValue = null;
			}
		};

		const realtimeSuggestion = () => {
			store
				.dispatch('root/requestSuggestionList', {
					include: state.searchOption,
					word: state.searchValue,
				})
				.then(res => {
					state.suggestion = res.data.suggestions;
					console.log(state.suggestion);
				})
				.catch(err => {
					console.log(err);
				});
		};

		const displaySuggestion = () => {
			state.showSuggestion = true;
		};

		const hideSuggestion = () => {
			state.showSuggestion = false;
		};

		return {
			state,
			searchInput,
			loginModal,
			signupModal,
			conferenceCreateModal,
			clickLogin,
			clickSignup,
			createRoom,
			clickLogout,
			changeOption,
			addHash,
			roomSearch,
			realtimeSuggestion,
			hideSuggestion,
			displaySuggestion,
		};
	},
};
</script>
<style></style>
