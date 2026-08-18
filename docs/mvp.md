# MVP 범위 정의

> 대회 제출 마감: 2026-08-27
> 이 파일에 없는 기능은 MVP 범위 밖 → 이슈 등록만 하고 구현 금지

---

## 확정 스키마

> 코드는 이 스키마를 기준으로 구현한다. 스키마 변경 없이 Java 코드만 수정하는 것을 원칙으로 한다.

```sql
CREATE TABLE accounts (
    id          UUID            NOT NULL,
    email       VARCHAR(255)    NOT NULL, -- UQ
    PRIMARY KEY (id)
);

CREATE TABLE channels (
    id          UUID            NOT NULL,
    user_id     UUID            NOT NULL,
    type        channel_type    NOT NULL, -- ENUM: SLACK, DISCORD, APP, EMAIL
    name        VARCHAR(100)    NOT NULL,
    config      JSONB           NOT NULL,
    created_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    updated_at  TIMESTAMPTZ     NOT NULL DEFAULT now(),
    deleted_at  TIMESTAMPTZ     NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES accounts (id)
);

CREATE TABLE platforms (
    id                      UUID                NOT NULL,
    name                    VARCHAR(100)        NOT NULL,
    category                service_category    NOT NULL, -- ENUM: CLOUD, AI, COMMUNICATION, DEVTOOL, OTHER
    health_check_url        VARCHAR(500)        NOT NULL,
    timeout_ms              INTEGER             NOT NULL DEFAULT 3000,
    degraded_threshold_ms   INTEGER             NOT NULL DEFAULT 1000,
    icon_url                VARCHAR(500)        NULL,
    is_active               BOOLEAN             NOT NULL DEFAULT true,
    PRIMARY KEY (id)
);

CREATE TABLE health_check_logs (
    id               UUID            NOT NULL,
    platform_id      UUID            NOT NULL,
    status           service_status  NOT NULL, -- ENUM: OPERATIONAL, DEGRADED, PARTIAL_OUTAGE, MAJOR_OUTAGE, UNKNOWN
    http_status_code INTEGER         NULL,
    response_ms      INTEGER         NULL,
    created_at       TIMESTAMPTZ     NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY (platform_id) REFERENCES platforms (id)
);

CREATE TABLE channel_platforms (
    id          UUID        NOT NULL,
    channel_id  UUID        NOT NULL,
    platform_id UUID        NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at  TIMESTAMPTZ NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (channel_id)  REFERENCES channels (id),
    FOREIGN KEY (platform_id) REFERENCES platforms (id)
);

CREATE TABLE delivery_logs (
    id                  UUID            NOT NULL,
    channel_platform_id UUID            NOT NULL,
    status              delivery_status NOT NULL DEFAULT 'PENDING', -- ENUM: PENDING, SUCCESS, FAILED
    delivered_at        TIMESTAMPTZ     NOT NULL DEFAULT now(),
    PRIMARY KEY (id),
    FOREIGN KEY (channel_platform_id) REFERENCES channel_platforms (id)
);
```

---

## 프로젝트 개요

**한 줄 요약**: 개발자 및 엔지니어가 사용하는 서비스의 현재 상태(헬스체크)를 간단하게 확인할 수 있는 서비스

**컨셉**: 경량 Datadog — 복잡한 설정 없이 빠르게 서비스 상태 파악

**대상 사용자**: 개발자 + 인프라 엔지니어

**제공 형태 우선순위**: Alert → Mac App → Mail → (Dashboard는 후순위)

---

## 모니터링 대상 플랫폼

| 플랫폼 | 비고 |
|---|---|
| Claude | |
| GPT | |
| GitHub | |
| Google / Gemini | 개별 장애 케이스 존재, 분리 고려 |
| AWS | |
| Azure | |
| Cloudflare | |
| Discord | |
| Slack | |
| Notion | |
| Vercel | |
| Datadog | |
| Jira / Atlassian | |

---

## 유저 권한

| 역할 | 권한 |
|---|---|
| Guest | 폭넓은 권한 (슈퍼 게스트) — 대부분의 기능 사용 가능 |
| User (로그인) | Guest + 소폭 추가 권한 (알림 채널 등록, 관심 서비스 저장 등) |

---

## ✅ 1차 MVP (서버 구현 범위)

### 핵심 기능
| 이슈 | 기능 | 상태 |
|---|---|---|
| #27 | 헬스체크 스케줄러 (주기적 폴링 + DB 기록) | 🔄 PR 머지 대기 |
| #26 | 채널 API (Slack/Discord webhook 등록/삭제) | 🔄 PR 머지 대기 |
| #28 | 플랫폼 상태 조회 API | ✅ 완료 |

### Alert (Slack & Discord)
- 서비스 장애 감지 시 등록된 채널로 알림 발송
- Slack, Discord webhook 기반

### 인증/인가
- Guest: 별도 로그인 없이 조회 가능
- User: OAuth 기반 로그인, 알림 채널 등록 등 추가 기능

### Mail (가능하면)
- 장애 발생 시 이메일 알림
- 도메인 인증 필요 → 시간 되면 붙이기

