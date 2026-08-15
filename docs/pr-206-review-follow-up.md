# PR 206 Review Follow-up

PR 206 review comments 대응 내역입니다.

## Applied Changes

### Token refresh

- `AuthInterceptor`에서 만료된 access token으로 refresh를 요청할 때 해당 token을 `staleAccessToken`으로 함께 전달하도록 수정했습니다.
- `TokenAuthenticator`에 `Mutex`를 추가해 동시에 여러 요청이 refresh를 시도해도 refresh 요청이 직렬화되도록 했습니다.
- Mutex lock 획득 후 저장된 access token을 다시 읽고, 이미 다른 요청이 갱신한 token이 있으면 refresh 요청을 다시 보내지 않고 그 token을 재사용합니다.
- refresh token은 `null`, empty, blank 값을 모두 거부하도록 맞췄습니다.
- blocking OkHttp `execute()` 호출은 `withContext(Dispatchers.IO)` 안에서 실행되도록 변경했습니다.
- refresh 응답 성공 판정은 기존 `response.isSuccessful` 방식을 유지합니다.

### Home repository exception handling

- `HomeRepositoryImpl`에서 `ExceptionResponse`가 일반 `Exception` catch로 감싸지지 않도록 전용 catch를 먼저 추가했습니다.
- 홈 no-data 케이스는 404만 `ExceptionResponse`로 보존하도록 정리했습니다.
- 401은 더 이상 "빈 홈 데이터"처럼 처리하지 않습니다. 인증/세션 실패는 서버 오류 경로로 전달됩니다.
- `HOME_SUCCESS_STATUS = 0` 처리는 제거하고, 홈 API body status 성공은 200만 성공으로 판단합니다.

### Profile side effect

- `ProfileViewModel`의 profile completion navigation event 전달을 `MutableSharedFlow`에서 buffered `Channel` + `receiveAsFlow()`로 변경했습니다.
- `NavigateToMain` 이벤트가 collector 재생성 타이밍에 유실될 가능성을 줄였습니다.

### Root navigation

- `Register -> RegisterSurvey -> RegisterProfile` 온보딩 흐름에서 단계 이동 시 `rootBackStack.clear()`를 제거했습니다.
- 설문 화면에서 뒤로가면 회원가입 화면으로, 프로필 화면에서 뒤로가면 설문 화면으로 돌아갈 수 있도록 이전 route를 back stack에 남깁니다.
- `MatchingEditPostRoute`에 `postId`를 전달하도록 연결했습니다.
- `MatchingEditPostViewModel`과 `MatchingEditPostUiState`에도 `postId`를 보관할 수 있는 입력을 추가했습니다. 실제 상세 조회/수정 API가 붙으면 이 값을 기준으로 로딩하면 됩니다.

### UI polish

- `HomeSubPageTopBar`의 back icon에 `"뒤로가기"` content description을 추가했습니다.
- back icon 클릭 영역을 48dp로 확장했습니다.
- `MainScreenPreview`를 `DduruTheme`으로 감쌌습니다.
- JWT payload decoding은 `android.util.Base64`에서 `java.util.Base64`로 변경했습니다.

## Handoff

### Chat room ID type

Chat 영역은 이번 커밋에서 수정하지 않고 담당자에게 공유할 내용으로 남깁니다.

공유 문구:

> CodeRabbit이 `ChatMainScreen`의 `onChatRoomClick(id.toLong())`를 지적했습니다. 현재 UI model의 `id`가 `String`이라 화면 클릭 시점에 `Long`으로 변환하는데, 잘못된 문자열이 들어오면 클릭 순간 crash가 날 수 있습니다. 서버/domain의 `chatRoomId`가 이미 `Long`이면 `ChatRoomUiModel.id`도 `Long`으로 두고, mapper에서 `chatRoomId`를 그대로 전달한 뒤 `ChatMainScreen`은 `viewModel::onChatRoomClick`으로 ID를 전달만 하는 쪽이 안전합니다. Chat 쪽 소유자가 UI model 타입 변경 영향 범위를 확인해주면 좋겠습니다.

## Not Applied

### Matching detail repository integration

`MatchingDetailViewModel`의 상세 조회와 좋아요 변경을 실제 repository에 연결하라는 코멘트는 아직 적용하지 않았습니다.

현재 코드베이스에는 매칭 상세 조회/좋아요 repository 또는 API가 없습니다. PR 설명에도 `MatchingDetailViewModel`은 임시로 `postId` 기반 `MatchingDetailUiState`를 생성하고, 추후 상세 API 연동 시 `loadMatchingDetail` 내부만 교체한다고 되어 있습니다.

후속 API/repository가 추가되면 다음 작업이 필요합니다.

- 상세 조회 repository 주입
- `loadMatchingDetail(postId)`에서 repository 호출
- 성공 결과를 `MatchingDetailUiState`로 mapping
- 실패 시 `UiState.Failure` 노출
- 좋아요 변경도 repository 결과 기준으로 상태 갱신

## Validation

- `./gradlew assembleDebug` 성공
