/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019-06-22 10:13:07                          */
/*==============================================================*/


drop table if exists Admin;

drop table if exists Admin_login_log;

drop table if exists Article;

drop table if exists Catalog;

drop table if exists Comment;

/*==============================================================*/
/* Table: Admin                                                 */
/*==============================================================*/
create table Admin
(
   admin_id             int not null auto_increment,
   admin_name           varchar(20) not null,
   admin_pass           varchar(20) not null,
   nickname             varchar(50) not null,
   primary key (admin_id)
);

/*==============================================================*/
/* Table: Admin_login_log                                       */
/*==============================================================*/
create table Admin_login_log
(
   log_id               int not null auto_increment,
   admin_id             int,
   login_ip             varchar(50) not null,
   login_date           timestamp not null,
   primary key (log_id)
);

/*==============================================================*/
/* Table: Article                                               */
/*==============================================================*/
create table Article
(
   article_id           int not null auto_increment,
   catalog_id           int,
   admin_id             int,
   title                varchar(100) not null,
   keywords             varchar(100) not null,
   summary              text not null,
   content              text not null,
   pub_date             timestamp not null,
   click                int not null,
   primary key (article_id)
);

/*==============================================================*/
/* Table: Catalog                                               */
/*==============================================================*/
create table Catalog
(
   catalog_id           int not null auto_increment,
   catalog_name         varchar(20) not null,
   descr                varchar(50),
   primary key (catalog_id)
);

/*==============================================================*/
/* Table: Comment                                               */
/*==============================================================*/
create table Comment
(
   c_id                 int not null auto_increment,
   article_id           int,
   c_content            text not null,
   c_date               timestamp not null,
   c_nickname           varchar(20) not null,
   c_email              varchar(50) not null,
   primary key (c_id)
);

alter table Admin_login_log add constraint FK_Login_Log_Admin foreign key (admin_id)
      references Admin (admin_id) on delete restrict on update restrict;

alter table Article add constraint FK_Article_Admin foreign key (admin_id)
      references Admin (admin_id) on delete restrict on update restrict;

alter table Article add constraint FK_Article_Catalog foreign key (catalog_id)
      references Catalog (catalog_id) on delete restrict on update restrict;

alter table Comment add constraint FK_Comment_Article foreign key (article_id)
      references Article (article_id) on delete restrict on update restrict;

