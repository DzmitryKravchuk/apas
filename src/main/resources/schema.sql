CREATE TABLE IF NOT EXISTS "office" (
                                 ID serial NOT NULL,
                                 CODE varchar(100) NOT NULL UNIQUE,
                                 NAME varchar(255) NOT NULL,
                                 OFFICE_TYPE_ID bigint NOT NULL,
                                 PARENT_ID bigint,
                                 CONSTRAINT office_pk PRIMARY KEY (ID)
);



CREATE TABLE IF NOT EXISTS "indicator" (
                                    ID serial NOT NULL,
                                    NAME varchar(255) NOT NULL,
                                    CODE varchar(100) NOT NULL UNIQUE,
                                    PARENT_ID bigint NOT NULL,
                                    INDICATOR_TYPE_ID bigint NOT NULL,
                                    CONSTRAINT indicator_pk PRIMARY KEY (ID)
);






CREATE TABLE IF NOT EXISTS "year_month" (
                                     ID serial NOT NULL,
                                     YEAR int NOT NULL,
                                     MONTH int NOT NULL,
                                     CONSTRAINT year_month_pk PRIMARY KEY (ID)
);



CREATE TABLE IF NOT EXISTS "values_of_indicators" (
                                               ID serial NOT NULL,
                                               IS_CLOSED BOOLEAN NOT NULL,
                                               OFFICE_ID bigint NOT NULL,
                                               MONTH_ID bigint NOT NULL,
                                               INDICATOR_ID bigint NOT NULL,
                                               VALUE bigint NOT NULL,
                                               CREATED TIMESTAMP NOT NULL,
                                               UPDATED TIMESTAMP NOT NULL,
                                               IS_PLAN BOOLEAN NOT NULL,
                                               CONSTRAINT values_of_indicators_pk PRIMARY KEY (ID)
);



CREATE TABLE IF NOT EXISTS "indicator_type" (
                                         ID serial NOT NULL,
                                         NAME varchar(255) NOT NULL,
                                         CODE varchar(100) NOT NULL UNIQUE,
                                         CONSTRAINT indicator_type_pk PRIMARY KEY (ID)
);



CREATE TABLE IF NOT EXISTS "indicator_for_office_type" (
                                                    ID serial NOT NULL,
                                                    INDICATOR_ID bigint NOT NULL,
                                                    OFFICE_TYPE_ID bigint NOT NULL,
                                                    ROUNDING_MODE varchar(50) NOT NULL,
                                                    CONSTRAINT indicator_for_office_type_pk PRIMARY KEY (ID)
);



CREATE TABLE IF NOT EXISTS "office_type" (
                                      ID serial NOT NULL,
                                      NAME varchar(255) NOT NULL,
                                      CODE varchar(100) NOT NULL UNIQUE,
                                      HIERARCHY_LVL int NOT NULL,
                                      CONSTRAINT office_type_pk PRIMARY KEY (ID)
);



ALTER TABLE "office" ADD CONSTRAINT office_fk0 FOREIGN KEY (OFFICE_TYPE_ID) REFERENCES "office_type"(ID);
ALTER TABLE "office" ADD CONSTRAINT office_fk1 FOREIGN KEY (PARENT_ID) REFERENCES "office"(ID);


ALTER TABLE "indicator" ADD CONSTRAINT indicator_fk0 FOREIGN KEY (PARENT_ID) REFERENCES "indicator"(ID);
ALTER TABLE "indicator" ADD CONSTRAINT indicator_fk1 FOREIGN KEY (INDICATOR_TYPE_ID) REFERENCES "indicator_type"(ID);


ALTER TABLE "values_of_indicators" ADD CONSTRAINT values_of_indicators_fk0 FOREIGN KEY (OFFICE_ID) REFERENCES "office"(ID);
ALTER TABLE "values_of_indicators" ADD CONSTRAINT values_of_indicators_fk1 FOREIGN KEY (MONTH_ID) REFERENCES "year_month"(ID);
ALTER TABLE "values_of_indicators" ADD CONSTRAINT values_of_indicators_fk2 FOREIGN KEY (INDICATOR_ID) REFERENCES "indicator"(ID);


ALTER TABLE "indicator_for_office_type" ADD CONSTRAINT indicator_for_office_type_fk0 FOREIGN KEY (INDICATOR_ID) REFERENCES "indicator"(ID);
ALTER TABLE "indicator_for_office_type" ADD CONSTRAINT indicator_for_office_type_fk1 FOREIGN KEY (OFFICE_TYPE_ID) REFERENCES "office_type"(ID);










