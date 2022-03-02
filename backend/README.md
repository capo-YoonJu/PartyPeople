# 웹 기술 Back-end

## 라이브러리(플러그인)

| Plugin               | Version |
| -------------------- | ------- |
| Java(OpenJDK)        | 11.0    |
| spring boot          | 2.4.5   |
| Lombok               | 1.18.20 |
| QueryDSL             | 4.4.0   |
| Java-JWT             | 3.10.3  |
| Kurento-Client       | 6.16.0  |
| OpenVidu-Java-Client | 2.20.0  |
| Gradle               | 6.7     |


## 디렉토리 구조

```
backend
├── docker /* docker 관련 설정 파일 */
│	├── docker-compose.yml /* springboot, nginx, certbot docker-compose 파일 */
│	├── Dockerfile /* springboot 컨테이너 관련 dockerfile */
│	├── init-letsencrypt.sh /* SSL 발급 자동화 파일 */
│	└── partypeople-web-project-1.0.jar /* 빌드된 프로젝트 파일 */
├── gradle
│	└── wrapper
│		├── gradle-wrapper.jar
│		└── gradle-wrapper.properties
├── src
│    └── main
│        ├── generated
│        ├── java
│        │   └── com
│        │       └── ssafy
│        │          ├── GroupCallApplication.java
│        │          ├── api  /* REST API 요청관련 컨트롤러, 서비스, 요청/응답 모델 정의 */
│        │          │   ├── controller
│        │          │   │   ├── AuthController.java
│        │          │   │   ├──ListController.java
│        │          │   │   ├── RoomController.java
│        │          │   │   └── UserController.java
│        │          │   ├── request
│        │          │   │   ├── RoomCreatePostReq.java
│        │          │   │   ├── RoomEntryPostReq.java
│        │          │   │   ├── RoomHostUpdatePostReq.java
│        │          │   │   ├── UserLoginPostReq.java
│        │          │   │   └── UserSignUpPostReq.java
│        │          │   ├── response
│        │          │   │   ├── CreateRoomRes.java
│        │          │   │   ├── RoomEntryLinkRes.java
│        │          │   │   ├── RoomListRes.java
│        │          │   │   ├── RoomUserListRes.java
│        │          │   │   ├── RoomUserRes.java
│        │          │   │   ├── SuggestedKeywardRes.java
│        │          │   │   ├── UserLoginPostRes.java
│        │          │   │   └── UserRes.java
│        │          │   └── service
│        │          │       ├── FileUploadService.java
│        │          │       ├── FileUploadServiceImpl.java
│        │          │       ├── ListService.java
│        │          │       ├── ListServiceImpl.java
│        │          │       ├── OpenviduService.java
│        │          │       ├── OpenviduServiceImpl.java
│        │          │       ├── RoomService.java
│        │          │       ├── RoomServiceInpl.java
│        │          │       ├── UserService.java
│        │          │       └── UserServiceImpl.java
│        │          ├── common /* 공용 유틸, 응답 모델, 인증, 예외처리 관련 정의*/
│        │          │   ├── auth
│        │          │   │   ├── JwtAuthenticationFilter.java
│        │          │   │   ├── SsafyUserDetailService.java
│        │          │   │   └── SsafyUserDetails.java
│        │          │   ├── exception
│        │          │   │   └── handler
│        │          │   │       └── NotFoundHandler.java
│        │          │   ├── model
│        │          │   │   └── response
│        │          │   │       └── BaseResponseBody.java
│        │          │   └── util
│        │          │       ├── JwtTokenUtil.java
│        │          │       └── ResponseBodyWriteUtil.java
│        │          ├── config /* WebMvc 및 JPA, Security, Swagger 등의 추가 플러그인 설정 정의*/
│        │          │   ├── JpaConfig.java
│        │          │   ├── SecurityConfig.java
│        │          │   ├── SwaggerConfig.java
│        │          │   └── WebMvcConfig.java
│        │          └── db /* 디비에 저장될 모델 정의 및 쿼리 구현 */
│        │              ├── entity
│        │              │   ├── BaseEntity.java
│        │              │   ├── Room.java
│        │              │   ├── RoomTag.java
│        │              │   ├── Session.java
│        │              │   ├── SuggstionRoom.java
│        │              │   ├── SuggestionTag.java
│        │              │   ├── Tag.java
│        │              │   └── User.java
│        │              └── repository
│        │                  ├── ListRepository.java
│        │                  ├── ListRepositorySupport.java
│        │                  ├── RoomRepository.java
│        │                  ├── RoomRepositorySupport.java
│        │                  ├── RoomTagRepository.java
│        │                  ├── RoomTagRepositorySupport.java
│        │                  ├── SessionRepository.java
│        │                  ├── SessionRepositorySupport.java
│        │                  ├── SuggestionRoomRepository.java
│        │                  ├── SuggestionRoomRepositorySupport.java
│        │                  ├── SuggestionTagRepository.java
│        │                  ├── SuggestionTagRepositorySupport.java
│        │                  ├── TagRepository.java
│        │                  ├── UserRepository.java
│        │                  └── UserRepositorySupport.java
│        └── resources
│            ├── application.properties /* 웹 리소스(서버 host/port, 디비 host/port/계정/패스워드) 관련 설정 정의 */
│            └── sql_init.sql /* 인메모리 DB에 있는 값이 mysql 종료로 인해 초기화될 때 사용하는 sql */
├── .gitignore
├── build.gradle /* gradle 설정 파일 */
├── gradlew
├── gradlew.bat
├── README.md  /* backend 라이브러리, 디렉토리 구조 설명 파일 */
└── settings.gradle /* 프로젝트 이름 설정 파일 */
```
.
