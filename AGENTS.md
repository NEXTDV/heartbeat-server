## Git 규칙

### 공통

- **`main`에 직접 커밋/푸시하지 않는다.** 모든 변경은 브랜치 → PR → 머지를 거친다. (저장소 초기 부트스트랩 커밋만 예외)
- **커밋/PR에 AI 생성 표시 절대 금지** ("Generated with Claude", "Co-Authored-By: Claude" 등).
- 설명 문장은 한글, 명확하고 간결하게 작성한다.

### 브랜치

- 형식: `<type>/<짧은-설명>` (kebab-case, 영문)
- 예: `feat/user-login`, `fix/save-race`, `docs/plan`

### 커밋 메시지 (Conventional Commits)

- 형식: `<type>: <한글 설명>`
- 제목은 50자 이내, 마침표 없이. 상세 설명은 본문에 "왜"를 적는다(무엇을 했는지는 diff가 말한다).

**type**

| type | 용도 |
|---|---|
| `feat` | 사용자 기능 추가 |
| `fix` | 버그 수정 |
| `docs` | 문서 (README, 설계 문서 등) |
| `test` | 테스트 추가/수정 |
| `refactor` | 동작 변화 없는 구조 개선 |
| `style` | 포맷/린트 |
| `chore` | 빌드·설정·의존성 잡무 |
| `ci` | CI 설정 |

**예시**

```
feat: 매직 링크 이메일 발송 로직 추가
fix: 타임아웃 시 UNKNOWN 대신 MAJOR_OUTAGE로 판단하도록 수정
test: 플랫폼 등록 및 조회 테스트 추가
docs: Git 규칙 및 커밋 컨벤션 추가
```

### PR 규칙

- 제목: 커밋과 동일한 Conventional 형식.
- 크기: **300줄 이내 권장**. 넘으면 쪼갠다.
- 본문: `.github/PULL_REQUEST_TEMPLATE.md`를 단일 원본으로 삼는다.
