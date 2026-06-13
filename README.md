# dduru_android


## 패키지 구조

<details>
<summary>패키지구조</summary>

```txt
com.gildongmu.dduru
├── core
├── data
├── domain
└── presentation
```

### `core`

앱 전반에서 공통으로 사용하는 코드가 위치합니다.

- `common`: 상수, 확장 함수, 날짜 유틸, 소셜 로그인 콜백 등 공통 유틸 코드
- `designsystem`: 컬러, 타이포그래피, 테마, 공통 버튼 등 디자인 시스템 컴포넌트

### `data`

외부 API, 로컬 저장소, DTO, Repository 구현체 등 데이터 처리 코드가 위치합니다.

- `remote`: Retrofit service, remote data source, interceptor 등 네트워크 통신 관련 코드
- `local`: 토큰 등 로컬에 저장하거나 읽는 데이터 소스
- `repositoryimpl`: `domain` 계층의 Repository 인터페이스 구현체
- `mapper`: DTO와 domain model 간 변환 코드
- `di`: DataSource, Repository, Network 관련 Hilt 모듈
- `register`: 회원가입/온보딩 관련 API, 요청/응답 모델, Repository 구현체
- `auth`: 인증 토큰 관리 코드

### `domain`

앱의 핵심 비즈니스 규칙과 모델이 위치합니다.

- `entity`: 화면이나 데이터 계층에 종속되지 않는 도메인 모델
- `repository`: 데이터 접근을 위한 Repository 인터페이스
- `usecase`: 로그인 등 기능 단위 비즈니스 로직
- `register`, `phone`: 회원가입, 온보딩, 휴대폰 인증 관련 도메인 계약과 에러 매핑

### `presentation`

사용자에게 보여지는 화면과 UI 상태 관리 코드가 위치합니다.

- `feature`: 로그인, 홈, 회원가입, 매칭, 여행 그룹 등 기능별 화면
- `component`: 여러 화면에서 재사용하는 공통 UI 컴포넌트
- `navigation`: Root navigation, bottom navigation, route 정의

### 루트 파일

- `DduRuApplication.kt`: 앱 전역 Application 클래스

</details>
