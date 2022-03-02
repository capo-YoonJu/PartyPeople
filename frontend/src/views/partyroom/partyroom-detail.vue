<template>
	<div class="h-screen w-screen flex bg-gradient-to-t from-main-300 to-sub-300">
		<div class="fixed inset-0 flex z-40">
			<room-sidebar
				@sendRoulletteMessage="sendRoulletteMessage"
				@startVote="startVote"
				@sendVoteResult="sendVoteResult"
				@toggle-whiteboard="toggleWhiteboard"
				@stickerOverlay="applyStickerFilter"
				@visualFilter="applyVisualFilter"
				@textOverlay="applyTextFilter"
				@voiceFilter="applyVoiceFilter"
				@filterOff="filterOff"
				@bottombarFilterBtn="bottombarFilterBtn"
				ref="roomSidebar"
			></room-sidebar>
			<div id="session" class="w-full pl-32 pr-80" v-if="session">
				<!-- 타이머 -->
				<div id="session-header">
					<div class="mx-auto">
						<timer @startCountdown="startCountdown" ref="timer"></timer>
					</div>
				</div>
				<!-- 컨텐츠가 없는 경우 -->
				<div v-show="!isWhiteboardOpen && !isRouletteOpen" class="mt-2">
					<div
						v-if="currentUserCount == 0"
						id="video-container-1"
						class="flex flex-wrap mx-8 justify-center mt-16 gap-4"
					>
						<user-video
							class="userVideo-1"
							:stream-manager="publisher"
							ref="pubVideoRef"
						/>
					</div>
					<div
						v-else-if="currentUserCount < 4"
						id="video-container-2"
						class="flex flex-wrap mx-8 justify-center gap-4"
					>
						<user-video
							class="userVideo-2"
							:stream-manager="publisher"
							ref="pubVideoRef"
						/>
						<user-video
							class="userVideo-2"
							v-for="sub in subscribers"
							:key="sub.stream.connection.connectionId"
							:stream-manager="sub"
							ref="userVideoRef"
						/>
					</div>
					<div
						v-else-if="currentUserCount < 6"
						id="video-container-3"
						class="flex flex-wrap mx-8 justify-center gap-4"
					>
						<user-video
							class="userVideo-3"
							:stream-manager="publisher"
							ref="pubVideoRef"
						/>
						<user-video
							class="userVideo-3"
							v-for="sub in subscribers"
							:key="sub.stream.connection.connectionId"
							:stream-manager="sub"
							ref="userVideoRef"
						/>
					</div>
					<div
						v-else
						id="video-container-4"
						class="flex flex-wrap mx-8 justify-center gap-4"
					>
						<user-video
							class="userVideo-4"
							:stream-manager="publisher"
							ref="pubVideoRef"
						/>
						<user-video
							class="userVideo-4"
							v-for="sub in subscribers"
							:key="sub.stream.connection.connectionId"
							:stream-manager="sub"
							ref="userVideoRef"
						/>
					</div>
				</div>
				<!-- 컨텐츠(룰렛 / 화이트보드)를 실행중인 경우 -->
				<div class="grid grid-rows-4">
					<vue-agile
						class="row-span-1 p-3"
						:slides-to-show="3"
						:infinite="false"
						:nav-buttons="false"
						v-show="isWhiteboardOpen || isRouletteOpen"
					>
						<user-video
							class="h-full slide max-h-48 mx-2"
							:stream-manager="publisher"
						/>
						<user-video
							class="h-full slide max-h-48 mx-2"
							v-for="sub in subscribers"
							:key="sub.stream.connection.connectionId"
							:stream-manager="sub"
						/>
					</vue-agile>
					<!-- 화이트보드 컴포넌트 (실행시에만 show) -->
					<whiteboard
						ref="whiteboard"
						v-show="isWhiteboardOpen"
						class="row-span-3 justify-center items-center mb-28"
						@send-whiteboard-signal="sendWhiteboardSignal"
						@send-reset-signal="sendResetSignal"
						@close-whiteboard="closeWhiteboard"
					></whiteboard>
					<!-- 룰렛 컴포넌트 (실행시에만 show) -->
					<roulette
						ref="roulette"
						v-show="isRouletteOpen"
						class="row-span-3 justify-center items-center mb-16"
						@closeRoulette="closeRoulette"
					></roulette>
				</div>
			</div>
			<room-chat
				ref="chat"
				@message="sendMessage"
				:subscribers="subscribers"
			></room-chat>
			<room-bottombar
				ref="bottombar"
				@filterOff="filterOff"
				@audioOnOff="audioOnOff"
				@videoOnOff="videoOnOff"
				@leaveSession="leaveSession()"
			></room-bottombar>
		</div>
	</div>
