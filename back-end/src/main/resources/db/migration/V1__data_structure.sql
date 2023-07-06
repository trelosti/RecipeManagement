CREATE TABLE users
(
    id          BIGINT(64) AUTO_INCREMENT NOT NULL,
    created_at  datetime(6)     NOT NULL,
    updated_at  datetime(6)     NOT NULL,
    login       VARCHAR(255)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255)  NOT NULL,
    last_name   VARCHAR(255)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);