CREATE TABLE user (
    id bigint auto_increment,
    name varchar(300),
    password varchar(300)
);

CREATE TABLE tweet (
    id bigint auto_increment,
    owner_id bigint,
    created datetime,
    text varchar(140),
    foreign key (owner_id) references user(id)
);