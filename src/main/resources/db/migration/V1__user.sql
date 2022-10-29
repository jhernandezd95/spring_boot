CREATE TABLE IF NOT EXISTS `user`
(
    `id`
    int
    NOT
    NULL
    AUTO_INCREMENT
    PRIMARY
    KEY,
    `email`
    varchar
(
    20
) NOT NULL,
    `password` varchar
(
    50
),
    `birthday` timestamp NOT NULL,
    `last_login` timestamp,
    `is_enable` boolean NOT NULL DEFAULT 0,
    `is_account_non_locked` boolean NOT NULL DEFAULT 0,
    `is_account_non_expired` boolean NOT NULL DEFAULT 0,
    `created_at` timestamp,
    `updated_at` timestamp,
    CONSTRAINT unique_email UNIQUE
(
    email
)
    ) ENGINE=InnoDB DEFAULT CHARSET=UTF8