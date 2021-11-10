CREATE TABLE "public.office" (
                                 "id" serial NOT NULL,
                                 "code" varchar(100) NOT NULL UNIQUE,
                                 "name" varchar(255) NOT NULL,
                                 "office_type_id" bigint NOT NULL,
                                 "parent_office_id" bigint,
                                 CONSTRAINT "office_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



CREATE TABLE "public.indicator" (
                                    "id" serial NOT NULL,
                                    "name" varchar(255) NOT NULL,
                                    "code" varchar(100) NOT NULL UNIQUE,
                                    "order" int NOT NULL,
                                    "parent_indicator_id" bigint NOT NULL,
                                    "indicator_type_id" bigint NOT NULL,
                                    CONSTRAINT "indicator_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



CREATE TABLE "public.indicator_type_group" (
                                               "id" serial NOT NULL,
                                               "name" varchar(255) NOT NULL,
                                               "code" varchar(100) NOT NULL UNIQUE,
                                               CONSTRAINT "indicator_type_group_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



CREATE TABLE "public.year_month" (
                                     "id" serial NOT NULL,
                                     "year" int NOT NULL,
                                     "month" int NOT NULL,
                                     CONSTRAINT "year_month_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



CREATE TABLE "public.values_of_indicators" (
                                               "id" serial NOT NULL,
                                               "is_closed" BOOLEAN NOT NULL,
                                               "office_id" bigint NOT NULL,
                                               "month_id" bigint NOT NULL,
                                               "indicator_id" bigint NOT NULL,
                                               "value" bigint NOT NULL,
                                               "created" TIMESTAMP NOT NULL,
                                               "updated" TIMESTAMP NOT NULL,
                                               "is_plan" BOOLEAN NOT NULL,
                                               CONSTRAINT "values_of_indicators_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



CREATE TABLE "public.indicator_type" (
                                         "id" serial NOT NULL,
                                         "name" varchar(255) NOT NULL,
                                         "code" varchar(100) NOT NULL UNIQUE,
                                         "indicator_type_group_id" bigint NOT NULL,
                                         CONSTRAINT "indicator_type_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



CREATE TABLE "public.indicator_for_office_type" (
                                                    "id" serial NOT NULL,
                                                    "indicator_id" bigint NOT NULL,
                                                    "office_type_id" bigint NOT NULL,
                                                    "rounding_mode" varchar(50) NOT NULL,
                                                    CONSTRAINT "indicator_for_office_type_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



CREATE TABLE "public.office_type" (
                                      "id" serial NOT NULL,
                                      "name" varchar(255) NOT NULL,
                                      "code" varchar(100) NOT NULL UNIQUE,
                                      CONSTRAINT "office_type_pk" PRIMARY KEY ("id")
) WITH (
      OIDS=FALSE
      );



ALTER TABLE "office" ADD CONSTRAINT "office_fk0" FOREIGN KEY ("office_type_id") REFERENCES "office_type"("id");
ALTER TABLE "office" ADD CONSTRAINT "office_fk1" FOREIGN KEY ("parent_office_id") REFERENCES "office"("id");

ALTER TABLE "indicator" ADD CONSTRAINT "indicator_fk0" FOREIGN KEY ("parent_indicator_id") REFERENCES "indicator"("id");
ALTER TABLE "indicator" ADD CONSTRAINT "indicator_fk1" FOREIGN KEY ("indicator_type_id") REFERENCES "indicator_type"("id");



ALTER TABLE "values_of_indicators" ADD CONSTRAINT "values_of_indicators_fk0" FOREIGN KEY ("office_id") REFERENCES "office"("id");
ALTER TABLE "values_of_indicators" ADD CONSTRAINT "values_of_indicators_fk1" FOREIGN KEY ("month_id") REFERENCES "year_month"("id");
ALTER TABLE "values_of_indicators" ADD CONSTRAINT "values_of_indicators_fk2" FOREIGN KEY ("indicator_id") REFERENCES "indicator"("id");

ALTER TABLE "indicator_type" ADD CONSTRAINT "indicator_type_fk0" FOREIGN KEY ("indicator_type_group_id") REFERENCES "indicator_type_group"("id");

ALTER TABLE "indicator_for_office_type" ADD CONSTRAINT "indicator_for_office_type_fk0" FOREIGN KEY ("indicator_id") REFERENCES "indicator"("id");
ALTER TABLE "indicator_for_office_type" ADD CONSTRAINT "indicator_for_office_type_fk1" FOREIGN KEY ("office_type_id") REFERENCES "office_type"("id");









