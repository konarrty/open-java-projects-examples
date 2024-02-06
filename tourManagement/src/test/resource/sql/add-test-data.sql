insert into users (id, email, password, role, username)
values (1, 'testmail@mail.ru', 'password', 0, 'user'),
       (2, 'testmail2@mail.ru', 'password', 2, 'user2'),
       (3, 'testmail3@mail.ru', 'password', 1, 'user3');

insert into clients (id, first_name, last_name, middle_name, passport_number, phone_number, social_href, user_id)
values (1, 'Иван', 'Иванов', 'Иванович', 'AB1234567', '+375296822943', 'https://www.instagram.com/', 2);

insert into tour_operators (id, description, name, user_id)
values (1, 'Лучший туроператор', 'Оператор', 1);

insert into agents (id, salary, operator_id, user_id)
values (1, 2000, 1, 3);

insert into hotels (id, city, country, house, name, stars, street, operator_id)
values (1, 'Берн', 'Швейцария', 34, 'отель', 5, 'Zytglogge', 1);

insert into tour_details (id, description, name, nutrition_type, price, rest_type, hotel_id, operator_id)
values (1, 'описание.....', 'Прекрасная швейцария', 0, 3000, 1, 1, 1);

insert into tours (id, capacity, end_date_time, start_date_time, last_time_tour, details_id)
values (1, 40, '2023-02-18 12:00', '2023-02-10 12:00', false, 1);

insert into reservations (status, tour_id, client_id, agent_id)
values (2, 1, 1, 1);




