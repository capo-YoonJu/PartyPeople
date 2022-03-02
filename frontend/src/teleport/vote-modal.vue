<template>
	<base-modal ref="baseModal">
		<div class="flex justify-center">
			<div class="w-full max-w-xs">
				<form class="bg-main-200 shadow-md rounded-t-xl px-8 pt-6 pb-8">
					<!-- 투표 제목 -->
					<div
						class="shadow appearance-none border rounded-lg bg-white w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
					>
						<p>{{ state.voteInfo.voteTopic }}</p>
					</div>
				</form>
				<form class="bg-main-300 shadow-md rounded-b-xl px-8 pt-4 pb-8 mb-4">
					<!-- 투표 항목 -->
					<ul>
						<li
							class="mt-4 flex justify-between"
							v-for="i in state.voteInfo.voteList.length - 1"
							:key="i"
						>
							<label
								class="shadow text-left appearance-none border bg-white rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							>
								{{ state.voteInfo.voteList[i] }}
							</label>
							<input
								class="shadow appearance-none border rounded-full h-4 w-4 mx-2 mb-1 mt-3 leading-tight focus:outline-none focus:shadow-outline"
								:id="state.voteInfo.voteList[i]"
								:value="state.voteInfo.voteList[i]"
								type="radio"
								v-model="state.checked"
							/>
						</li>
					</ul>
					<!-- 투표 완료 버튼 -->
					<div class="flex items-center justify-center mt-4">
						<button
							class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold h-10 py-1 px-24 rounded-full focus:outline-none focus:shadow-outline"
							type="button"
							@click="sendVoteResult"
						>
							투표완료
						</button>
					</div>
				</form>
				<!-- <p class="text-center text-gray-500 text-xs">
					&copy;2022 PartyPeople Corp. All rights reserved.
				</p> -->
			</div>
		</div>
	</base-modal>
</template>
<style></style>

<script>
import { computed, reactive, ref } from '@vue/reactivity';
import { useStore } from 'vuex';
import BaseModal from './base-modal.vue';

export default {
	components: { BaseModal },
	name: 'voteModal',
	setup(props, { emit }) {
		const baseModal = ref(null);
		const store = useStore();
		const state = reactive({
			voteInfo: computed(() => store.getters['root/getVoteInfo']),
			voteComplete: computed(() => store.getters['root/getVoteResult']),
			checked: null,
		});

		const open = voteInfo => {
			// state.voteInfo.voteList.shift()
			console.log('open', voteInfo);
			store.commit('root/setVote', voteInfo);
			console.log(state.voteInfo.voteList);
			baseModal.value.openModal();
		};

		const close = () => {
			baseModal.value.closeModal();
			emit('closeModal');
		};

		const sendVoteResult = () => {
			let voteResult = store.getters['root/getVoteResult'];
			voteResult[state.checked] = voteResult[state.checked] + 1;
			console.log('1.sendVote', voteResult);
			emit('sendVoteResult');
			close();
		};

		return { state, store, open, close, baseModal, sendVoteResult };
	},
};
</script>
