create sequence if not exists hibernate_sequence start 1 increment 1;

create table if not exists author
(
    author_id   bigserial   not null primary key,
    name        text        not null unique
);

create table if not exists book
(
    book_id             bigserial   not null primary key,
    publishing_year     smallint    not null,
    title               text        not null unique
);

create table if not exists book_author
(
    author_id   bigint  references author,
    book_id     bigint  references book,
    primary key (author_id, book_id)
);