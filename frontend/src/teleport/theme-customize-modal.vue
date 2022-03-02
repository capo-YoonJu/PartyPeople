// 테마 설정 모달
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

			<div class="flex flex-col justify-between items-center mx-6 my-3">
				<h1 class="text-2xl font-bold">Theme</h1>

				<!-- 테마 설정 -->
				<div class="w-full h-52 flex flex-col my-6 px-3 py-2">
					<div id="scrolling-content" class="flex overflow-x-auto h-full">
						<div
							v-for="theme in state.roomThemes"
							:key="theme.id"
							class="w-1/2 cursor-pointer h-full px-4 flex-shrink-0"
							@click="clickTheme(theme.id)"
						>
							<img
								:src="theme.url"
								class="rounded-2xl"
								:class="[
									theme.id === state.selectedThemeId
										? 'border-4 border-sub-200'
										: '',
								]"
							/>
						</div>
					</div>
				</div>

				<!-- 입퇴장 효과음 설정 / 버튼 -->
				<div class="flex flex-row justify-between w-full mb-3">
					<div>
						<button
							class="rounded-full w-40 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
							type="button"
						>
							Entry/Exit sounds
						</button>
					</div>
					<div class="flex flew-row items-center">
						<button
							class="rounded-full w-24 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
							type="button"
						>
							Submit
						</button>
						<button
							class="rounded-full w-24 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100 ml-3"
							type="button"
						>
							Cancel
						</button>
					</div>
				</div>
			</div>
		</div>
	</base-modal>
</template>

<style></style>
<script>
import { ref, reactive } from 'vue';
import BaseModal from './base-modal.vue';

export default {
	name: 'ThemeCustomizeModal',

	components: {
		BaseModal,
	},

	setup(props, { emit }) {
		const baseModal = ref(null);

		const state = reactive({
			selectedThemeId: null,

			roomThemes: [
				{
					id: 1,
					url: 'https://images.unsplash.com/photo-1508361001413-7a9dca21d08a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGFsbG93ZWVufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=200&q=20',
				},
				{
					id: 2,
					url: 'https://images.unsplash.com/photo-1508361001413-7a9dca21d08a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGFsbG93ZWVufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=200&q=20',
				},
				{
					id: 3,
					url: 'https://images.unsplash.com/photo-1508361001413-7a9dca21d08a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGFsbG93ZWVufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=200&q=20',
				},
				{
					id: 4,
					url: 'https://images.unsplash.com/photo-1508361001413-7a9dca21d08a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGFsbG93ZWVufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=200&q=20',
				},
			],
		});

		const open = () => {
			baseModal.value.openModal();
		};

		const close = () => {
			baseModal.value.closeModal();
			emit('closeModal');
		};

		const clickTheme = id => {
			state.selectedThemeId === id
				? (state.selectedThemeId = null)
				: (state.selectedThemeId = id);
		};

		return { state, baseModal, clickTheme, open, close };
	},
};
</script>
