<template>
	<div v-if="streamManager" class="userVideoContainer">
		<ov-video
			class="rounded-xl"
			:stream-manager="streamManager"
			:class="{ userSpeaking: isSpeaking }"
		/>
		<div
			:isPreview="isPreview"
			v-show="!isPreview"
			class="userOverlay rounded-b-xl text-tc-500"
		>
			<p>{{ clientData }} / {{ userNickname }}</p>
		</div>
	</div>
</template>
<style>
.userVideoContainer {
	position: relative;
}
.userOverlay {
	display: none;
}
.userVideoContainer:hover .userOverlay {
	display: block;
	width: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	position: absolute;
	bottom: 0px;
}
.userSpeaking {
	outline: 4px solid #6667ab;
}
</style>
<script>
import { computed } from 'vue';
import OvVideo from './ov-video';
import { useStore } from 'vuex';
export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	data() {
		return {
			store: useStore(),
			isSpeaking: false,
			clientData: computed(() => {
				const { clientData } = this.getConnectionData();
				this.getSubscriberNickname(clientData);
				return clientData;
			}),
			userNickname: null,
		};
	},

	props: {
		streamManager: Object,
		isPreview: Boolean,
	},
	methods: {
		getConnectionData() {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
		getSubscriberNickname(userId) {
			this.store
				.dispatch('root/requestNickname', userId)
				.then(res => {
					console.log(res);
					this.userNickname = res.data.nickname;
				})
				.catch(err => {
					console.log(err);
				});
		},
		highlightOn(name) {
			const { clientData } = this.getConnectionData();

			if (clientData === name) {
				this.isSpeaking = true;
				return true;
			}

			return false;
		},
		highlightOff(name) {
			const { clientData } = this.getConnectionData();

			if (clientData === name) {
				this.isSpeaking = false;
				return true;
			}

			return false;
		},
	},
};
</script>
