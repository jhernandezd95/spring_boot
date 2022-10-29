CREATE TABLE IF NOT EXISTS `user`
(
    `id`            int          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`          varchar(20)  NOT NULL,
    `description`   varchar(50),
    `created_at`    timestamp,
    `updated_at`    timestamp,
    `created_by_id` INT UNSIGNED NOT NULL,
    CONSTRAINT `role_user` FOREIGN KEY (created_by_id) REFERENCES user (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8