create table use_types
(
   id integer not null,
   name varchar(255) not null
);

create table sites
(
   id integer not null,
   name varchar(255) not null,
   address varchar(255) not null,
   city varchar(255) not null,
   state varchar(255) not null,
   zipcode varchar(255) not null
);

create table site_uses
(
   id integer not null,
   description varchar(255) not null,
   site_id integer not null,
   size_sqft integer not null,
   use_type_id integer not null
);