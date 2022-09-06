CREATE TABLE "companies"
(
    id          serial,
    name        VARCHAR   NOT NULL,
    date_create TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
);
INSERT INTO "companies"
VALUES (Default, 'IT TECH', '2001.03.14');
INSERT INTO "companies"
VALUES (Default, 'IT NET', '2012.12.02');
INSERT INTO "companies"
VALUES (Default, 'ITois', '2022.01.19');

CREATE TABLE "employees"
(
    id         serial,
    name       VARCHAR NOT NULL,
    status     VARCHAR NOT NULL,
    salary     BIGINT  NOT NULL,
    year_birth INTEGER NOT NULL,
    company_id BIGINT  NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (company_id) REFERENCES "companies" (id)
);
INSERT INTO "employees"
VALUES (Default, 'Jessie Roberts', 'full_time_emp', 100000, 1989,
        (select c.id from companies c where c.name = 'IT TECH'));
INSERT INTO "employees"
VALUES (Default, 'Byron Ferguson', 'freelance_emp', 120000, 2000,
        (select c.id from companies c where c.name = 'IT TECH'));
INSERT INTO "employees"
VALUES (Default, 'Jose Green', 'full_time_emp', 130000, 1991,
        (select c.id from companies c where c.name = 'IT TECH'));
INSERT INTO "employees"
VALUES (Default, 'John Delgado', 'full_time_emp', 50000, 1979,
        (select c.id from companies c where c.name = 'IT TECH'));
INSERT INTO "employees"
VALUES (Default, 'Raymond Ramirez', 'freelance_emp', 200000, 1987,
        (select c.id from companies c where c.name = 'IT TECH'));
INSERT INTO "employees"
VALUES (Default, 'Jose Alvarado', 'full_time_emp', 56000, 20001,
        (select c.id from companies c where c.name = 'IT TECH'));

INSERT INTO "employees"
VALUES (Default, 'Brandon James', 'full_time_emp', 77000, 1999,
        (select c.id from companies c where c.name = 'IT NET'));
INSERT INTO "employees"
VALUES (Default, 'Gary McDonald', 'full_time_emp', 314000, 1984,
        (select c.id from companies c where c.name = 'IT NET'));
INSERT INTO "employees"
VALUES (Default, 'Albert Jenkins', 'freelance_emp', 140000, 1982,
        (select c.id from companies c where c.name = 'IT NET'));
INSERT INTO "employees"
VALUES (Default, 'Bruce Myers', 'freelance_emp', 90000, 1980,
        (select c.id from companies c where c.name = 'IT NET'));
INSERT INTO "employees"
VALUES (Default, 'Gabriel Gardner', 'full_time_emp', 70000, 1993,
        (select c.id from companies c where c.name = 'IT NET'));
INSERT INTO "employees"
VALUES (Default, 'Ralph McDonald', 'full_time_emp', 400000, 1998,
        (select c.id from companies c where c.name = 'IT NET'));

INSERT INTO "employees"
VALUES (Default, 'John Woods', 'freelance_emp', 177000, 1998,
        (select c.id from companies c where c.name = 'ITois'));
INSERT INTO "employees"
VALUES (Default, 'Richard Montgomery', 'freelance_emp', 134000, 1983,
        (select c.id from companies c where c.name = 'ITois'));
INSERT INTO "employees"
VALUES (Default, 'Arthur Brock', 'full_time_emp', 200000, 1976,
        (select c.id from companies c where c.name = 'ITois'));
INSERT INTO "employees"
VALUES (Default, 'Jay Foster', 'full_time_emp', 254000, 1975,
        (select c.id from companies c where c.name = 'ITois'));
INSERT INTO "employees"
VALUES (Default, 'Louis Horton', 'full_time_emp', 76000, 2000,
        (select c.id from companies c where c.name = 'ITois'));
INSERT INTO "employees"
VALUES (Default, 'William Austin', 'freelance_emp', 304500, 1981,
        (select c.id from companies c where c.name = 'ITois'));
