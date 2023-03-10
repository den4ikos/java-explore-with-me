drop table if exists hits;
drop table if exists apps;

create table if not exists apps
(
    id  bigint generated by default as identity not null,
    name    varchar(100) not null,

    constraint pk_app_id primary key (id),
    unique (name)
);

create table if not exists hits
(
    id              bigint generated by default as identity not null,
    app_id          bigint,
    uri             varchar(100) not null,
    ip              varchar(20) not null,
    timestamp     timestamp without time zone not null,

    constraint pk_hit_id primary key (id)
);

alter table hits drop constraint if exists fk_hit_app_id;
alter table hits add constraint fk_hit_app_id foreign key(app_id) references apps (id) on delete cascade;