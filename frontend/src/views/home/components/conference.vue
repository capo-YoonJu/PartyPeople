// 파티 룸 카드 컴포넌트

<template>
	<div
		class="max-w-sm rounded-xl overflow-hidden shadow-lg conference-card h-96 w-80"
	>
		<!-- 파티룸 썸네일 -->
		<div class="w-80 h-48">
			<img
				:src="room.thumbnailUrl"
				class="object-cover w-80 h-48"
				alt="PartyRoom Thumbnail"
			/>
		</div>
		<!-- 파티룸 정보 -->
		<div class="px-5 py-4 flex flex-col h-28">
			<div class="flex flex-row justify-between items-center">
				<div
					class="border-l-2 border-sub-200 font-bold text-xl pl-1 room-title"
				>
					{{ room.title }}
				</div>
				<div class="inline px-1">
					<span class="rounded-full main-bg-color ml-3 px-3 py-1 text-white h-8"
						>{{ curConnCnt }} / {{ room.capacity }}</span
					>
				</div>
			</div>
			<p class="text-gray-700 mx-1 mt-3 text-sm card-description text-left">
				{{ room.description }}
			</p>
		</div>
		<!-- 파티룸 태그 -->
		<div
            class="h-20 px-6 py-2 flex flex-row items-center flex-wrap bg-gradient-to-r from-main-200 to-sub-200"
        >
			<span
				v-for="tags in room.roomTags"
				:key="tags.id"
				class="inline-block partyroom-tag rounded-full px-3 py-1 text-xs shadow-md font-semibold text-white mr-2 mb-1"
			>
				#{{ tags.tag.tagName }}
			</span>
        </div>
	</div>
</template>

<style>
.main-bg-color {
  background-color: #6667ab;
}

.partyroom-tag {
  background-color: #ff95b5;
}

/* title => 1줄 넘어가면 말줄임 표시 */
.room-title {
	max-width: 200px;
	overflow: hidden;
	word-break: break-all;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 1;
}

/* description => 2줄 넘어가면 말줄임 표시 */
.card-description {
	overflow: hidden;
	display: -webkit-box;
	-webkit-box-orient: vertical;
	-webkit-line-clamp: 2;
}

/* 테블릿, 모바일은 2줄 넘어가면 말줄임 표시 */
/* @media (max-width: 1269px) {
	.card-description {
		-webkit-line-clamp: 2;
	}
} */
</style>

<script>
import { ref } from 'vue'

export default {
  name: "Home",

  props: {
    room: {
      type: Object,
    },
    thumbnailUrl: {
      type: String,
      default:
        "https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg",
    },
    title: {
      type: String,
      default: "파티룸 이름",
    },
    desc: {
      type: String,
      default:
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
    },
    currentUserCount: {
      type: Number,
      default: 1,
    },
    maxUserCount: {
      type: Number,
      default: 8,
    },
  },

  setup(props) {
    let curConnCnt
    if (props.room.sessions === undefined) curConnCnt = ref(0)
    else curConnCnt = ref(props.room.sessions.length)

    return {
      curConnCnt
    }
  },
};
</script>
