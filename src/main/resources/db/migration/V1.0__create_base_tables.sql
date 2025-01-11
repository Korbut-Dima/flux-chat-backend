create table users (
    id bigserial not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    phone varchar(255),
    role varchar(255),
    primary key (id));

alter table if exists users
    drop constraint if exists uk_users_email,
    add constraint uk_users_email unique (email),

    drop constraint if exists uk_users_phone,
    add constraint uk_users_phone unique (phone);
