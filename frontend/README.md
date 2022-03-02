# 공통 프로젝트 A106 빠뤼피플 Frontend

## 소개

WebRTC기반의 랜선파티 플랫폼 빠뤼피플의 프론트엔드 파트 코드입니다.
Vue CLI 3.0 버전 기반으로 작성되었습니다.


## 기술스택 및 라이브러리

주요 기술스택

- Vue (3.2.29) : 프론트엔드 메인 프레임워크
- Vuex (4.0.2) : Vue 상태관리 라이브러리
- TailwindCSS (2.2.17) : 유틸리티 클래스 기반 CSS프레임워크
- Axios (0.25.0) : HTTP통신 라이브러리
- Openvidu-browser (2.20.0) : WebRTC기반 실시간 통신 라이브러리
- Eslint (8.8.0) : 정적테스팅 라이브러리

UI 라이브러리

- animejs (3.2.1) : CSS애니메이션 라이브러리
- keen-slider (6.6.3) : carousel UI 라이브러리
- sweetalert2 (11.4.0) : Toast Message 라이브러리


## 디렉토리 구조

```
.
└── frontend
    ├── public
    │   ├── favicon.ico
    │   └── index.html /* VueApp 이 반영되는 템플릿 파일*/
    │
    ├── src
    │   ├── api /* 서버 및 외부 api관련 파일들을 포함하는 폴더*/
    │   │    └── axios.js
    │   │
    │   ├── assets
    │   │    ├── audios /* 서비스 내에서 사용되는 음성 에셋 폴더*/
    │   │    ├── images /* 서비스 내에서 사용되는 이미지 에셋 폴더*/
    │   │    ├── js /* 서비스 내에서 사용되는 UI프레임워크 전역 변수를 정의하는 폴더*/
    │   │    └── json-assets /* 서비스 내에서 사용되는 json형태의 파일을 저장하는 폴더*/
    │   │
    │   ├── router
    │   │    └── router.js /* 웹페이지 라우팅 관련 설정파일*/
    │   │
    │   ├── store /* 상태관리에 관련된 파일을 분리한 폴더*/
    │   │    ├── actions.js
    │   │    ├── auth.js /* 로그인, 회원가입 action과 localStorage에 저장할 state를 분리한 파일*/
    │   │    ├── getters.js
    │   │    ├── index.js /* 따로 분리한 action, mutation, state, getter파일을 root 모듈로 통합하는 파일*/
    │   │    ├── mutations.js
    │   │    ├── state.js
    │   │    └── store.js /* Vuex관련 모듈들을 통해 스토어를 생성하는 파일*/
    │   │
    │   ├── teleport /* 모달 컴포넌트들을 모아놓은 폴더*/
    │   │    ├── audio-customize-modal.js
    │   │    ├── base-modal.js /* 모든 모달이 공통으로 상속할 내용을 포함하는 베이스 모달*/
    │   │    ├── conference-create-modal.js
    │   │    ├── login-modal.js
    │   │    ├── out-modal.js
    │   │    ├── password-confirm.js
    │   │    ├── roulette-create-modal.js
    │   │    ├── signup-modal.js
    │   │    ├── theme-customize-modal.js
    │   │    ├── timer-create-modal.js
    │   │    ├── video-customize-modal.js
    │   │    ├── vote-create-modal.js
    │   │    └── vote-modal.js
    │   │
    │   ├── views /* Vue 컴포넌트들을 분리한 폴더*/
    │   │    ├── home
    │   │    │    ├── components /* 메인 페이지 내에서 사용될 컴포넌트*/
    │   │    │    │    ├── conference.vue
    │   │    │    │    └── main-header.vue
    │   │    │    └── home.vue /* 메인 페이지 컴포넌트*/
    │   │    │
    │   │    ├── landing
    │   │    │    └── landing-page.vue /* 랜딩 페이지 컴포넌트*/
    │   │    │
    │   │    └── partyroom
    │   │         ├── components /* 파티룸 내부에서 사용될 컴포넌트*/
    │   │         │    ├── ov-video.vue
    │   │         │    ├── room-bottombar.vue
    │   │         │    ├── room-chat.vue
    │   │         │    ├── room-sidebar.vue
    │   │         │    ├── roulette.vue
    │   │         │    ├── timer.vue
    │   │         │    ├── user-video.vue
    │   │         │    └── whiteboard.vue
    │   │         └── partyroom-detail.vue /* 파티룸 내부 페이지 컴포넌트*/
    │   │
    │   ├── App.vue /* index.html에 반영될 최상위 컴포넌트*/
    │   └── main.js /* Vue인스턴스를 생성하는 파일*/
    │
    ├── .eslintrc.js  /* 적용할 eslint규칙을 정의하는 설정파일*/
    ├── .gitignore
    ├── babel.config.js
    ├── package-lock.json
    ├── package.json
    ├── postcss.config.js
    ├── README.md
    ├── tailwind.config.js  /* tailwindCSS프레임워크의 커스텀 변수 및 플러그인을 정의하는 설정파일*/
    └── vue.config.js
```
.
