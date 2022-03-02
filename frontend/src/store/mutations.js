export function setPlatform(state, isDesktop) {
	state.isDesktopPlatform = isDesktop;
}

export function setPublisher(state, publisher) {
	state.publisher = publisher;
}

export function setRoomList(state, roomList) {
	console.log(roomList);
	state.roomList = roomList;
}

export function pushRoomList(state, roomList) {
	console.log(roomList);
	roomList.forEach(room => {
		state.roomList.push(room);
	});
}

export function setTimer(state, timerTime) {
	console.log(timerTime);
	state.timerTime = timerTime;
}

export function setRoomUserList(state, roomUserList) {
	console.log(roomUserList);
	state.roomUserList = roomUserList;
}

export function setRouletteSignalData(state, rouletteSignalData) {
	state.rouletteSignalData = rouletteSignalData;
}

export function setRoomId(state, roomId) {
	state.roomId = roomId;
}

export function setVote(state, voteInfo) {
	console.log(voteInfo);
	state.voteInfo = voteInfo;
}

export function setVoteResult(state, voteResult) {
	console.log(voteResult);
	state.voteResult = voteResult;
}

export function setPage(state, page) {
	console.log(page);
	state.page = page;
}

export function setSearchValue(state, searchValue) {
	console.log(searchValue);
	state.searchValue = searchValue;
}

export function setSearchOption(state, searchOption) {
	console.log(searchOption);
	state.searchOption = searchOption;
}
