// 비디오 커스터마이징 모달
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
				<!-- 커스터마이징 적용 미리보기 창 -->
				<div
					class="w-80 border-main-100 border-2 h-52 mt-2 mb-6 bg-main-100 shadow-lg"
				>
					<user-video
						class="w-full h-full"
						:stream-manager="state.publisher"
						:isPreview="true"
					/>
				</div>

				<!-- 카테고리와 버튼 -->
				<div class="flex flex-row justify-between w-full mb-3">
					<div
						class="relative inline-flex border-3 rounded-lg border-main-100 w-1/4 m-0 h-9 p-0"
					>
						<select
							v-model="state.selectedCategory"
							class="cursor-pointer font-bold px-4 py-0 text-sm form-select border-0 rounded-md text-gray-600 w-full h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
						>
							<option
								v-for="category in state.categories"
								:value="category.name"
								:key="category.id"
							>
								{{ category.name }}
							</option>
						</select>
					</div>
					<div class="flex flew-row items-center">
						<button
							@click="applyVideoCustom()"
							class="rounded-full w-24 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
							type="button"
						>
							적용
						</button>
						<button
							@click="cancelVideoCustom()"
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
					<!-- 스티커 선택 -->
					<div
						v-if="state.selectedCategory === '스티커'"
						id="scrolling-content"
						class="flex overflow-x-auto h-full"
					>
						<div
							v-for="custom in state.stickerList"
							:key="custom.id"
							class="w-1/4 cursor-pointer h-full px-4 flex-shrink-0"
							v-show="showCategory(custom.category)"
							@click="clickVideoCustom(custom)"
						>
							<img
								:src="custom.url"
								:class="[
									custom.id === state.selectedCustomId
										? 'border-4 border-sub-200'
										: '',
								]"
							/>
						</div>
					</div>
					<!-- 비주얼 필터 선택 -->
					<div
						v-if="state.selectedCategory === '필터'"
						id="scrolling-content"
						class="flex overflow-x-auto h-full"
					>
						<div
							v-for="custom in state.visualFilterList"
							:key="custom.id"
							class="w-1/4 cursor-pointer h-full px-4 flex-shrink-0 text-center"
							v-show="true"
							@click="clickVideoCustom(custom)"
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
					<!-- 문구 선택 -->
					<div
						v-if="state.selectedCategory === '문구'"
						id="scrolling-content"
						class="flex-col overflow-x-auto h-full"
					>
						<div>
							<select
								v-model="state.textList.font"
								class="m-1 border-main-100 border-2 cursor-pointer font-bold py-0 text-sm form-select border-0 rounded-md text-gray-600 h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
							>
								<option value="" disabled selected>글꼴 선택</option>
								<option
									v-for="font in state.fonts"
									:value="font.name"
									:key="font.id"
								>
									{{ font.name }}
								</option>
							</select>
							<select
								v-model="state.textList.location"
								class="m-1 border-main-100 border-2 cursor-pointer font-bold py-0 text-sm form-select rounded-md text-gray-600 h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
							>
								<option value="" disabled selected>위치 선택</option>
								<option
									v-for="location in state.locations"
									:value="location.name"
									:key="location.id"
								>
									{{ location.value }}
								</option>
							</select>
							<input
								type="number"
								placeholder="글자 크기"
								v-model="state.textList.fontSize"
								class="m-1 border-main-100 border-2 cursor-pointer font-bold py-0 text-sm form-select rounded-md text-gray-600 h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
							/>
						</div>
						<input
							type="text"
							placeholder="입력할 문구"
							v-model="state.textList.inputText"
							class="overflow-x-auto w-full h-14 border-main-100 border-2 font-bold text-sm rounded-md text-gray-600 border-transparent focus:border-transparent focus:ring-0 appearance-none"
						/>
					</div>
				</div>
			</div>
			<a
				href="https://www.freepik.com/vectors/design"
				class="font-thin m-2 text-xs text-gray-400"
				>Design vector created by macrovector - www.freepik.com</a
			>
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
import { ref, reactive, computed } from 'vue';
import { useStore } from 'vuex';
import BaseModal from './base-modal.vue';
import UserVideo from '@/views/partyroom/components/user-video.vue';
import stickerListJson from '@/assets/json-assets/stickerList.json';
import visualFilterJson from '@/assets/json-assets/visualFilterList.json';

