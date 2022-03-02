// 회원 가입 모달 컴포넌트
<template>
	<teleport to="#modal-area">
		<transition name="fade">
			<div
				v-show="isOpen"
				class="overflow-y-auto overflow-x-hidden fixed inset-1/4 z-50 justify-center items-center md:h-full"
			>
				<slot></slot>
			</div>
		</transition>
	</teleport>
</template>
<style>
.fade-enter-active,
.fade-leave-active {
	transition: opacity 0.3s ease-out;
}

.fade-enter-from,
.fade-leave-to {
	opacity: 0;
}
</style>
<script>
import { ref } from 'vue';

export default {
	name: 'BaseModal',

	setup() {
		const isOpen = ref(false);

		const closeModal = () => {
			console.log('modalclose');
			isOpen.value = false;
		};

		const openModal = () => {
			console.log('modalopen');
			isOpen.value = true;
		};

		return { isOpen, closeModal, openModal };
	},
};
</script>
