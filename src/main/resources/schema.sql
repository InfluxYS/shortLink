create table short_link
(
    number        int auto_increment,
    full_url      varchar(2000) not null,
    request_count int default 0 not null,
    sha256        varchar(64)   not null,
    constraint short_link_number_uindex
        unique (number),
    constraint short_link_sha256_uindex
        unique (sha256)
);

alter table short_link
    add primary key (number);

