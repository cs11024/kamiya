# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table data (
  id                        varchar(255) not null,
  rightnum                  integer,
  testcase                  varchar(255),
  constraint pk_data primary key (id))
;

create table user (
  id                        varchar(255) not null,
  password                  varchar(255),
  constraint pk_user primary key (id))
;

create sequence data_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists data;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists data_seq;

drop sequence if exists user_seq;

