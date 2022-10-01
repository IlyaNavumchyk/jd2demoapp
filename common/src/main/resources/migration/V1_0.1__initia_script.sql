create table if not exists users
(
    id                bigserial
        primary key,
    user_name         varchar(20)  default 'name'::character varying    not null,
    surname           varchar(50)  default 'surname'::character varying not null,
    birth             timestamp(6) default CURRENT_TIMESTAMP(6)         not null,
    is_deleted        boolean      default false                        not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)         not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6)         not null,
    user_login        varchar(100)                                      not null
        unique,
    user_password     varchar(200)                                      not null
);

alter table users
    owner to postgres;

create index if not exists users_user_name_index
    on users (user_name);

create index if not exists users_user_name_surname_index
    on users (user_name, surname);

create unique index if not exists users_id_uindex
    on users (id);

create index if not exists users_birth_index
    on users (birth);

create index if not exists users_is_deleted_index
    on users (is_deleted);

create table if not exists roles
(
    id                serial
        primary key,
    role_name         varchar(20)                               not null
        unique,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6) default CURRENT_TIMESTAMP(6) not null
);

alter table roles
    owner to postgres;

create unique index if not exists roles_id_uindex
    on roles (id);

create table if not exists l_user_role
(
    id      bigserial
        primary key,
    user_id bigint  not null
        constraint l_user_role_users_id_fk
            references users
            on update cascade on delete cascade,
    role_id integer not null
        constraint l_user_role_roles_id_fk
            references roles
            on update cascade on delete cascade
);

alter table l_user_role
    owner to postgres;

create unique index if not exists l_user_role_user_id_role_id_uindex
    on l_user_role (user_id, role_id);