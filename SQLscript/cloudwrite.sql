-- run the following from MySQL workbench or some other MySQL client

-- comment in/out as appropriate:
-- CREATE DATABASE cloud_write;
use cloud_write;

create table concept (id bigint not null auto_increment, description varchar(255), purpose varchar(255), fundamental_piece_id bigint, primary key (id)) engine=InnoDB;
create table exposition_piece (id bigint not null auto_increment, created_date datetime, current_progress varchar(255), exposition_purpose varchar(255), future_work varchar(255), keyword varchar(255), last_modified_date datetime, title varchar(255), standfirst_id bigint, primary key (id)) engine=InnoDB;
create table fundamental_piece (id bigint not null auto_increment, created_date datetime, keyword varchar(255), last_modified_date datetime, prerequisites varchar(255), summary varchar(255), title varchar(255), primary key (id)) engine=InnoDB;
create table key_result (id bigint not null auto_increment, description varchar(255), exposition_piece_id bigint, primary key (id)) engine=InnoDB;
create table standfirst (id bigint not null auto_increment, approach varchar(255), rationale varchar(255), exposition_piece_id bigint, primary key (id)) engine=InnoDB;
alter table concept add constraint FK2tkq7dqkl0bm2c4lcetue2l20 foreign key (fundamental_piece_id) references fundamental_piece (id);
alter table exposition_piece add constraint FKc47fsu3dcmt90jqpbufes0cu8 foreign key (standfirst_id) references standfirst (id);
alter table key_result add constraint FK2mc3av4trnuwpabkm5fnamrvq foreign key (exposition_piece_id) references exposition_piece (id);
alter table standfirst add constraint FKnpgb2vbbdff6knnyn8wv2xm7y foreign key (exposition_piece_id) references exposition_piece (id);