export default {
	name: 'VideoCustomizeModal',

	components: {
		BaseModal,
		UserVideo,
	},

	setup(props, { emit }) {
		const baseModal = ref(null);

		const store = useStore();
		const state = reactive({
			publisher: computed(() => store.getters['root/getPublisher']),

			selectedCategory: '스티커',
			selectedCustom: null,
			isClicked: false,
			isCancelled: true,

			categories: [
				{ id: 1, name: '스티커' },
				{ id: 2, name: '필터' },
				{ id: 3, name: '문구' },
			],

			fonts: [
				{ id: 1, name: 'Cantarell' },
				{ id: 2, name: 'Sans' },
			],

			locations: [
				{ id: 1, name: 'Top|Right', value: '↗' },
				{ id: 2, name: 'Top|Center', value: '↑' },
				{ id: 3, name: 'Top|Left', value: '↖' },
				{ id: 4, name: 'Center|Right', value: '→' },
				{ id: 5, name: 'Center|Center', value: '○' },
				{ id: 6, name: 'Center|Left', value: '←' },
				{ id: 7, name: 'Bottom|Right', value: '↘' },
				{ id: 8, name: 'Bottom|Center', value: '↓' },
				{ id: 9, name: 'Bottom|Left', value: '↙' },
			],

			// 각 항목을 json 파일로 따로 저장하여 json파일 불러옴
			stickerList: stickerListJson,

			visualFilterList: visualFilterJson,

			textList: {
				inputText: '',
				location: '',
				font: '',
				fontSize: 25,
			},
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

		// 옵션 카테고리가 기본값인 '스티커'이거나 함수의 인자로 들어온 category와 같은 경우에만 true 리턴
		const showCategory = category => {
			return (
				state.selectedCategory == '스티커' || state.selectedCategory == category
			);
		};

		// 스티커, 비주얼 필터 선택 시 종류를 클릭했는지 여부 검사
		const clickVideoCustom = customObject => {
			console.log('선택된 항목 : ' + customObject);
			state.selectedCustom = customObject;
			state.isClicked = true;
			previewVideoCustom();
		};

		const previewVideoCustom = () => {
			var selected = state.selectedCategory;

			if (customizeValidationCheck()) {
				// 이전에 적용된 필터 해제 후 새롭게 적용
				cancelVideoCustom();

				console.log('apply 클릭 : ' + selected);

				if (selected === '스티커') {
					emit('stickerOverlay', state.selectedCustom);
				} else if (selected === '필터') {
					emit('visualFilter', state.selectedCustom);
				} else if (selected === '문구') {
					emit('textOverlay', {
						inputText: state.textList.inputText,
						valignment: state.textList.location
							? state.textList.location.split('|')[0].toLowerCase()
							: '',
						halignment: state.textList.location
							? state.textList.location.split('|')[1].toLowerCase()
							: '',
						font: state.textList.font,
						fontSize: state.textList.fontSize,
					});
				}
			}
			state.isClicked = false;
		};

		// 카테고리에 따라 파티룸 내부 함수 호출(emit)
		const applyVideoCustom = () => {
			// 미리보기 적용
			previewVideoCustom();

			if (!state.isCancelled) {
				// bottombar 버튼 활성화
				emit('bottombarFilterBtn', true);
			}

			// 모달 창 닫기
			close();
		};

		// 커스텀 적용 여부 체크
		const customizeValidationCheck = () => {
			if (state.selectedCategory === '문구') {
				// 문구 필터 선택 시
				if (
					// 문구가 입력된 경우만 필터 적용
					state.textList.inputText != null &&
					state.textList.inputText.length > 0
				) {
					if (
						// 문구 위치를 선택하지 않았다면 기본값('Top|Center')으로 설정
						state.textList.location === null ||
						state.textList.location.length === 0
					) {
						state.textList.location = 'Top|Center';
					}
					if (
						// 문구 폰트를 선택하지 않았다면 기본값('Cantarell')으로 설정
						state.textList.font === null ||
						state.textList.font.length === 0
					) {
						state.textList.font = 'Cantarell';
					}
					return true;
				} else {
					// 문구가 입력되지 않아 필터 적용 안함
					return false;
				}
			} else {
				// 스티커, 비주얼 필터 선택 시 스티커와 비주얼 필터 종류를 클릭할 경우만 적용
				if (state.selectedCustom != null && state.isClicked) return true;
				else return false;
			}
		};

		// 비디오 커스텀 해제
		const cancelVideoCustom = () => {
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
			showCategory,
			clickVideoCustom,
			previewVideoCustom,
			applyVideoCustom,
			customizeValidationCheck,
			cancelVideoCustom,
		};
	},
};
</script>
