CREATE TABLE users (
    id                      bigserial PRIMARY KEY,
    login                   VARCHAR(40) UNIQUE,
    name                    VARCHAR(40) NOT NULL,
    password                VARCHAR(80) NOT NULL
);

CREATE TABLE roles (
    id                      bigserial PRIMARY KEY,
    name                    VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users_roles (
    user_id               bigint REFERENCES users (id),
    role_id               bigint REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles (name)
VALUES
('ROLE_ADMIN'),
('ROLE_ANALYST'),
('ROLE_OPERATOR');

INSERT INTO users (login, name, password)
VALUES
('vlad', 'Vladimir', 'V100'),
('alex', 'Alexander', 'A100');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(1, 3),
(2, 2),
(2, 3);