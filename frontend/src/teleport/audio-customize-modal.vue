// 오디오 커스터마이징 모달
<template>
	<base-modal ref="baseModal">
		<div
			class="w-full max-w-2xl bg-main-300 shadow-md rounded-xl px-6 pt-6 pb-4"
		>
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
			<div class="flex flex-col justify-between items-center">
				<!-- 버튼 -->
				<div class="flex flex-row justify-between w-full mb-3">
					<div class="flex flew-row items-center">
						<button
							@click="applyAudioCustom()"
							class="rounded-full w-24 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
							type="button"
						>
							적용
						</button>
						<button
							@click="cancelAudioCustom()"
							class="rounded-full w-24 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100 ml-3"
							type="button"
						>
							해제
						</button>
					</div>
				</div>

				<!-- 커스터마이징 아이템 -->
				<div
					class="w-full h-32 border-3 rounded-lg border-main-100 shadow-lg flex flex-col px-3 py-2"
				>
					<div id="scrolling-content" class="flex overflow-x-auto h-full">
						<div
							v-for="custom in state.voiceFilterList"
							:key="custom.id"
							class="w-1/4 cursor-pointer h-full px-4 flex-shrink-0 text-center"
							v-show="true"
							@click="clickAudioCustom(custom)"
						>
							<div
								:class="[
									custom.id === state.selectedCustom
										? 'border-4 border-sub-200'
										: 'mt-3 border-4 border-main-100 py-4 rounded-xl bg-main-200 text-tc-500',
								]"
							>
								{{ custom.url }}
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</base-modal>
</template>
<style>
.userVideo-4 {
	width: 100%;
	height: 100%;
}
</style>
<script>
import { ref, reactive } from 'vue';
import BaseModal from './base-modal.vue';
import voiceFilterJson from '@/assets/json-assets/voiceFilterList.json';

export default {
	name: 'AudioCustomizeModal',

	components: {
		BaseModal,
	},

	setup(props, { emit }) {
		const baseModal = ref(null);

		const state = reactive({
			selectedCustom: null,
			isClicked: false,
			isCancelled: true,
			// 각 항목을 json 파일로 따로 저장하여 json파일 불러옴
			voiceFilterList: voiceFilterJson,
		});

		// 모달 열기
		const open = () => {
			baseModal.value.openModal();
		};

		// 모달 닫기
		const close = () => {
			baseModal.value.closeModal();
			emit('closeModal');
		};

		const clickAudioCustom = customObject => {
			console.log('선택된 항목 : ' + customObject);
			state.selectedCustom = customObject.command;
			state.isClicked = true;
		};

		// 카테고리에 따라 파티룸 내부 함수 호출(emit)
		const applyAudioCustom = () => {
			if (customizeValidationCheck()) {
				// 이전에 적용된 필터 해제 후 새롭게 적용
				cancelAudioCustom();
				// 선택한 보이스 필터 emit
				emit('voiceFilter', state.selectedCustom);
			}
			state.isClicked = false;

			if (!state.isCancelled) {
				// bottombar 버튼 활성화
				emit('bottombarFilterBtn', true);
			}

			// 모달 창 닫기
			close();
		};

		// 커스텀 적용 여부 체크
		const customizeValidationCheck = () => {
			// 필터 종류를 클릭할 경우만 적용
			if (state.selectedCustom != null && state.isClicked) return true;
			else return false;
		};

		// 오디오 커스텀 해제
		const cancelAudioCustom = () => {
			emit('filterOff');
			// bottombar 버튼 비활성화
			emit('bottombarFilterBtn', false);
			state.isCancelled = true;
		};

		return {
			state,
			baseModal,
			open,
			close,
			clickAudioCustom,
			applyAudioCustom,
			customizeValidationCheck,
			cancelAudioCustom,
		};
	},
};
</script>
