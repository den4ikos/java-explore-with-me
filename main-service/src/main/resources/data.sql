drop table if exists categories cascade;
drop table if exists compilation_to_event cascade;
drop table if exists compilations cascade;
drop table if exists events cascade;
drop table if exists locations cascade;
drop table if exists requests cascade;
drop table if exists users cascade;

-- COMPILATIONS
create table if not exists compilations
(
    id      bigint generated by default as identity not null,
    pinned  boolean,
    title   varchar(255) default null,
    constraint pk_compilation_id primary key (id),
    unique (title)
);

-- CATEGORIES
create table if not exists categories
(
    id      bigint generated by default as identity not null,
    name    varchar(255) default null,
    constraint pk_category_id primary key(id),
    unique (name)
);

-- LOCATIONS
create table if not exists locations
(
    id bigint generated by default as identity not null,
    lat real not null,
    lon real not null,
    constraint pk_location_id primary key (id)
);

-- USERS
create table if not exists users
(
    id bigint generated by default as identity not null,
    name varchar(100) not null,
    email varchar(255) not null,
    constraint pk_usera_id primary key (id),
    unique (email)
);

-- EVENTS
create table if not exists events
(
    id                  bigint generated by default as identity not null,
    title               varchar(255) not null,
    annotation          varchar(300) default null,
    description         text default null,
    category_id         bigint default null,
    confirmed_request   int default 0,
    event_date          timestamp without time zone not null,
    initiator           bigint default null,
    location_id         bigint default null,
    pid                 boolean,
    participant_limit   int default 0,
    published_on        timestamp without time zone default null,
    request_moderation  boolean default true,
    state               varchar(50) not null,
    views               int default 0,
    created_at          timestamp without time zone not null,
    constraint pk_event_id primary key (id)
);

-- COMPILATION TO EVENT
create table if not exists compilation_to_event
(
    compilation_id  bigint,
    event_id        bigint,
    primary key (compilation_id, event_id)
);

-- REQUESTS
create table if not exists requests
(
    id              bigint generated by default as identity not null,
    event_id        bigint,
    requestor_id    bigint,
    status          varchar (20) not null,
    created         timestamp without time zone not null,
    primary key (id)
);

alter table events drop constraint if exists fk_event_category_id;
alter table events add constraint fk_event_category_id foreign key (category_id) references categories (id) on delete set null;

alter table events drop constraint if exists fk_event_initiator;
alter table events add constraint fk_event_initiator foreign key (initiator) references users (id) on delete set null;

alter table events drop constraint if exists fk_event_location_id;
alter table events add constraint fk_event_location_id foreign key (location_id) references locations (id) on delete set null;

alter table compilation_to_event drop constraint if exists fk_compilation_id_compilation_to_event;
alter table compilation_to_event add constraint fk_compilation_id_compilation_to_event foreign key (compilation_id) references compilations (id) on delete cascade;

alter table compilation_to_event drop constraint if exists fk_event_id_compilation_to_event;
alter table compilation_to_event add constraint fk_event_id_compilation_to_event foreign key (event_id) references events (id) on delete cascade;

alter table requests drop constraint if exists fk_event_id_requests;
alter table requests add constraint fk_event_id_requests foreign key (event_id) references events (id) on delete cascade;

alter table requests drop constraint if exists fk_requestor_id_requests;
alter table requests add constraint fk_requestor_id_requests foreign key (requestor_id) references users (id) on delete cascade;
