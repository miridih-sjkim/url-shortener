DROP TABLE IF EXISTS shorten_url CASCADE;
DROP SEQUENCE IF EXISTS shorten_url_id_seq;

CREATE TABLE shorten_url
(
    id           bigint,
    original_url varchar(255),
    shorten_url  varchar(255),
    primary key (id)
);

CREATE SEQUENCE shorten_url_id_seq START WITH 1 INCREMENT BY 1;
