![PartyPeople-Logo](./doc/Main-Image/PartyPeople-Logo.png)

<h1 align="center">🎉🎉 빠뤼 피플 🎉🎉</h1>

## 🎈 프로젝트 개요

> 딱딱한 회의는 끝, 파티에 최적화된 빠뤼피플
>
> 빠뤼 피플은 기존의 딱딱한 화상 회의가 아닌, 참여자 다 같이 즐기는 컨텐츠가 준비된 비대면 파티 플랫폼입니다.



## 📌 주요 기능

### 스티커

![Sticker](./doc/Main-Image/Sticker.gif)

### 비디오/음성 필터

![Video-Filter](./doc/Main-Image/Video-Filter.gif)

### 타이머

![Timer](./doc/Main-Image/Timer.gif)

### 투표

![Vote](./doc/Main-Image/Vote.gif)

### 룰렛

![Roulette](./doc/Main-Image/Roulette.gif)

### 화이트보드

![Whiteboard](./doc/Main-Image/Whiteboard.gif)



## 👩‍💻 팀원 구성

### Front-End

* [최현규](https://lab.ssafy.com/choifrance) - Front-End 리더
  * 메인 페이지 컴포넌트 개발
* [서민기](https://lab.ssafy.com/zcvn12345) - 발표 및 Jira 리더
  * 컨텐츠 컴포넌트 개발
* [이정환](https://lab.ssafy.com/ready2start) - UCC 및 Git 리더
  * WebRTC 연결

### Back-End

* [하윤주](https://lab.ssafy.com/hayj04) - Back-End 리더
  * 파티룸 수정/퇴장 API 개발
* [박건우](https://lab.ssafy.com/hggygw77) - AWS 서버 리더
  * 파티룸 조회/검색 API 개발
* [선민기](https://lab.ssafy.com/suns1502) - QA 리더
  * 파티룸 입장/삭제 API 개발
  
  

## 📆 개발 기간

2022.01.10 ~ 2022.02.18 (6주)



## 🌱 개발 환경

### Front-End

* Node.js 16.13.2
* Vue.js 3.2.29
* Vuex 4.0.2
* ESLint 6.7.2
* Axios 0.25.0
* Tailwind CSS 2.2.17
* PostCSS 7
* openVidu-browser 2.20.0

### Back-End

* OpenJDK 11.0.14.9-1
* spring boot 2.4.5
* Lombok 1.18.20
* QueryDSL 4.4.0
* Java-JWT 3.10.3
* Kurento-Client 6.16.0
* OpenVidu-Java-Client 2.20.0

### Server

* Docker 20.10.12
* Docker-compose 1.29.2
* MariaDB 10.2
* NGINX 1.21.6
* Kurento-media-server 6.16.0
* OpenVidu-Server  2.20.0



## 📝 기획/설계 문서

[빠뤼 피플 기획/설계 문서](./doc/README.md)



## 💻 서비스 배포 문서

[서비스 배포 문서](./exec/README.md)



## 🖋 변경 기록

### 1.0 (2022-02-18)

#### 변경 사항

* 산출물, 문서 등 각종 서류 추가

#### 구현 기능

* 랜딩 페이지
  * 메인 로고 모션
  * 기능 설명 페이지
  * Favicon
* 파티룸 외부 기능
  * 파티룸 태그 입력
  * 파티룸 태그 검색
  * 파티룸 검색 자동완성
* 파티룸 내부 기능
  * 비디오 필터
  * 음성 필터



### 0.2.1 (2022-02-09)

#### 변경 사항

* README 기획/설계 항목 파일 분리
* Swagger-API 문서 추가



### 0.2 (2022-02-08)

#### 변경 사항

* Front-End 파일 구조 변경

#### 구현 기능

* 회원 기능
  * 로그인
  * 회원 가입
* 파티룸 외부 기능
  * 파티룸 리스트 조회
  * 파티룸 입장
  * 파티룸 비밀번호 입장
  * 파티룸 생성
  * 파티룸 썸네일 업로드
* 파티룸 내부 기능 
  * 채팅
  * 화상 채팅
  * 마이크 On/Off
  * 카메라 On/Off
  * 타이머
  * 룰렛
  * 투표
  * 화이트보드
  * 화면 스티커 꾸미기



### 0.1 (2022-02-09)

#### 변경 사항

* 스켈레톤 코드 작성
