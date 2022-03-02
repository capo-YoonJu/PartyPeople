<template>
	<div class="bottom flex justify-center mb-5">
		<div>
			<svg
				@click="videoOnOff"
				xmlns="http://www.w3.org/2000/svg"
				class="cursor-pointer py-2 mx-5 h-10 w-10 text-red-600 bg-main-200 rounded-full"
				fill="none"
				viewBox="0 0 24 24"
				stroke="currentColor"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="1.5"
					d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"
				/>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="1.5"
					d="M15 13a3 3 0 11-6 0 3 3 0 016 0z"
				/>
				<line v-if="!state.video" x1="3" y1="3" x2="29" y2="29" />
			</svg>
		</div>
		<div>
			<svg
				@click="audioOnOff"
				xmlns="http://www.w3.org/2000/svg"
				class="cursor-pointer py-2 px-2 mx-5 h-10 w-10 text-red-600 bg-main-200 rounded-full"
				fill="none"
				viewBox="0 0 24 24"
				stroke="currentColor"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="1.5"
					d="M19 11a7 7 0 01-7 7m0 0a7 7 0 01-7-7m7 7v4m0 0H8m4 0h4m-4-8a3 3 0 01-3-3V5a3 3 0 116 0v6a3 3 0 01-3 3z"
				/>
				<line v-if="!state.audio" x1="3" y1="3" x2="29" y2="29" />
			</svg>
		</div>
		<div>
			<svg
				v-show="state.filter"
				@click="filterOff"
				xmlns="http://www.w3.org/2000/svg"
				class="cursor-pointer py-2 px-2 mx-5 h-10 w-10 text-red-600 bg-main-200 rounded-full"
				fill="none"
				viewBox="0 0 28 28"
				stroke="currentColor"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="0.1"
					d="M3.28033 2.21967C2.98744 1.92678 2.51257 1.92677 2.21967 2.21967C1.92678 2.51256 1.92677 2.98743 2.21967 3.28033L8.6437 9.70446L3.63623 10.4321C3.41859 10.4637 3.21745 10.5662 3.06394 10.7237C2.67844 11.1192 2.68653 11.7523 3.08202 12.1378L7.81294 16.7493L6.69612 23.2608C6.65894 23.4776 6.69426 23.7006 6.7966 23.8952C7.0536 24.3841 7.65822 24.572 8.14707 24.315L13.9948 21.2407L19.8425 24.315C20.0372 24.4174 20.2602 24.4527 20.4769 24.4155C21.0213 24.3221 21.3868 23.8052 21.2935 23.2608L21.1058 22.1668L24.7194 25.7805C25.0123 26.0734 25.4872 26.0734 25.7801 25.7805C26.073 25.4876 26.073 25.0127 25.7801 24.7198L3.28033 2.21967ZM19.2689 20.3298L19.6438 22.5159L13.9948 19.546L8.34579 22.5159L9.42466 16.2256L4.85451 11.7708L9.96712 11.0279L19.2689 20.3298Z"
					fill="red"
				/>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="0.1"
					d="M12.1169 8.93511L10.9969 7.81512L13.0981 3.55767C13.3425 3.06241 13.9421 2.85907 14.4374 3.1035C14.6346 3.20083 14.7942 3.36045 14.8915 3.55767L17.8154 9.48207L24.3534 10.4321C24.8999 10.5115 25.2786 11.019 25.1992 11.5655C25.1676 11.7831 25.0651 11.9843 24.9076 12.1378L20.2949 16.634L20.0457 16.8641L18.9915 15.8098L23.1351 11.7708L16.8193 10.8531L13.9948 5.13001L12.1169 8.93511Z"
					fill="red"
				/>
			</svg>
		</div>
		<button
			@click="out()"
			class="bg-alert-100 w-32 h-10 text-white rounded-full text-center mx-5"
		>
			<div class="my-auto text-xl font-bold">OUT</div>
		</button>
	</div>
	<out-modal @leaveSession="leaveSession" ref="outModal" />
</template>

<style>
.bottom {
	position: fixed;
	bottom: 0;
	width: 100%;
	/* Height of the footer*/
}
</style>

<script>
import outModal from '@/teleport/out-modal.vue';
import { reactive, ref } from 'vue';

export default {
	name: 'room-bottomvar',
	components: { outModal },
	setup(props, { emit }) {
		const outModal = ref(null);
		const state = reactive({
			video: true,
			audio: true,
			filter: false,
		});

		const videoOnOff = () => {
			if (state.video) {
				state.video = false;
			} else {
				state.video = true;
			}

			emit('videoOnOff', {
				video: state.video,
			});
		};

		const audioOnOff = () => {
			if (state.audio) {
				state.audio = false;
			} else {
				state.audio = true;
			}
			console.log(state.audio);
			emit('audioOnOff', {
				audio: state.audio,
			});
		};

		const filterOff = () => {
			if (state.filter) {
				state.filter = false;
			} else {
				state.filter = true;
			}
			emit('filterOff');
		};

		const out = () => {
			console.log('out');
			outModal.value.open();
		};

		const leaveSession = () => {
			emit('leaveSession');
		};
		return {
			state,
			outModal,
			out,
			videoOnOff,
			audioOnOff,
			filterOff,
			leaveSession,
		};
	},
};
</script>
