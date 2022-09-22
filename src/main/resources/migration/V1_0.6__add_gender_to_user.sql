alter table users
    add if not exists gender varchar(15) default 'NOT_SELECTED' not null;