// ROOT STATE 변수 정의 및 기본값 대입

/**
 * 플랫폼 관련 정보로 데스크탑인지, 모바일인지 판별 - 하이브리드 앱 대비
 */
function getIsDesktop() {
	var userAgent = window.navigator.userAgent,
		platform = window.navigator.platform,
		macosPlatforms = ['Macintosh', 'MacIntel', 'MacPPC', 'Mac68K'],
		windowsPlatforms = ['Win32', 'Win64', 'Windows', 'WinCE'],
		iosPlatforms = ['iPhone', 'iPad', 'iPod'],
		os = null;

	if (macosPlatforms.indexOf(platform) !== -1) {
		//Desktop - Mac
		return true;
	} else if (iosPlatforms.indexOf(platform) !== -1) {
		// IOS
		return false;
	} else if (windowsPlatforms.indexOf(platform) !== -1) {
		//Desktop - window
		return true;
	} else if (/Android/.test(userAgent)) {
		//Android
		return false;
	} else if (!os && /Linux/.test(platform)) {
		//Linux
		return true;
	}

	return os;
}

const IsDesktop = getIsDesktop();

export default {
	isDesktopPlatform: IsDesktop,
	publisher: null,
	roomList: [
		{ id: 1, title: '파티룸 1번' },
		{ id: 2, title: '파티룸 2번' },
		{ id: 3, title: '파티룸 3번' },
	],
	timerTime: {
		min: 0,
		sec: 0,
		count: false,
	},
	roomUserList: [],
	voteInfo: {
		voteTopic: '',
		voteList: {},
	},
	voteResult: {},
	rouletteSignalData: {
		participants: [],
		winner: null,
	},
	roomId: null,
	searchValue: '',
	searchOption: 'title',
	page: 1,
};
