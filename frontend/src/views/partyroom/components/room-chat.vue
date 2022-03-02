<template>
	<div class="absolute flex top-0 h-screen z-20 right-0 flex-row border-l border-main-100">
		<!-- chat-bar toggle button -->
		<!-- <button
			@click.prevent="toggle()"
			class="w-6 h-48 p-0 my-auto rounded-l-full text-white bg-main-200 text-center focus:outline-none hover:bg-gray-500 transition-color duration-300"
		>
			<svg
				v-if="state.isSidebarOpen"
				class="p-0"
				viewBox="0 0 48 48"
				fill="none"
				xmlns="http://www.w3.org/2000/svg"
			>
				<rect width="40" height="40" fill="white" fill-opacity="0.01" />
				<path
					d="M19 12L31 24L19 36"
					stroke="black"
					stroke-width="4"
					stroke-linecap="round"
					stroke-linejoin="round"
				/>
			</svg>
			<svg
				v-else
				class="p-0"
				viewBox="0 0 48 48"
				fill="none"
				xmlns="http://www.w3.org/2000/svg"
			>
				<rect width="40" height="40" fill="white" fill-opacity="0.01" />
				<path
					d="M31 36L19 24L31 12"
					stroke="black"
					stroke-width="4"
					stroke-linecap="round"
					stroke-linejoin="round"
				/>
			</svg>
		</button> -->

		<!-- chat-bar -->
		<div
			ref="content"
			class="transition-all pt-16 pb-12 px-8 w-80 duration-700 bg-main-300 overflow-hidden flex flex-col items-center justify-between"
			:class="[state.isSidebarOpen ? 'max-w-lg' : 'max-w-0']"
		>
			<!-- 채팅 내용 -->
			<ul
				class="border-2 h-full w-full border-main-100 bg-tc-500 p-4 overflow-y-auto m-0"
				id="chat-bar"
			>
				<li
					class="flex flex-col mb-2"
					v-for="(chat, idx) in state.chats"
					:key="idx"
				>
					<!-- 내가 보낸 채팅인 경우 -->
					<div v-if="chat.isMyMessage" class="border-r-2 border-sub-200 pr-2">
						<div class="flex flex-row-reverse items-center">
							<span class="text-sub-200 text-md ml-2 font-bold">Me</span>
							<span class="text-tc-400 text-xs">{{ chat.time }}</span>
						</div>
						<div>
							<p class="text-tc-300 text-sm break-all text-right">
								{{ chat.content }}
							</p>
						</div>
					</div>
					<!-- 다른 사람이 보낸 채팅인 경우 -->
					<div v-else class="border-l-2 border-main-100 pl-2">
						<div class="flex flex-row items-center">
							<span class="text-main-100 text-md mr-2 font-bold">{{
								chat.userId
							}}</span>
							<span class="text-tc-400 text-xs">{{ chat.time }}</span>
						</div>
						<div>
							<p class="text-tc-300 text-sm break-all text-left">
								{{ chat.content }}
							</p>
						</div>
					</div>
				</li>
			</ul>

			<div class="flex flex-col items-start w-full mt-8">
				<!-- 메시지 보낼 유저 선택창 -->
				<div
					class="relative inline-flex border-3 rounded-lg border-main-100 w-1/2 mb-4 h-7 p-0"
				>
					<select
						v-model="state.selectedUser"
						class="cursor-pointer font-bold px-4 py-0 text-sm form-select border-0 rounded-md text-gray-600 w-full h-5-5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
					>
						<option selected="selected" value="all">all</option>
						<option
							v-for="(sub, idx) in state.subscribers"
							:value="sub.stream.connection"
							:key="idx"
						>
							{{ JSON.parse(sub.stream.connection.data).clientData }}
						</option>
					</select>
				</div>

				<!-- 메시지 작성 -->
				<div class="w-full p-0 flex flex-row justify-between items-center">
					<textarea
						v-model="state.message"
						@keydown.enter="sendMessage"
						class="w-5/6 border-2 border-main-100 text-xs focus:border-main-100 focus:border-2 resize-none"
						cols=""
						rows="2"
					></textarea>
					<svg
						@click="sendMessage()"
						class="h-6 w-6 send-icon cursor-pointer"
						version="1.1"
						xmlns="http://www.w3.org/2000/svg"
						xmlns:xlink="http://www.w3.org/1999/xlink"
						x="0px"
						y="0px"
						viewBox="0 0 491.022 491.022"
						style="enable-background: new 0 0 491.022 491.022"
						xml:space="preserve"
					>
						<g>
							<g>
								<path
									d="M490.916,13.991c-0.213-1.173-0.64-2.347-1.28-3.307c-0.107-0.213-0.213-0.533-0.32-0.747
									c-0.107-0.213-0.32-0.32-0.533-0.533c-0.427-0.533-0.96-1.067-1.493-1.493c-0.427-0.32-0.853-0.64-1.28-0.96
									c-0.213-0.107-0.32-0.32-0.533-0.427c-0.32-0.107-0.747-0.32-1.173-0.427c-0.533-0.213-1.067-0.427-1.6-0.533
									c-0.64-0.107-1.28-0.213-1.92-0.213c-0.533,0-1.067,0-1.6,0c-0.747,0.107-1.493,0.32-2.133,0.533
									c-0.32,0.107-0.747,0.107-1.067,0.213L6.436,209.085c-5.44,2.347-7.893,8.64-5.547,14.08c1.067,2.347,2.88,4.373,5.227,5.44
									l175.36,82.453v163.947c0,5.867,4.8,10.667,10.667,10.667c3.733,0,7.147-1.92,9.067-5.12l74.133-120.533l114.56,60.373
									c5.227,2.773,11.627,0.747,14.4-4.48c0.427-0.853,0.747-1.813,0.96-2.667l85.547-394.987c0-0.213,0-0.427,0-0.64
									c0.107-0.64,0.107-1.173,0.213-1.707C491.022,15.271,491.022,14.631,490.916,13.991z M190.009,291.324L36.836,219.218
									L433.209,48.124L190.009,291.324z M202.809,437.138V321.831l53.653,28.267L202.809,437.138z M387.449,394.898l-100.8-53.013
									l-18.133-11.2l-0.747,1.28l-57.707-30.4L462.116,49.298L387.449,394.898z"
								/>
							</g>
						</g>
					</svg>
				</div>
			</div>
		</div>
	</div>