### 대회 제출 필수
| 이슈 | 내용 | 상태 |
|---|---|---|
| #37 | README + 데모 시나리오 작성 | ❌ 미작성 |
| #30 | Lightsail 배포 환경 구성 | ❌ 미완 |

### 완료된 것
| 이슈 | 내용 |
|---|---|
| #32 | UUID v7 유틸리티 |
| #34 | 전역 예외처리 |
| #35 | GitHub Actions CI |
| #31 | Flyway 마이그레이션 |

---

## ❌ MVP 범위 밖 (이슈 등록만, 구현 금지)

| 내용 | 이유 |
|---|---|
| #38 expected_status_code 컬럼 추가 | 확정 스키마에 없는 컬럼 |
| #33 service_current_status UPSERT | 확정 스키마에 없는 테이블, 코드 제거됨 |
| 웹 대시보드 | 마지막 순위, 시간 부족 |
| IntelliJ 플러그인 | 유스케이스 불명확 |
| K8s | 배포 복잡도 증가, 두 명 모두 미숙 |
| 정기점검 감지 | 플랫폼별 종속성 심함, 방법 제각각 |
| Linux 지원 | GUI 앱 사용자 비율 낮음 |
| 서비스 안정성 비교 기능 | DB 누적 선행 필요, 후속 기능 |
| #29 | API 문서화 도구 결정 |
| #39 | Terraform 인프라 코드화 |
| #41~#48 | 보안 강화 (k8s, SSL, 컨테이너 등) |
| #53 | UuidV7 테스트 개선 |
| #24 | HealthCheckLogRepository 통합 테스트 |
| #25 | ChannelRepository 통합 테스트 |

---

## 🛠 운영 수동 작업

### 플랫폼 초기 데이터 삽입

배포 후 DB에 직접 실행:

```sql
INSERT INTO platforms (id, name, category, health_check_url, timeout_ms, degraded_threshold_ms, icon_url, is_active) VALUES
  (gen_random_uuid(), 'Claude',     'AI',            'https://status.anthropic.com/api/v2/summary.json',             5000, 2000, 'https://www.anthropic.com/favicon.ico', true),
  (gen_random_uuid(), 'ChatGPT',    'AI',            'https://status.openai.com/api/v2/summary.json',                5000, 2000, 'https://openai.com/favicon.ico', true),
  (gen_random_uuid(), 'Gemini',     'AI',            'https://status.cloud.google.com/incidents.json',               5000, 2000, 'https://www.gstatic.com/lamda/images/gemini_favicon_f069958c85030456e93de685481c559f160ea06.svg', true),
  (gen_random_uuid(), 'GitHub',     'DEVTOOL',       'https://www.githubstatus.com/api/v2/summary.json',             5000, 2000, 'https://github.com/favicon.ico', true),
  (gen_random_uuid(), 'AWS',        'CLOUD',         'https://status.aws.amazon.com/rss/all.rss',                    5000, 2000, 'https://a0.awsstatic.com/libra-css/images/logos/aws_logo_smile_1200x630.png', true),
  (gen_random_uuid(), 'Azure',      'CLOUD',         'https://azure.status.microsoft/en-us/status/feed/',            5000, 2000, 'https://azure.microsoft.com/favicon.ico', true),
  (gen_random_uuid(), 'GCP',        'CLOUD',         'https://status.cloud.google.com/incidents.json',               5000, 2000, 'https://cloud.google.com/favicon.ico', true),
  (gen_random_uuid(), 'Slack',      'COMMUNICATION', 'https://status.slack.com/api/v2.0.0/current',                  5000, 2000, 'https://slack.com/favicon.ico', true),
  (gen_random_uuid(), 'Discord',    'COMMUNICATION', 'https://discordstatus.com/api/v2/summary.json',                5000, 2000, 'https://discord.com/favicon.ico', true),
  (gen_random_uuid(), 'Notion',     'DEVTOOL',       'https://status.notion.so/api/v2/summary.json',                 5000, 2000, 'https://www.notion.so/favicon.ico', true),
  (gen_random_uuid(), 'Cloudflare', 'CLOUD',         'https://www.cloudflarestatus.com/api/v2/summary.json',         5000, 2000, 'https://www.cloudflare.com/favicon.ico', true),
  (gen_random_uuid(), 'Vercel',     'CLOUD',         'https://www.vercel-status.com/api/v2/summary.json',            5000, 2000, 'https://vercel.com/favicon.ico', true),
  (gen_random_uuid(), 'Datadog',    'DEVTOOL',       'https://status.datadoghq.com/api/v2/summary.json',             5000, 2000, 'https://www.datadoghq.com/favicon.ico', true),
  (gen_random_uuid(), 'Jira',       'DEVTOOL',       'https://jira-software.status.atlassian.com/api/v2/summary.json', 5000, 2000, 'https://wac-cdn.atlassian.com/assets/img/favicons/atlassian/favicon.png', true);
```

---

## 🚫 도입하지 않는 기능

**Incidents (장애 이벤트 별도 추적)**
별도 incidents 테이블을 두고 장애 시작/종료를 추적하는 방식은 이 MVP에서 채택하지 않는다.
장애 여부는 `health_check_logs`의 최신 status로 판단하는 것으로 충분하다고 결정했으며, 재설계 없이는 도입하지 않는다.
