
drop table if exists users;
drop table if exists tickets;

create table users
(
   user_id              int not null auto_increment,
   username             varchar(255) not null,
   email                varchar(255) not null,
   password             varchar(255),
   primary key (user_id)
);


create table tickets
(
   ticket_id            int not null auto_increment,
   user_id              int,
   title                varchar(255) not null,
   description          varchar(255) comment '',
   status               varchar(20) not null,
   primary key (ticket_id)
);

alter table tickets add constraint FK_ADD_TICKET_USER foreign key (user_id)
      references users (user_id) on delete restrict on update restrict;
