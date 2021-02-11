create table if not exists "boards" (
  "id" varchar(255) primary key,
  "title" varchar(255) not null,
  "description" varchar(255)
);