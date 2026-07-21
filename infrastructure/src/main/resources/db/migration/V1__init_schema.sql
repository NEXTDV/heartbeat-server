CREATE TYPE channel_type AS ENUM ('SLACK', 'DISCORD', 'APP', 'EMAIL');
CREATE TYPE service_status AS ENUM ('OPERATIONAL', 'DEGRADED', 'PARTIAL_OUTAGE', 'MAJOR_OUTAGE', 'UNKNOWN');
CREATE TYPE service_category AS ENUM ('CLOUD', 'AI', 'COMMUNICATION', 'DEVTOOL', 'OTHER');
CREATE TYPE delivery_status AS ENUM ('SUCCESS', 'FAILED');

CREATE TABLE accounts (
    id UUID NOT NULL,
    email VARCHAR(255) NOT NULL,
    CONSTRAINT pk_accounts PRIMARY KEY (id),
    CONSTRAINT uq_accounts_email UNIQUE (email)
);

CREATE TABLE platforms (
    id UUID NOT NULL,
    name VARCHAR(100) NOT NULL,
    category service_category NOT NULL,
    health_check_url VARCHAR(500) NOT NULL,
    timeout_ms INTEGER NOT NULL DEFAULT 3000,
    degraded_threshold_ms INTEGER NOT NULL DEFAULT 1000,
    icon_url VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT pk_platforms PRIMARY KEY (id)
);

CREATE TABLE channels (
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    type channel_type NOT NULL,
    name VARCHAR(100) NOT NULL,
    config JSONB NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ,
    CONSTRAINT pk_channels PRIMARY KEY (id),
    CONSTRAINT fk_channels_account FOREIGN KEY (user_id) REFERENCES accounts (id)
);

CREATE TABLE health_check_logs (
    id UUID NOT NULL,
    platform_id UUID NOT NULL,
    status service_status NOT NULL,
    http_status_code INTEGER,
    response_ms INTEGER,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT pk_health_check_logs PRIMARY KEY (id),
    CONSTRAINT fk_health_check_logs_platform FOREIGN KEY (platform_id) REFERENCES platforms (id)
);

CREATE TABLE channel_platforms (
    id UUID NOT NULL,
    channel_id UUID NOT NULL,
    platform_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    deleted_at TIMESTAMPTZ,
    CONSTRAINT pk_channel_platforms PRIMARY KEY (id),
    CONSTRAINT fk_channel_platforms_channel FOREIGN KEY (channel_id) REFERENCES channels (id),
    CONSTRAINT fk_channel_platforms_platform FOREIGN KEY (platform_id) REFERENCES platforms (id)
);

CREATE TABLE delivery_logs (
    id UUID NOT NULL,
    channel_platform_id UUID NOT NULL,
    status delivery_status NOT NULL,
    delivered_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT pk_delivery_logs PRIMARY KEY (id),
    CONSTRAINT fk_delivery_logs_channel_platform FOREIGN KEY (channel_platform_id) REFERENCES channel_platforms (id)
);
