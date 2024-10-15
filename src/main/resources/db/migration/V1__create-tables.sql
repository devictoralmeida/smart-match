create table "tb_candidate"
(
  "created_at"  datetime(6),
  "id"          binary(16) not null,
  "password"    varchar(100),
  "curriculum"  varchar(255),
  "description" varchar(255),
  "email"       varchar(255),
  "name"        varchar(255),
  "username"    varchar(255),
  CONSTRAINT "pk_tb_candidate" PRIMARY KEY ("id")
) engine=InnoDB;

create table "tb_company"
(
  "created_at"  datetime(6),
  "id"          binary(16) not null,
  "password"    varchar(100),
  "description" varchar(255),
  "email"       varchar(255),
  "name"        varchar(255),
  "username"    varchar(255),
  "website"     varchar(255),
  CONSTRAINT "pk_tb_company" PRIMARY KEY ("id")
) engine=InnoDB;

create table "tb_job"
(
  "created_at"  datetime(6),
  "id"          binary(16) not null,
  "id_company"  binary(16),
  "benefits"    varchar(255),
  "description" varchar(255),
  "level"       varchar(255),
  CONSTRAINT "pk_tb_job" PRIMARY KEY ("id"),
  CONSTRAINT "fk_tbjob_tbcompany" FOREIGN KEY ("id_company") REFERENCES "tb_company" ("id")
) engine=InnoDB;

create table "tb_apply_job"
(
  "created_at"   datetime(6),
  "id"           binary(16) not null,
  "id_candidate" binary(16) not null,
  "id_job"       binary(16) not null,
  CONSTRAINT "pk_tb_apply_job" PRIMARY KE ("id"),
  CONSTRAINT "fk_tbapplyjob_tbcandidate" FOREIGN KEY ("id_candidate") REFERENCES "tb_candidate" ("id"),
  CONSTRAINT "fk_tbapplyjob_tbjob" FOREIGN KEY ("id_job") REFERENCES "tb_job" ("id")
) engine=InnoDB;