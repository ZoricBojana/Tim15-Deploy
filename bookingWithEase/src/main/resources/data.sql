alter table room_reservation_date engine=innodb;
alter table quick_room_reservation engine=innodb;
alter table room_reservation engine=innodb;
insert into rentacar (id,name, address, description, rating, latitude, longitude) values (101,'Atlas servis', 'Novi sad', 'Najbolji rent-a-car servis',5, 45.267136, 19.833549 );
insert into rentacar (id,name, address, description, rating, latitude, longitude) values (202,'Uni Line', 'Beograd', 'Povoljno izdavanje vozila', 4, 44.787197, 20.457273);
insert into rentacar (id,name, address, description, rating, latitude, longitude) values (303,'DDM Rent-a-car', 'Novi Sad', 'Veliki izbor vozila', 4, 45.267136, 19.833549);
insert into rentacar (id,name, address, description, rating, latitude, longitude) values (404,'Inex', 'Novi Sad', 'Izdavanje vozila', 4, 45.267136, 19.833549);

insert into airline (id,name, address, description, rating, latitude, longitude) values (1000,'Aeroflot Russian Airlines', 'Russia', '', 1, 55.76, 37.64);
insert into airline (id,name, address, description, rating, latitude, longitude) values (2000,'Norwegian', 'Norway', '', 1, 55.76, 37.64);
insert into airline (id,name, address, description, rating, latitude, longitude) values (3000,'Ethiopian Airlines', 'Ethiopia ', '', 1, 55.76, 37.64);
insert into airline (id,name, address, description, rating, latitude, longitude) values (4000,'KLM Royal Dutch Airlines', 'Germany', '', 1, 55.76, 37.64);
insert into airline (id,name, address, description, rating, latitude, longitude) values (5000,'Air France', 'France', '', 1, 48.864716, 2.349014);
insert into airline (id,name, address, description, rating, latitude, longitude) values (6000,'Austrian Airlines', 'Austria', '', 1, 55.76, 37.64);
insert into airline (id,name, address, description, rating, latitude, longitude) values (7000,'Swiss International Air', 'Switzerland', '', 1, 55.76, 37.64);
insert into airline (id,name, address, description, rating, latitude, longitude) values (9000,'Turkish Airlines', 'Turkey', '', 1, 55.76, 37.64);
insert into airline (id,name, address, description, rating, latitude, longitude) values (10000,'Air Serbia', 'Serbia', '', 1, 44.787197, 20.457273);

insert into destination(id, address, name_aerodroms, airline_id) values (100, "Belgrade", "NTesla", 1000);
insert into destination(id, address, name_aerodroms, airline_id) values (200, "Belgrade", "NTesla", 2000);
insert into destination(id, address, name_aerodroms, airline_id) values (300, "Dubai", "UAE", 2000);
insert into destination(id, address, name_aerodroms, airline_id) values (400, "Paris", "CdeGol", 1000);

insert into flight (id, date_fligh, date_land, finald, information_luggage, length_travel, number, price_ticket, startd, time_travel, airline_id, final_id, start_id) values (1111,'2019-04-30','2019-05-30','Belgrade (NTesla)','da',5,5,5,'Paris (CdeGol)',1,1000,100,400);
insert into flight (id, date_fligh, date_land, finald, information_luggage, length_travel, number, price_ticket, startd, time_travel, airline_id, final_id, start_id) values (1222,'2019-09-30','2019-06-30','Paris (CdeGol)','ne',3,3,3,'Dubai (UAE)',2,2000,400,300);


insert into branch (id, rac_id, name, address) values (100, 101,'Atlas NS', 'Novi Sad');
insert into branch (id, rac_id, name, address) values (200, 303,'DDM BG', 'Beograd');
insert into branch (id, rac_id, name, address) values (300, 101,'Atlas ZL', 'Zlatibor');
insert into branch (id, rac_id, name, address) values (400, 101,'Atlas KV', 'Kraljevo');


insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (1, 'AA-11', 'Audi A4', 'automatic', 'red', 100, 400, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (2, 'BB-11', 'Renault Megane', 'manual', 'green',100, 350, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (3, 'CC-11', 'Volkswagen Passat', 'automatic', 'black', 100, 100, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (4, 'DD-11', 'Skoda Octavia', 'manual', 'green',100, 700, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (5, 'EE-11', 'Skoda Fabia', 'automatic', 'black', 100, 560, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (6, 'BB-21', 'Audi A4', 'manual', 'green',300, 200, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (7, 'CC-21', 'Audi A4', 'automatic', 'black', 300, 50, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (8, 'BB-31', 'Audi A4', 'manual', 'green',400, 50, 1);
insert into vehicle (id, registration_number, type, gear, color, branch_id, price_per_day, version) values (9, 'CC-41', 'Audi A4', 'automatic', 'black', 400, 60, 1);

insert into hotel_service_type_prices (id,bed_and_breakfast_enabled, bed_and_breakfast_price,half_board_enabled, half_board_price,full_board_enabled, full_board_price,all_inclusive_enabled, all_inclusive_price) values (100, true, 10,  false, 0, false, 0, false, 0);
insert into hotel_service_type_prices (id,bed_and_breakfast_enabled, bed_and_breakfast_price,half_board_enabled, half_board_price,full_board_enabled, full_board_price,all_inclusive_enabled, all_inclusive_price) values (200, true, 10,  true, 12, true, 15, true, 20);
insert into hotel_service_type_prices (id,bed_and_breakfast_enabled, bed_and_breakfast_price,half_board_enabled, half_board_price,full_board_enabled, full_board_price,all_inclusive_enabled, all_inclusive_price) values (300, true, 10,  false, 0, false, 0, false, 0);
insert into hotel_service_type_prices (id,bed_and_breakfast_enabled, bed_and_breakfast_price,half_board_enabled, half_board_price,full_board_enabled, full_board_price,all_inclusive_enabled, all_inclusive_price) values (400, true, 10,  false, 0, false, 0, false, 0);
insert into hotel_service_type_prices (id,bed_and_breakfast_enabled, bed_and_breakfast_price,half_board_enabled, half_board_price,full_board_enabled, full_board_price,all_inclusive_enabled, all_inclusive_price) values (500, true, 13,  true, 20, false, 0, false, 0);

insert into hotel (id, name, address, description, rating, service_type_prices_id, stars, latitude, longitude) values (100, 'Hotel van de Vijsel', 'Amsterdam', '',4.5, 200, 4, 52.379189, 4.899431);
insert into hotel (id, name, address, description, rating, service_type_prices_id,stars, latitude, longitude) values (200, 'Amsterdam Marriott Hotel', 'Amsterdam', '',4.2, 100, 5, 52.379189, 4.899431);
insert into hotel (id, name, address, description, rating, service_type_prices_id, stars, latitude, longitude) values (300, 'The Peninsula Paris', 'Paris', '',5, 300, 5, 48.864716, 2.349014);
insert into hotel (id, name, address, description, rating, service_type_prices_id, stars, latitude, longitude) values (400, 'Hotel de Nesle', 'Paris', '',4, 400, 3, 48.864716, 2.349014);
insert into hotel (id, name, address, description, rating, service_type_prices_id, stars, latitude, longitude) values (500, 'Red Planet Tokyo Asakusa', 'Tokyo', '',4, 500, 3,35.652832, 139.839478);

insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (100, 101, 1, 2, 5, 45, 100);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (101, 102, 1, 2, 5, 45, 100);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (102, 103, 1, 2, 3.7, 40, 200);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (106, 104, 1, 2, 4, 40, 200);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (107, 105, 1, 3, 4, 45, 200);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (108, 203, 2, 3, 5, 40, 200);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (109, 204, 2, 2, 5, 40, 200);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (110, 301, 3, 4, 5, 50, 200);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (111, 302, 3, 3, 5, 45, 200);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (103, 303, 3, 2, 5, 40, 300);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (104, 201, 3, 2, 5, 55, 100);
insert into room (id, room_number, floor_number, capacity, rating, price_per_nigth, hotel_id) values (105, 202, 2, 2, 5, 55, 100);

insert into price (id, price, start_date, end_date) values (100, 20, '2019-06-01 00:00:00', '2019-07-01 00:00:00');
insert into price (id, price, start_date, end_date) values (101, 25, '2019-07-01 00:00:00', '2019-08-01 00:00:00');
insert into price (id, price, start_date, end_date) values (102, 27, '2019-08-01 00:00:00', '2019-09-01 00:00:00');
insert into price (id, price, start_date, end_date) values (103, 20, '2019-09-01 00:00:00', '2019-10-01 00:00:00');

insert into price (id, price, start_date, end_date) values (104, 20, '2019-06-01 00:00:00', '2019-07-01 00:00:00');
insert into price (id, price, start_date, end_date) values (105, 25, '2019-07-01 00:00:00', '2019-08-01 00:00:00');
insert into price (id, price, start_date, end_date) values (106, 27, '2019-08-01 00:00:00', '2019-09-01 00:00:00');
insert into price (id, price, start_date, end_date) values (107, 20, '2019-09-01 00:00:00', '2019-10-01 00:00:00');

insert into room_prices (room_id, prices_id) values (100, 100);
insert into room_prices (room_id, prices_id) values (100, 101);
insert into room_prices (room_id, prices_id) values (100, 102);
insert into room_prices (room_id, prices_id) values (100, 103);

insert into room_prices (room_id, prices_id) values (101, 104);
insert into room_prices (room_id, prices_id) values (101, 105);
insert into room_prices (room_id, prices_id) values (102, 106);
insert into room_prices (room_id, prices_id) values (102, 107);
insert into room_prices (room_id, prices_id) values (106, 106);
insert into room_prices (room_id, prices_id) values (106, 107);

insert into hotel_special_offer (id, name, description, price) values (100, 'Wellness','', 10);
insert into hotel_special_offer (id, name, description, price) values (101, 'Wellness','', 12);
insert into hotel_special_offer (id, name, description, price) values (102, 'Wellness','', 11);
insert into hotel_special_offer (id, name, description, price) values (103, 'Wellness','', 13);

insert into hotel_special_offer (id, name, description, price) values (104, 'Gym','', 9);
insert into hotel_special_offer (id, name, description, price) values (105, 'Gym','', 10);
insert into hotel_special_offer (id, name, description, price) values (106, 'Gym','', 10);
insert into hotel_special_offer (id, name, description, price) values (107, 'Gym','', 12);

insert into hotel_special_offers (hotel_id, special_offers_id) values (100, 100);
insert into hotel_special_offers (hotel_id, special_offers_id) values (200, 101);
insert into hotel_special_offers (hotel_id, special_offers_id) values (300, 102);
insert into hotel_special_offers (hotel_id, special_offers_id) values (400, 103);

insert into hotel_special_offers (hotel_id, special_offers_id) values (100, 104);
insert into hotel_special_offers (hotel_id, special_offers_id) values (200, 105);
insert into hotel_special_offers (hotel_id, special_offers_id) values (300, 106);
insert into hotel_special_offers (hotel_id, special_offers_id) values (400, 107);

insert into discounts (id, price, points) values (1, 500, 1);
insert into discount (id, points, discount) values (1, 15, 10);
insert into discounts_discs(discounts_id, discs_id) values (1, 1);


INSERT INTO registered_user (id, username, password, first_name, last_name, email, telephone_number, enabled) VALUES (100, 'user', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'user@gmail.com', '0652232235',  true);
INSERT INTO registered_user (id, username, password, first_name, last_name, email, enabled) VALUES (101, 'user1', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko1', 'Markovic1', 'user1@example.com', true);
INSERT INTO registered_user (id, username, password, first_name, last_name, email, enabled) VALUES (102, 'user2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko2', 'Markovic2', 'user2@example.com', true);
INSERT INTO registered_user (id, username, password, first_name, last_name, email, enabled) VALUES (103, 'user3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko3', 'Markovic3', 'user3@example.com', true);

insert into room_reservation(id, room_id, user_id, check_in_date, check_out_date, reservation_date, total_price ) values (100, 102, 100, '2019-11-01 00:00:00', '2019-11-15 00:00:00', '2019-05-15 00:00:00', 200);
insert into room_reservation(id, room_id, user_id, check_in_date, check_out_date, reservation_date, total_price ) values (101, 101, 100, '2019-11-01 00:00:00', '2019-11-15 00:00:00', '2019-05-15 00:00:00', 200);
insert into room_reservation(id, room_id, user_id, check_in_date, check_out_date, reservation_date, total_price ) values (105, 102, 100, '2019-05-01 00:00:00', '2019-05-15 00:00:00', '2019-04-15 00:00:00', 200);
insert into room_reservation(id, room_id, user_id, check_in_date, check_out_date, reservation_date, total_price ) values (106, 101, 100, '2019-05-10 00:00:00', '2019-05-25 00:00:00', '2019-04-15 00:00:00', 200);
insert into quick_room_reservation(id, room_id, check_in_date, check_out_date, reservation_date, total_price, discount, final_price, version) values (103, 102, '2019-12-01 00:00:00', '2019-12-15 00:00:00', '2019-05-15 00:00:00', 600, 10, 540, 0);
insert into quick_room_reservation(id, room_id, check_in_date, check_out_date, reservation_date, total_price, discount, final_price, version) values (104, 102, '2019-12-16 00:00:00', '2019-12-25 00:00:00', '2019-05-15 00:00:00', 600, 20, 480, 0);

INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, type, password_changed) VALUES (200, 'admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nikola', 'Nikolic', 'admin@example.com', true, 0, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, company_id, type, password_changed) VALUES (300, 'adminrac', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Bojana', 'Zoric', 'admin1@example.com', true, 101, 3, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, company_id, type, password_changed) VALUES (400, 'adminhotel', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Ana', 'Mijailovic', 'admin2@example.com', true, 200, 2, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, type, company_id, password_changed) VALUES (500, 'adminairline', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marina', 'Simovic', 'admin3@example.com', true, 1, 1000, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, company_id, type, password_changed) VALUES (600, 'adminrac2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Bojana', 'Zoric', 'admin4@example.com', true, 101, 3, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, company_id, type, password_changed) VALUES (700, 'adminhotel2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Ana', 'Mijailovic', 'admin5@example.com', true, 300, 2, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, type, password_changed) VALUES (800, 'adminairline2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marina', 'Simovic', 'admin6@example.com', true, 1, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, company_id, type, password_changed) VALUES (900, 'adminrac3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Bojana', 'Zoric', 'admin7@example.com', true, 101, 3, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, company_id, type, password_changed) VALUES (1000, 'adminhotel3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Ana', 'Mijailovic', 'admin8@example.com', true, 200, 2, true);
INSERT INTO admin (id, username, password, first_name, last_name, email, enabled, type, password_changed) VALUES (1100, 'adminairline3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marina', 'Simovic', 'admin9@example.com', true, 1, true);




INSERT INTO authority (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (3, 'ROLE_ADMINRAC');
INSERT INTO authority (id, name) VALUES (4, 'ROLE_ADMINHOTEL');
INSERT INTO authority (id, name) VALUES (5, 'ROLE_ADMINAIRLINE');

INSERT INTO user_authority (user_id, authority_id) VALUES (100, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (101, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (102, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (103, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (200, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (300, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (400, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (500, 5);
INSERT INTO user_authority (user_id, authority_id) VALUES (600, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (700, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (800, 5);
INSERT INTO user_authority (user_id, authority_id) VALUES (900, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (1000, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (1100, 5);


insert into vehicle_reservation (id, vehicle_id, vehicle_user_id, check_in_date, check_out_date, total_price, version) values(111,1, 100,'2018-11-01 00:00:00', '2018-11-02 00:00:00', 100, 1);
insert into vehicle_reservation (id, vehicle_id, vehicle_user_id, check_in_date, check_out_date, total_price, version) values(222,1, 100,'2019-01-01 00:00:00', '2019-01-02 00:00:00', 200,1);
insert into vehicle_reservation (id, vehicle_id, vehicle_user_id, check_in_date, check_out_date, total_price, version) values(333,1, 100,'2019-02-01 00:00:00', '2019-02-02 00:00:00', 105,1);
insert into vehicle_reservation (id, vehicle_id, vehicle_user_id, check_in_date, check_out_date, total_price, version) values(444,1, 100,'2019-04-01 00:00:00', '2019-04-02 00:00:00', 150,1);
insert into vehicle_reservation (id, vehicle_id, vehicle_user_id, check_in_date, check_out_date, total_price, version) values(555,1, 100,'2019-03-01 00:00:00', '2019-03-02 00:00:00', 250,1);
insert into vehicle_reservation (id, vehicle_id, vehicle_user_id, check_in_date, check_out_date, total_price, version) values(666,1, 100,'2019-07-01 00:00:00', '2019-07-03 00:00:00', 250,1);

insert into quick_vehicle_reservation (id, vehicle_id, check_in_date, check_out_date, total_price, discount, version) values (150, 1, '2019-07-01 00:00:00', '2019-07-02 00:00:00', 250, 15, 1);
insert into quick_vehicle_reservation (id, vehicle_id, check_in_date, check_out_date, total_price, discount, version) values (151, 2, '2019-08-01 00:00:00', '2019-08-02 00:00:00', 400, 15, 1);
insert into quick_vehicle_reservation (id, vehicle_id, check_in_date, check_out_date, total_price, discount, version) values (152, 3, '2019-09-01 00:00:00', '2019-09-05 00:00:00', 200, 15, 1);
insert into quick_vehicle_reservation (id, vehicle_id, check_in_date, check_out_date, total_price, discount, version) values (153, 4, '2019-07-01 00:00:00', '2019-07-02 00:00:00', 150, 15, 1);
insert into quick_vehicle_reservation (id, vehicle_id, check_in_date, check_out_date, total_price, discount, version) values (154, 5, '2019-07-01 00:00:00', '2019-07-02 00:00:00', 270, 15, 1);


insert into racspecial_offer (id, name, racservice_id, price) values (1,"Pick vehicle at airport", 101, 10);

INSERT INTO friend_request (sender_id, reciever_id, date_created) VALUES (100, 101, '2019-06-01 00:00:00');
INSERT INTO friend_request (sender_id, reciever_id, date_created) VALUES (102, 100, '2019-06-01 00:00:00');
INSERT INTO friend_request (sender_id, reciever_id, date_created) VALUES (101, 103, '2019-06-01 00:00:00');

insert into seat (flight_id, seat_number, type, available) values (1111,  1, 1, true);
insert into seat (flight_id, seat_number, type, available) values (1111,  2, 1, true);
insert into seat (flight_id, seat_number, type, available) values (1111,  3, 1, true);
insert into seat (flight_id, seat_number, type, available) values (1111,  4, 2, true);
insert into seat (flight_id, seat_number, type, available) values (1111,  5, 2, true);
insert into seat (flight_id, seat_number, type, available) values (1111,  6, 2, true);
insert into seat (flight_id, seat_number, type, available) values (1111,  7, 0, true);
insert into seat (flight_id, seat_number, type, available) values (1111,  8, 1, false);
insert into seat (flight_id, seat_number, type, available) values (1111,  9, 2, true);
insert into seat (flight_id, seat_number, type, available) values (1111, 10, 2, true);
insert into seat (flight_id, seat_number, type, available) values (1111, 11, 2, true);
insert into seat (flight_id, seat_number, type, available) values (1111, 12, 1, true);
insert into seat (flight_id, seat_number, type, available) values (1111, 13, 1, true);
insert into seat (flight_id, seat_number, type, available) values (1111, 14, 1, true);
insert into seat (flight_id, seat_number, type, available) values (1111, 15, 0, true);
insert into seat (flight_id, seat_number, type, available) values (1111, -1, 0, false);
insert into seat (flight_id, seat_number, type, available) values (1111, 17, 1, false);
insert into seat (flight_id, seat_number, type, available) values (1111, 18, 1, false);

insert into quick_flight_reservation (id, flight_id, check_in_date, check_out_date, total_price, discount) values (1001, 1111, '2019-05-01 00:00:00', '2019-08-02 00:00:00', 45, 10);
insert into quick_flight_reservation (id, flight_id, check_in_date, check_out_date, total_price, discount) values (1002, 1111, '2019-04-01 00:00:00', '2019-08-02 00:00:00', 45, 10);

insert into quick_flight_reservation (id, flight_id, check_in_date, check_out_date, total_price, discount, f_user_id) values (1003, 1111, '2019-06-01 00:00:00', '2019-08-02 00:00:00', 45, 10, 102);
insert into quick_flight_reservation (id, flight_id, check_in_date, check_out_date, total_price, discount, f_user_id) values (1004, 1111, '2019-07-01 00:00:00', '2019-08-02 00:00:00', 45, 10, 103);
insert into quick_flight_reservation (id, flight_id, check_in_date, check_out_date, total_price, discount, f_user_id) values (1005, 1111, '2019-06-09 00:00:00', '2019-08-02 00:00:00', 45, 10, 102);

insert into flight_invite (seat_id, reservation_id, expiration_date, accepted) values (8, 1001, '2019-07-01 00:00:00', false);
insert into flight_invite (seat_id, reservation_id, expiration_date, accepted) values (17, 1002, '2019-07-01 00:00:00', false);
insert into flight_invite (seat_id, reservation_id, expiration_date, accepted) values (18, 1003, '2019-07-01 00:00:00', true);
insert into flight_invite (seat_id, reservation_id, expiration_date, accepted) values (9, 1004, '2019-08-01 00:00:00', true);
insert into flight_invite (seat_id, reservation_id, expiration_date, accepted) values (10, 1005, '2019-08-01 00:00:00', true);

insert into vehicle_rate (id, rate, reservation_id) values(1, 4.0, 111);
insert into vehicle_rate (id, rate, reservation_id) values(2, 3.0, 222);
insert into vehicle_rate (id, rate, reservation_id) values(3, 2.0, 333);

insert into hotel_rate (id, rate, reservation_id) values(1, 4.0, 100);
insert into hotel_rate (id, rate, reservation_id) values(2, 3.0, 101);


insert into flight_reservation (id, flight_id, f_user_id, check_in_date, check_out_date, total_price) values(1101, 1111, 103, '2019-07-01 00:00:00', '2019-07-05 00:00:00', 500); 
insert into flight_reservation (id, flight_id, f_user_id, check_in_date, check_out_date, total_price) values(1102, 1111, 102, '2019-01-01 00:00:00', '2019-01-05 00:00:00', 500); 

insert into room_reservation_date (id, room_id, day) values (1000, 102, '2019-11-01 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1001, 102, '2019-11-02 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1002, 102, '2019-11-03 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1003, 102, '2019-11-04 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1004, 102, '2019-11-05 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1005, 102, '2019-11-06 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1006, 102, '2019-11-07 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1007, 102, '2019-11-08 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1008, 102, '2019-11-09 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1009, 102, '2019-11-10 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1010, 102, '2019-11-11 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1011, 102, '2019-11-12 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1012, 102, '2019-11-13 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1013, 102, '2019-11-14 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1014, 102, '2019-11-15 00:00:00');

insert into room_reservation_date (id, room_id, day) values (1015, 101, '2019-11-01 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1016, 101, '2019-11-02 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1017, 101, '2019-11-03 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1018, 101, '2019-11-04 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1019, 101, '2019-11-05 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1020, 101, '2019-11-06 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1021, 101, '2019-11-07 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1022, 101, '2019-11-08 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1023, 101, '2019-11-09 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1024, 101, '2019-11-10 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1025, 101, '2019-11-11 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1026, 101, '2019-11-12 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1027, 101, '2019-11-13 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1028, 101, '2019-11-14 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1029, 101, '2019-11-15 00:00:00');

insert into room_reservation_date (id, room_id, day) values (1030, 102, '2019-12-01 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1033, 102, '2019-12-02 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1034, 102, '2019-12-03 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1035, 102, '2019-12-04 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1036, 102, '2019-12-05 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1037, 102, '2019-12-06 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1038, 102, '2019-12-07 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1039, 102, '2019-12-08 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1040, 102, '2019-12-09 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1041, 102, '2019-12-10 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1042, 102, '2019-12-11 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1043, 102, '2019-12-12 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1044, 102, '2019-12-13 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1045, 102, '2019-12-14 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1046, 102, '2019-12-15 00:00:00');

insert into room_reservation_date (id, room_id, day) values (1047, 102, '2019-12-16 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1048, 102, '2019-12-17 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1049, 102, '2019-12-18 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1050, 102, '2019-12-19 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1051, 102, '2019-12-20 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1052, 102, '2019-12-21 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1053, 102, '2019-12-22 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1054, 102, '2019-12-23 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1055, 102, '2019-12-24 00:00:00');
insert into room_reservation_date (id, room_id, day) values (1056, 102, '2019-12-25 00:00:00');

