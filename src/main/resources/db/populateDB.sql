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
VALUES ('2022-09-16 10:00', 'Завтрак', 500, 100000),
       ('2022-09-16 13:00', 'Обед', 1000, 100000),
       ('2022-09-16 20:00', 'Ужин', 500, 100000),
       ('2022-09-22 00:00', 'Еда на граничное значение', 100, 100000),
       ('2022-09-22 10:00', 'Завтрак', 1000, 100000),
       ('2022-09-22 13:00', 'Обед', 500, 100000),
       ('2022-09-22 20:00', 'Ужин', 410, 100000),
       ('2022-09-20 10:00', 'Завтрак Админа', 800, 100001),
       ('2022-09-20 14:00', 'Обед админа', 1300, 100001);