// 룰렛 컴포넌트

<template>
	<div>
		<div class="roulette-outer rounded-xl bg-main-200">
			<!-- 룰렛 당첨자 핀 -->
			<div class="roulette-pin"></div>
			<!-- 룰렛 원판 -->
			<div class="roulette" v-bind:style="this.state.rouletteStyle">
				<!-- 룰렛 아이템(참가자 리스트) -->
				<div v-if="state.items.length < 4" class="item-wrapper text-2xl">
					<div
						class="item pt-20 text-tc-500"
						:key="item.value"
						v-for="(item, index) in this.state.items"
						v-bind:style="this.state.itemStyles[index]"
					>
						{{ item.value }}
					</div>
				</div>
				<div v-else class="item-wrapper text-xs">
					<div
						class="item pt-8 text-tc-500"
						:key="item.value"
						v-for="(item, index) in this.state.items"
						v-bind:style="this.state.itemStyles[index]"
					>
						{{ item.value }}
					</div>
				</div>
				<!-- 룰렛 아이템 경계선 -->
				<div class="line-wrapper">
					<div
						class="line"
						:key="item.value"
						v-for="(item, index) in this.state.items"
						v-bind:style="this.state.lineStyles[index]"
					></div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { reactive, computed } from 'vue';
import { useStore } from 'vuex';

export default {
	name: 'roulette',
	setup(props, { emit }) {
		const store = useStore();
		const state = reactive({
			// 참가자 리스트
			items: computed(
				() => store.getters['root/getRouletteSignalData'].participants,
			),
			// 당첨자 핀
			currentPin: 0,
			// 당첨자 핀 각도 동적 배치를 위한 룰렛 회전 횟수
			count: 0,
			history: [],
			// 참가자 각도 동적 배치
			itemStyles: computed(() => {
				let arr = [];
				state.items.forEach((el, idx) => {
					arr.push({
						transform: 'rotate(' + state.segment * idx + 'deg)',
					});
				});
				return arr;
			}),
			// 참가자 경계선 동적 배치
			lineStyles: computed(() => {
				let arr = [];
				if (state.items.length <= 1) {
					arr.push({
						display: 'none',
					});
				} else {
					state.items.forEach((el, idx) => {
						arr.push({
							transform:
								'rotate(' + (state.segment * idx + state.offset) + 'deg)',
						});
					});
				}
				return arr;
			}),
			// 인원수 별 각도
			segment: computed(() => 360 / state.items.length),
			// 경계선을 위한 오프셋
			offset: computed(() => state.segment / 2),
			// 당첨자 핀이 가리킬 각도
			angle: computed(
				() => -(state.currentPin * state.segment + state.count * 360 * 5),
			),
			// 룰렛 회전 각 동적 적용
			rouletteStyle: null,
			// 현재 핀이 가리키는 참가자
			currentItem: computed(() => state.items[state.currentPin]),
		});

		// 룰렛 애니메이션 실행
		const playRoulette = () => {
			// 실행 횟수 증가
			state.count++;
			// 렌더링 1초 뒤 애니메이션 실행(당첨자 적용, 당첨자 핀 각도 동적 적용)
			setTimeout(() => {
				state.currentPin = store.getters['root/getRouletteSignalData'].winner;
				state.rouletteStyle = { transform: 'rotate(' + state.angle + 'deg)' };
			}, 1000);
			// 룰렛 애니메이션만큼 시간 흐른 뒤 룰렛 종료
			setTimeout(() => {
				state.history.push(state.currentItem.value);
				setTimeout(() => {
					// partyroom-detail에 룰렛 숨기기 신호 보내기
					emit('closeRoulette');
				}, 0);
			}, 8000);
		};

		return { store, state, playRoulette };
	},
};
</script>

<style>
.roulette-outer {
	position: relative;
	width: 500px;
	height: 500px;
	margin-left: auto;
	margin-right: auto;
}

.roulette-outer > .roulette-pin {
	position: absolute;
	top: 2%;
	left: 50%;
	width: 0;
	height: 0;
	border-style: solid;
	border-width: 50px 10px 0 10px;
	border-color: #f65b65 transparent transparent transparent;
	margin-left: -10px;
	z-index: 1;
}

.roulette-outer > .roulette {
	position: absolute;
	top: 5%;
	bottom: 5%;
	left: 5%;
	right: 5%;
	border-radius: 50%;
	border: 10px solid white;
	transition: transform 5s ease-in-out;
}

.roulette-outer > .roulette > .item-wrapper > .item {
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	font-family: Pretendard;
	font-weight: bold;
	text-align: center;
	display: flex;
	justify-content: center;
}

.roulette-outer > .roulette > .line-wrapper > .line {
	position: absolute;
	top: 0;
	bottom: 50%;
	left: 50%;
	width: 6px;
	margin-left: -3px;
	background: white;
	transform-origin: bottom;
}
</style>
