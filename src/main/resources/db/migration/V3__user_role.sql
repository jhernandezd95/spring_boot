CREATE TABLE IF NOT EXISTS user_roles
(
    user_id INT UNSIGNED NOT NULL,
    role_id INT UNSIGNED NOT NULL,
    CONSTRAINT user_roles_roles FOREIGN KEY (role_id) REFERENCES role (id),
    CONSTRAINT user_roles_user FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT user_roles_unique UNIQUE (user_id, role_id)
) ENGINE = InnoDB
  DEFAULT CHARSET = UTF8