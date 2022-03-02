<template>
	<base-modal ref="baseModal">
		<div class="flex justify-center">
			<div class="w-full max-w-xs">
				<form class="bg-main-300 shadow-md rounded-xl px-5 pt-5 pb-8 mb-4">
					<div
						class="flex justify-between items-start rounded-t border-none bg-main-300"
					>
						<svg
							class="ml-16 w-36 h-36"
							version="1.1"
							id="Capa_1"
							xmlns="http://www.w3.org/2000/svg"
							xmlns:xlink="http://www.w3.org/1999/xlink"
							viewBox="0 0 511 511"
							style="enable-background: new 0 0 511 511"
							xml:space="preserve"
						>
							<path
								d="M407.881,143.119c-11.693-11.693-24.497-21.867-38.167-30.444l12.062-20.891l21.218,12.25
                c1.181,0.682,2.471,1.006,3.743,1.006c2.592,0,5.113-1.346,6.502-3.751c2.071-3.587,0.842-8.174-2.745-10.245l-55.426-32
                c-3.587-2.072-8.174-0.842-10.245,2.745c-2.071,3.587-0.842,8.174,2.745,10.245l21.218,12.25l-12.046,20.864
                c-21.461-11.449-44.746-19.152-69.038-22.77C297.101,73.693,303,61.275,303,47.5C303,21.309,281.691,0,255.5,0S208,21.309,208,47.5
                c0,13.775,5.899,26.193,15.298,34.877c-24.292,3.618-47.576,11.321-69.037,22.77l-12.046-20.864l21.218-12.25
                c3.587-2.071,4.816-6.658,2.745-10.245c-2.071-3.587-6.657-4.816-10.245-2.745l-55.426,32c-3.587,2.071-4.816,6.658-2.745,10.245
                c1.389,2.406,3.91,3.751,6.502,3.751c1.272,0,2.562-0.324,3.743-1.006l21.218-12.25l12.062,20.891
                c-13.67,8.577-26.474,18.751-38.167,30.444C62.416,183.821,40,237.938,40,295.5s22.416,111.679,63.119,152.381
                S197.938,511,255.5,511s111.679-22.416,152.381-63.119S471,353.062,471,295.5S448.584,183.821,407.881,143.119z M223,47.5
                c0-17.92,14.58-32.5,32.5-32.5S288,29.58,288,47.5S273.42,80,255.5,80S223,65.42,223,47.5z M255.5,496
                C144.944,496,55,406.056,55,295.5S144.944,95,255.5,95S456,184.944,456,295.5S366.056,496,255.5,496z"
							/>
							<path
								d="M263,264.919V151.5c0-4.142-3.358-7.5-7.5-7.5s-7.5,3.358-7.5,7.5v113.409c-13.759,3.374-24,15.806-24,30.591
                s10.241,27.216,24,30.591V367.5c0,4.142,3.358,7.5,7.5,7.5s7.5-3.358,7.5-7.5v-41.409c13.759-3.374,24-15.806,24-30.591
                c0-7.765-2.851-15.226-8.027-21.006C274.704,269.725,269.11,266.421,263,264.919z M255.5,312c-9.098,0-16.5-7.402-16.5-16.5
                s7.402-16.5,16.5-16.5c4.687,0,9.169,2.004,12.298,5.5c2.709,3.026,4.202,6.933,4.202,11C272,304.598,264.598,312,255.5,312z"
							/>
							<path
								d="M255.5,112C154.318,112,72,194.318,72,295.5S154.318,479,255.5,479S439,396.682,439,295.5S356.682,112,255.5,112z
                M255.5,464C162.589,464,87,388.411,87,295.5S162.589,127,255.5,127S424,202.589,424,295.5S348.411,464,255.5,464z"
							/>
						</svg>
						<button
							@click="close()"
							type="button"
							class="text-tc-500 bg-alert-200 hover:bg-alert-100 hover:text-tc-500 rounded-full text-sm p-1.5 inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white"
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
					<div class="flex justify-center mt-5 mb-10">
						<div class="mt-2 p-1 bg-white rounded-lg shadow-xl w-40">
							<div class="flex justify-center">
								<input
									v-model="state.timerTime.min"
									type="number"
									class="border-none text-xl mt-2 font-bold w-16"
									max="59"
									min="0"
									placeholder="00"
								/>
								<span class="text-xl mt-3.5 font-bold">:</span>
								<input
									v-model="state.timerTime.sec"
									type="number"
									class="border-none text-xl ml-2 mt-2 font-bold w-16"
									max="59"
									min="0"
									placeholder="00"
								/>
							</div>
						</div>
					</div>
					<div class="flex items-center justify-center">
						<button
							@click="timerStart(), close()"
							class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold h-10 py-1 px-24 rounded-full focus:outline-none focus:shadow-outline"
							type="button"
						>
							START
						</button>
					</div>
				</form>
			</div>
		</div>
	</base-modal>
</template>
<style>
.input-none {
	border: none;
}
</style>
<script>
import { reactive } from '@vue/reactivity';
import BaseModal from './base-modal.vue';
import { ref } from 'vue';
import { useStore } from 'vuex';

export default {
	name: 'timerCreateModal',
	components: {
		BaseModal,
	},
	setup(props, { emit }) {
		const store = useStore();
		const state = reactive({
			timerTime: {
				min: 0,
				sec: 0,
				count: true,
			},
		});
		const baseModal = ref(null);
		const open = () => {
			console.log('timeropen');
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
			emit('closeModal');
		};

		const timerStart = () => {
			console.log('timerStart');
			store.commit('root/setTimer', state.timerTime);
		};

		return { state, open, close, baseModal, timerStart };
	},
};
</script>
