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
					<div class="text-center mb-5">
						<p>파티룸에서 나가시겠습니까?</p>
					</div>
					<div class="flex items-center justify-evenly">
						<button
							@click="confirm()"
							class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold py-1 px-5 rounded-full focus:outline-none focus:shadow-outline"
							type="button"
						>
							확인
						</button>
						<button
							@click="close()"
							class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold py-1 px-5 rounded-full focus:outline-none focus:shadow-outline"
							type="button"
						>
							취소
						</button>
					</div>
				</form>
			</div>
		</div>
	</base-modal>
</template>

<style></style>

<script>
import { ref } from 'vue';
import BaseModal from './base-modal.vue';
export default {
	name: 'outConfirm',
	components: {
		BaseModal,
	},
	setup(props, { emit }) {
		const baseModal = ref(null);

		const open = () => {
			console.log('open');
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
		};

		const confirm = () => {
			emit('leaveSession');
		};
		return { baseModal, open, close, confirm };
	},
};
</script>
