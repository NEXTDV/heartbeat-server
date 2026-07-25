INSERT INTO platforms (id, name, category, health_check_url, timeout_ms, degraded_threshold_ms, icon_url, is_active) VALUES
    ('a1b2c3d4-0001-0001-0001-000000000001', 'Claude', 'AI', 'https://status.anthropic.com/api/v2/summary.json', 5000, 2000, 'https://www.anthropic.com/favicon.ico', true),
    ('a1b2c3d4-0002-0002-0002-000000000002', 'ChatGPT', 'AI', 'https://status.openai.com/api/v2/summary.json', 5000, 2000, 'https://openai.com/favicon.ico', true),
    ('a1b2c3d4-0003-0003-0003-000000000003', 'Gemini', 'AI', 'https://status.cloud.google.com/incidents.json', 5000, 2000, 'https://www.gstatic.com/lamda/images/gemini_favicon_f069958c85030456e93de685481c559f160ea06.svg', true),
    ('a1b2c3d4-0004-0004-0004-000000000004', 'GitHub', 'DEVTOOL', 'https://www.githubstatus.com/api/v2/summary.json', 5000, 2000, 'https://github.com/favicon.ico', true),
    ('a1b2c3d4-0005-0005-0005-000000000005', 'AWS', 'CLOUD', 'https://status.aws.amazon.com/rss/all.rss', 5000, 2000, 'https://a0.awsstatic.com/libra-css/images/logos/aws_logo_smile_1200x630.png', true),
    ('a1b2c3d4-0006-0006-0006-000000000006', 'Azure', 'CLOUD', 'https://azure.status.microsoft/en-us/status/feed/', 5000, 2000, 'https://azure.microsoft.com/favicon.ico', true),
    ('a1b2c3d4-0007-0007-0007-000000000007', 'GCP', 'CLOUD', 'https://status.cloud.google.com/incidents.json', 5000, 2000, 'https://cloud.google.com/favicon.ico', true),
    ('a1b2c3d4-0008-0008-0008-000000000008', 'Slack', 'COMMUNICATION', 'https://status.slack.com/api/v2.0.0/current', 5000, 2000, 'https://slack.com/favicon.ico', true),
    ('a1b2c3d4-0009-0009-0009-000000000009', 'Discord', 'COMMUNICATION', 'https://discordstatus.com/api/v2/summary.json', 5000, 2000, 'https://discord.com/favicon.ico', true),
    ('a1b2c3d4-0010-0010-0010-000000000010', 'Notion', 'DEVTOOL', 'https://status.notion.so/api/v2/summary.json', 5000, 2000, 'https://www.notion.so/favicon.ico', true),
    ('a1b2c3d4-0011-0011-0011-000000000011', 'Cloudflare', 'CLOUD', 'https://www.cloudflarestatus.com/api/v2/summary.json', 5000, 2000, 'https://www.cloudflare.com/favicon.ico', true),
    ('a1b2c3d4-0012-0012-0012-000000000012', 'Vercel', 'CLOUD', 'https://www.vercel-status.com/api/v2/summary.json', 5000, 2000, 'https://vercel.com/favicon.ico', true),
    ('a1b2c3d4-0013-0013-0013-000000000013', 'Datadog', 'DEVTOOL', 'https://status.datadoghq.com/api/v2/summary.json', 5000, 2000, 'https://www.datadoghq.com/favicon.ico', true),
    ('a1b2c3d4-0014-0014-0014-000000000014', 'Jira', 'DEVTOOL', 'https://jira-software.status.atlassian.com/api/v2/summary.json', 5000, 2000, 'https://wac-cdn.atlassian.com/assets/img/favicons/atlassian/favicon.png', true)
ON CONFLICT (id) DO NOTHING;
