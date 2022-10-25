DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;


ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES (to_timestamp('16-09-2022 10:00', 'dd-mm-yyyy hh24:mi'), 'Завтрак', 500, 100000),
       (to_timestamp('16-09-2022 13:00', 'dd-mm-yyyy hh24:mi'), 'Обед', 1000, 100000),
       (to_timestamp('16-09-2022 20:00', 'dd-mm-yyyy hh24:mi'), 'Ужин', 500, 100000),
       (to_timestamp('22-09-2022 00:00', 'dd-mm-yyyy hh24:mi'), 'Еда на граничное значение', 100, 100000),
       (to_timestamp('22-09-2022 10:00', 'dd-mm-yyyy hh24:mi'), 'Завтрак', 1000, 100000),
       (to_timestamp('22-09-2022 13:00', 'dd-mm-yyyy hh24:mi'), 'Обед', 500, 100000),
       (to_timestamp('22-09-2022 20:00', 'dd-mm-yyyy hh24:mi'), 'Ужин', 410, 100000),
       (to_timestamp('20-09-2022 10:00', 'dd-mm-yyyy hh24:mi'), 'Завтрак Админа', 800, 100001),
       (to_timestamp('20-09-2022 14:00', 'dd-mm-yyyy hh24:mi'), 'Обед админа', 1300, 100001);