</template>
<style>
.userVideo-1 {
	width: 80%;
	height: 100%;
}
.userVideo-2 {
	width: 38%;
	height: 100%;
}
.userVideo-3 {
	width: 32%;
	height: 100%;
}
.userVideo-4 {
	width: 24%;
	height: 100%;
}
/* .contents-container {
  padding-left: 5%;
  padding-right: 5%;
} */
</style>
<script>
import { ref } from 'vue';
import roomSidebar from './components/room-sidebar.vue';
import RoomChat from './components/room-chat.vue';
import UserVideo from './components/user-video.vue';
import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import roomBottombar from './components/room-bottombar.vue';
import timer from './components/timer.vue';
import Roulette from './components/roulette.vue';
import Whiteboard from './components/whiteboard.vue';
import { swal } from '@/assets/js/common';
import { VueAgile } from 'vue-agile';

const OPENVIDU_SERVER_URL = 'https://pparttypeople.kro.kr:4443';
const OPENVIDU_SERVER_SECRET = 'a106ssafy0183';

export default {
	components: {
		roomSidebar,
		RoomChat,
		UserVideo,
		timer,
		roomBottombar,
		Roulette,
		Whiteboard,
		VueAgile,
	},
	name: 'conference-detail',
	props: {
		conferenceId: {
			type: Number,
		},
		userName: {
			type: String,
		},
	},
	setup() {
		const isRouletteOpen = ref(false);
		const isWhiteboardOpen = ref(false);
		// const isTimerOpen = ref(false);

		return { isRouletteOpen, isWhiteboardOpen };
	},
	data() {
		return {
			OV: undefined,
			session: undefined,
			mainStreamManager: undefined,
			publisher: undefined,
			subscribers: [],
			mySessionId: this.conferenceId,
			myUserName: this.userName,
			router: useRouter(),
			store: useStore(),
			rouletteTopic: '',
		};
	},
	computed: {
		currentUserCount: function () {
			return this.subscribers.length;
		},
	},
	methods: {
		joinSession() {
			// --- Get an OpenVidu object ---
			this.OV = new OpenVidu();

			// --- Init a session ---
			this.session = this.OV.initSession();

			// --- Optimize Audio Settings
			this.OV.setAdvancedConfiguration({
				publisherSpeakingEventsOptions: {
					interval: 50,
					threshold: -75,
				},
			});

			// --- Specify the actions when events take place in the session ---

			// On every new Stream received...
			this.session.on('streamCreated', ({ stream }) => {
				const subscriber = this.session.subscribe(stream);

				subscriber.userId = this.myUserName; // subscriber Object에 userName 추가
				this.subscribers.push(subscriber);
			});

			// On every Stream destroyed...
			this.session.on('streamDestroyed', ({ stream }) => {
				const index = this.subscribers.indexOf(stream.streamManager, 0);
				if (index >= 0) {
					this.subscribers.splice(index, 1);
				}
			});

			// On every asynchronous exception...
			this.session.on('exception', ({ exception }) => {
				console.warn(exception);
			});

			// public 채팅 signal 받기
			this.session.on('signal:public-chat', event => {
				this.$refs.chat.addMessage(
					event.data,
					JSON.parse(event.data).sender === this.myUserName,
					false,
				);
			});

			// private 채팅 signal 받기
			this.session.on('signal:private-chat', event => {
				this.$refs.chat.addMessage(event.data, false, true);
			});

			// 타이머 signal 받기
			this.session.on('signal:timer', event => {
				this.$refs.timer.startCountdown(event.data);
			});

			// 룰렛 signal 받기
			this.session.on('signal:roulette-result', event => {
				// vuex state에 signal로 받은 참가자, 당첨자 정보 저장
				this.store.commit('root/setRouletteSignalData', JSON.parse(event.data));
				this.rouletteTopic = JSON.parse(event.data).rouletteTopic;
				// 룰렛 컴포넌트 show
				this.isRouletteOpen = true;
				// 사이드바 모달 열려있는 상태 설정
				this.$refs.roomSidebar.state.isAnyModalOpen = true;
				// 룰렛 애니메이션 실행
				this.$refs.roulette.playRoulette();
			});

			// 투표 signal 받기
			this.session.on('signal:vote', event => {
				this.$refs.roomSidebar.startVote(event.data);
			});

			// 투표 결과 signal 받기
			this.session.on('signal:voteResult', event => {
				this.$store.commit('root/setVoteResult', JSON.parse(event.data));
				let voteResult = this.$store.getters['root/getVoteResult'];
				let sum = 0;
				for (let i of Object.values(voteResult)) {
					sum += i;
				}
				console.log('sum:', sum, '참가자 수:', this.subscribers.length);
				if (sum == this.subscribers.length + 1) {
					// alert(`투표결과\n${JSON.stringify(voteResult)}`);
					this.voteComplete(voteResult);
				}
			});

			// ctx 정보 보내기 step 3
			// whiteboard signal 받기
			this.session.on('signal:whiteboard', event => {
				this.$refs.whiteboard.addWhiteboardSignal(event.data);
			});

			// 모든 참가자의 화이트보드 초기화 step 3
			this.session.on('signal:reset-whiteboard', () => {
				this.$refs.whiteboard.resetWhiteboard();
			});

			// 발언자 감지
			this.session.on('publisherStartSpeaking', event => {
				if (this.$refs.userVideoRef) {
					for (let i = 0; i < this.$refs.userVideoRef.length; i++) {
						this.$refs.userVideoRef[i].highlightOn(
							JSON.parse(event.connection.data).clientData,
						);
					}
				}

				this.$refs.pubVideoRef.highlightOn(
					JSON.parse(event.connection.data).clientData,
				);
			});

			// 발언자 감지
			this.session.on('publisherStopSpeaking', event => {
				if (this.$refs.userVideoRef) {
					for (let i = 0; i < this.$refs.userVideoRef.length; i++) {
						this.$refs.userVideoRef[i].highlightOff(
							JSON.parse(event.connection.data).clientData,
						);
					}
				}

				this.$refs.pubVideoRef.highlightOff(
					JSON.parse(event.connection.data).clientData,
				);
			});

			// --- Connect to the session with a valid user token ---

			// 'getToken' method is simulating what your server-side should do.
			// 'token' parameter should be retrieved and returned by your own backend
			this.getToken(this.mySessionId).then(token => {
				this.session
					.connect(token, { clientData: this.myUserName })
					.then(() => {
						let publisher = this.OV.initPublisher(undefined, {
							audioSource: undefined, // The source of audio. If undefined default microphone
							videoSource: undefined, // The source of video. If undefined default webcam
							publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
							publishVideo: true, // Whether you want to start publishing with your video enabled or not
							resolution: '640x360', // The resolution of your video
							frameRate: 30, // The frame rate of your video
							insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
							mirror: false, // Whether to mirror your local video or not
						});

						publisher.stream.userName = 'asdfasdf';

						// Kurento 필터 적용을 위해 remote를 subscribe
						publisher.subscribeToRemote(true);

						this.mainStreamManager = publisher;
						this.publisher = publisher;

						// store의 publisher 업데이트
						this.$store.commit('root/setPublisher', this.publisher);
						console.log(this.$store.getters['root/getPublisher']);

						// --- Publish your stream ---
						this.session.publish(this.publisher);
					})
					.catch(error => {
						console.log(
							'There was an error connecting to the session:',
							error.code,
							error.message,
						);
					});
			});

			swal(
				true,
				'top-right',
				1500,
				'success',
				'파티룸에 입장하셨습니다.',
				null,
			);

			window.addEventListener('beforeunload', this.leaveSession);
		},

		leaveSession() {
			// --- Leave the session by calling 'disconnect' method over the Session object ---
			if (this.session) this.session.disconnect();

			this.session = undefined;
			this.mainStreamManager = undefined;
			this.publisher = undefined;
			this.subscribers = [];
			this.OV = undefined;

			// store의 publisher 업데이트
			this.$store.commit('root/setPublisher', this.publisher);

			this.store.dispatch('root/leaveSession', this.mySessionId);

			window.removeEventListener('beforeUnmount', this.leaveSession);
			this.router.push({ name: 'Home' });

			swal(
				true,
				'top-right',
				1500,
				'success',
				'파티룸에서 퇴장하셨습니다.',
				null,
			);
		},

		getToken(mySessionId) {
			return this.createSession(mySessionId).then(sessionId =>
				this.createToken(sessionId),
			);
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
		createSession(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(
						`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
						JSON.stringify({
							customSessionId: sessionId,
						}),
						{
							auth: {
								username: 'OPENVIDUAPP',
								password: OPENVIDU_SERVER_SECRET,
							},
						},
					)
					.then(response => response.data)
					.then(data => resolve(data.id))
					.catch(error => {
						if (error.response.status === 409) {
							resolve(sessionId);
						} else {
							console.warn(
								`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`,
							);
							if (
								window.confirm(
									`No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`,
								)
							) {
								location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
							}
							reject(error.response);
						}
					});
			});
		},

		// See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessionsltsession_idgtconnection
		createToken(sessionId) {
			return new Promise((resolve, reject) => {
				axios
					.post(
						`${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
						{
							// filter 사용을 위해 create connection 시 body를 추가
							type: 'WEBRTC',
							role: 'PUBLISHER',
							kurentoOptions: {
								allowedFilters: ['GStreamerFilter', 'FaceOverlayFilter'],
							},
						},
						{
							auth: {
								username: 'OPENVIDUAPP',
								password: OPENVIDU_SERVER_SECRET,
							},
						},
					)
					.then(response => response.data)
					.then(data => resolve(data.token))
					.catch(error => reject(error.response));
			});
		},

		sendMessage({ content, to }) {
			let now = new Date();
			let current = now.toLocaleTimeString([], {
				hour: '2-digit',
				minute: '2-digit',
				hour12: false, // true인 경우 오후 10:25와 같이 나타냄.
			});

			let messageData = {
				content: content,
				sender: this.myUserName,
				time: current,
			};

			// 전체 메시지
			if (to === 'all') {
				this.session
					.signal({
						data: JSON.stringify(messageData),
						to: [],
						type: 'public-chat',
					})
					.then(() => {
						console.log('메시지 전송 완료');
					})
					.catch(error => {
						console.log(error);
					});
			}

			// 개인 메시지
			if (to !== 'all') {
				this.session
					.signal({
						data: JSON.stringify(messageData),
						to: [to],
						type: 'private-chat',
					})
					.then(() => {
						// 내가 보낸 개인 메시지 추가
						this.$refs.chat.addMessage(JSON.stringify(messageData), true, true);
						console.log('메시지 전송 완료');
					})
					.catch(error => {
						console.log(error);
					});
			}
		},

		startCountdown({ timer }) {
			this.session
				.signal({
					data: JSON.stringify(timer),
					to: [],
					type: 'timer',
				})
				.then(() => {
					console.log('타이머 전송 완료');
				})
				.catch(error => {
					console.log(error);
				});
		},
		startVote({ voteInfo }) {
			this.session
				.signal({
					data: JSON.stringify(voteInfo),
					to: [],
					type: 'vote',
				})
				.then(() => {
					console.log('투표 전송 완료');
				})
				.catch(error => {
					console.log('투표 전송 실패', error);
				});
		},

		sendVoteResult() {
			console.log('3.sendVote');
			let voteResult = this.$store.getters['root/getVoteResult'];
			this.session
				.signal({
					data: JSON.stringify(voteResult),
					to: [],
					type: 'voteResult',
				})
				.then(() => {
					console.log('투표 결과 전송 완료');
				})
				.catch(error => {
					console.log('투표 결과 전송 실패', error);
				});
		},

		voteComplete(voteResult) {
			let now = new Date();
			let current = now.toLocaleTimeString([], {
				hour: '2-digit',
				minute: '2-digit',
				hour12: false, // true인 경우 오후 10:25와 같이 나타냄.
			});

			let voteInfo = this.$store.getters['root/getVoteInfo'];
			let resultList = this.sortVoteResult(voteResult);
			// let voteMessage = `투표 결과입니다. ${JSON.stringify(voteResult)}`;
			let voteMessage = `[${voteInfo.voteTopic}] 투표 결과 `;
			for (var i = 0; i < resultList.length; i++) {
				voteMessage += `${resultList[i].item}(${resultList[i].count}표) `;
			}

			let messageData = {
				content: voteMessage,
				sender: 'System',
				time: current,
			};

			// 투표 결과 토스트 알림
			swal(
				false,
				'center',
				5000,
				'info',
				'투표 결과...\n' + resultList[0].item + '당첨!',
				resultList[0].count + '표를 얻었습니다.',
			);
			// 자신의 채팅창에 당첨자 로그 출력
			this.$refs.chat.addMessage(JSON.stringify(messageData), false);
			// 투표 생성 모달 내용 초기화
			this.$refs.roomSidebar.$refs.voteCreateModal.state.voteInfo.voteTopic =
				'';
			this.$refs.roomSidebar.$refs.voteCreateModal.state.voteInfo.voteList = [];
			this.$refs.roomSidebar.$refs.voteCreateModal.state.itemNum = 2;
			// 기표 모달 내용 초기화
			this.$refs.roomSidebar.$refs.voteCreateModal.$refs.voteModal.state.checked =
				null;
		},

		// 투표 결과 내림차순 정렬
		sortVoteResult(voteResult) {
			let resultList = [];
			for (var key in voteResult) {
				resultList.push({
					item: key,
					count: voteResult[key],
				});
			}
			resultList.sort(function (a, b) {
				return b.count - a.count;
			});
			return resultList;
		},

		audioOnOff({ audio }) {
			console.log('audio');
			this.publisher.publishAudio(audio);
		},

		videoOnOff({ video }) {
			console.log('video');
			this.publisher.publishVideo(video);
		},

		// 룰렛 signal 보내기
		sendRoulletteMessage(rouletteTopic) {
			// 룰렛 데이터(참가자, 당첨자, 룰렛 제목) 저장
			let messageData = {
				participants:
					this.store.getters['root/getRouletteSignalData'].participants,
				winner: this.store.getters['root/getRouletteSignalData'].winner,
				rouletteTopic: rouletteTopic,
			};

			// 룰렛 데이터 signal 전송
			this.session
				.signal({
					data: JSON.stringify(messageData),
					to: [],
					type: 'roulette-result',
				})
				.then(() => {
					console.log('룰렛 메시지 전송 완료');
				})
				.catch(error => {
					console.log(error);
				});
		},

		// 룰렛 종료
		closeRoulette() {
			// 채팅창에 로그로 남길 데이터 정의
			let now = new Date();
			let current = now.toLocaleTimeString([], {
				hour: '2-digit',
				minute: '2-digit',
				hour12: false, // true인 경우 오후 10:25와 같이 나타냄.
			});
			let participants =
				this.store.getters['root/getRouletteSignalData'].participants;
			let winnerIdx = this.store.getters['root/getRouletteSignalData'].winner;

			let messageData = {
				content: `[${this.rouletteTopic}] ${participants[winnerIdx].value}님이 당첨되었습니다.`,
				sender: 'System',
				time: current,
			};
			// 룰렛 결과 토스트 알림
			swal(
				false,
				'center',
				5000,
				'info',
				'룰렛 결과...\n' + participants[winnerIdx].value + '님 당첨!',
				'축하합니다~!',
			);
			// 자신의 채팅창에 당첨자 로그 출력
			this.$refs.chat.addMessage(JSON.stringify(messageData), false);
			// 룰렛 제목 초기화
			this.$refs.roomSidebar.$refs.rouletteCreateModal.rouletteTopic = '';
			// 룰렛 컴포넌트 show 해제
			this.isRouletteOpen = false;
			// 사이드바 모달 열려있는 상태 해제
			this.$refs.roomSidebar.state.isAnyModalOpen = false;
		},

		// Kurento faceOverlayFilter 적용한 스티커 필터
		applyStickerFilter(filterInfo) {
			// bottombar 필터 해제 버튼 활성화
			this.$refs.bottombar.state.filter = true;
			this.publisher.stream.applyFilter('FaceOverlayFilter').then(filter => {
				var offsetX;
				var offsetY;
				var width;
				var height;

				if (filterInfo.category === 'hat') {
					offsetX = '-0.5F';
					offsetY = '-1.0F';
					width = '2.1F';
					height = '1.5F';
				} else if (filterInfo.category === 'mask') {
					offsetX = '-0.7F';
					offsetY = '-0.7F';
					width = '2.3';
					height = '2.3';
				} else if (filterInfo.category === 'eyes') {
					offsetX = '0.02F';
					offsetY = '0.1F';
					width = '1.0';
					height = '0.5';
				}

				filter.execMethod('setOverlayedImage', {
					uri: filterInfo.url,
					offsetXPercent: offsetX,
					offsetYPercent: offsetY,
					widthPercent: width,
					heightPercent: height,
				});
			});
			// bottombar 필터 해제 버튼 활성화
			this.$refs.bottombar.state.filter = true;
		},

		// Kurento GStreamerFilter 적용한 비주얼 필터
		applyVisualFilter(filterInfo) {
			this.publisher.stream
				.applyFilter('GStreamerFilter', {
					command: filterInfo.command,
				})
				.then(() => {
					console.log('Visual-filter applied');
					// bottombar 필터 해제 버튼 활성화
					this.$refs.bottombar.state.filter = true;
				})
				.catch(e => {
					console.log('err ::::: ', e);
				});
		},

		// Kurento GStreamerFilter 적용한 텍스트 필터
		applyTextFilter(filterInfo) {
			let command = `textoverlay text="${
				filterInfo.inputText ? filterInfo.inputText : 'PartyPeople~'
			}" valignment=${
				filterInfo.valignment ? filterInfo.valignment : 'top'
			} halignment=${
				filterInfo.halignment ? filterInfo.halignment : 'center'
			} font-desc="${filterInfo.font ? filterInfo.font : 'Cantarell'}
				${filterInfo.fontSize ? filterInfo.fontSize : 25}"`;
			this.publisher.stream
				.applyFilter('GStreamerFilter', {
					command: command,
				})
				.then(() => {
					console.log('Video flipped!!!!');
					// bottombar 필터 해제 버튼 활성화
					this.$refs.bottombar.state.filter = true;
				})
				.catch(e => {
					console.log('err ::::: ', e);
				});
		},

		// Kurento GStreamerFilter 적용한 음성 필터
		applyVoiceFilter(filterInfo) {
			let command = filterInfo;
			this.publisher.stream
				.applyFilter('GStreamerFilter', {
					command: command,
				})
				.then(() => {
					console.log('Voice transformed!!!!');
					// bottombar 필터 해제 버튼 활성화
					this.$refs.bottombar.state.filter = true;
				})
				.catch(e => {
					console.log('err ::::: ', e);
				});
		},

		// 필터 해제
		filterOff() {
			console.log('filter Off called');
			this.publisher.stream
				.removeFilter()
				.then(() => {
					console.log('-- Filter removed --');
				})
				.catch(error => {
					console.error(error);
				});
		},

		// bottombar filter 버튼 토글
		bottombarFilterBtn(btnState) {
			// bottombar 필터 해제 버튼 상태 변화
			this.$refs.bottombar.state.filter = btnState;
		},

		// 화이트보드 창 열기
		toggleWhiteboard() {
			console.log('toggle whiteboard');
			this.isWhiteboardOpen = !this.isWhiteboardOpen;
		},

		// 화이트보드 창 닫기
		// TODO: 화이트보드 창 닫을 때 화이트보드 초기화하기
		closeWhiteboard() {
			console.log('close whiteboard');
			this.isWhiteboardOpen = false;
		},

		// ctx 정보 보내기 step 2
		// 현재 좌표, 색깔, 굵기 정보를 받아 파티룸 내의 전체 사용자에게 전송
		sendWhiteboardSignal(lastX, lastY, x, y, color, width) {
			let data = {
				lastX: lastX,
				lastY: lastY,
				currentX: x,
				currentY: y,
				color: color,
				width: width,
			};

			this.session
				.signal({
					data: JSON.stringify(data),
					to: [],
					type: 'whiteboard',
				})
				.catch(error => {
					console.log(error);
				});
		},

		// 모든 참가자의 화이트보드 초기화 step 2
		sendResetSignal() {
			this.session
				.signal({
					to: [],
					type: 'reset-whiteboard',
				})
				.catch(error => {
					console.log(error);
				});
		},
	},

	mounted() {
		console.log('mounted');
		this.joinSession();
		this.store.commit('root/setRoomId', this.mySessionId);
	},
	beforeUnmount() {
		console.log('unmount');
		this.leaveSession();
	},
};
</script>