</template>

<style>
.border-3 {
	border-width: 3px;
}

.h-5-5 {
	height: 1.375rem;
}

.send-icon {
	fill: #6667ab;
}
</style>

<script>
import { reactive } from 'vue';

export default {
	props: {
		subscribers: Object,
	},

	setup(props, { emit }) {
		const state = reactive({
			// right: true,
			isSidebarOpen: true,
			selectedUser: 'all',
			message: '',
			subscribers: props.subscribers,
			chats: [],
		});

		const toggle = () => {
			state.isSidebarOpen = !state.isSidebarOpen;
		};

		const sendMessage = () => {
			let strippeddMessage = state.message.trim();

			if (strippeddMessage === '') return;

			console.log('보낼 메시지 : ' + strippeddMessage);

			emit('message', {
				content: strippeddMessage,
				to: state.selectedUser,
			});

			event.preventDefault(); // enter키 누를 때 줄바꿈 방지
			state.message = ''; // 메시지 창 초기화

			console.log(state.subscribers);
		};

		const addMessage = async (messageData, isMyMessage, isPrivate) => {
			let message = JSON.parse(messageData);

			// 내가 보낸 메시지인 경우
			if (isMyMessage) {
				message.sender += ' (You)';
			}

			// 개인 메시지인 경우
			if (isPrivate) {
				message.content += ' (private)';
			}

			let chatBar = document.querySelector('#chat-bar');
			let isScrollBottom =
				chatBar.scrollHeight - chatBar.scrollTop <= chatBar.clientHeight + 2;

			// await 키워드 => 새로운 채팅 메시지 추가 완료 후 스크롤바가 아래로 이동되도록 함.
			await state.chats.push({
				userId: message.sender,
				time: message.time,
				content: message.content,
				isMyMessage: isMyMessage,
			});

			// 채팅 스크롤이 끝까지 내려가 있는 경우 => 스크롤바 맨 아래로 이동시키기
			if (isScrollBottom) {
				chatBar.scrollTo({ top: chatBar.scrollHeight, behavior: 'smooth' });
			}

			console.log('메시지 수신 완료');
		};

		return { state, toggle, sendMessage, addMessage };
	},
};
</script>
