insert into country(name) values ('Srbija');
insert into country(name) values ('Rusija');
insert into country(name) values ('Italija');
insert into country(name) values ('Peru');

insert into currency(name, official_code, domicilna, country_id) values ('Dinar', 'DIN', true, 1);
insert into currency(name, official_code, domicilna, country_id) values ('Rublja', 'RUB', true, 2);
insert into currency(name, official_code, domicilna, country_id) values ('Lira', 'LIR', true, 3);
insert into currency(name, official_code, domicilna, country_id) values ('Sol', 'SOL', true, 4);

insert into bank(bank_id,bank_pib,bank_name,bank_address,bank_email,bank_web,bank_tel,bank_fax,bank_act,bank_swt,bank_tr_acc,country_id)
values(1,'993456789','Vojvodjanska banka','Bulevear fronta 20','vojvodjanska@gmail.com','www.vobanka.com','021/555-333','232-444',true,'23345678','222-111-333',1);

insert into bank(bank_id,bank_pib,bank_name,bank_address,bank_email,bank_web,bank_tel,bank_fax,bank_act,bank_swt,bank_tr_acc,country_id)
values(2,'123456789','Komercijalna banka','Bulvear nardonog fronta 20','komercijalna@mail.rs','www.komercijalna.com','024/555-131','444-444',true,'12345678','333-111-333',1);

insert into bank(bank_id,bank_pib,bank_name,bank_address,bank_email,bank_web,bank_tel,bank_fax,bank_act,bank_swt,bank_tr_acc,country_id)
values(3,'123456789','Erste banka','Bulvear nardonog fronta 100','erstelna@mail.rs','www.erste.com','024/525-111','123-123',true,'98456456','888-555-444',2);

insert into exchange_list(date, number_of_exchange_list, used_since, bank_bank_id) 
values('2017-10-15', 123, '2017-10-1', 1);

insert into exchange_list(date, number_of_exchange_list, used_since, bank_bank_id) 
values('2017-2-15', 222, '2014-10-1', 1);

insert into exchange_list(date, number_of_exchange_list, used_since, bank_bank_id) 
values('2017-1-2', 321, '2017-10-1', 2);

insert into currency_exchange(buy_rate, middle_rate, sell_rate, exchange_list_id, primary_currency_id, according_to_currency_id) 
values (321, 320, 319, 1, 1, 2);
insert into currency_exchange(buy_rate, middle_rate, sell_rate, exchange_list_id, primary_currency_id, according_to_currency_id) 
values (100.4, 102.4, 97.9, 2, 1, 3);
insert into currency_exchange(buy_rate, middle_rate, sell_rate, exchange_list_id, primary_currency_id, according_to_currency_id) 
values (50, 51, 52, 3, 3, 4);

insert into city(id, name, ptt_number, country_id) values(1, 'Beograd', '11000', 1);
insert into city(id, name, ptt_number, country_id) values(2, 'Novi sad', '21000', 1);
insert into city(id, name, ptt_number, country_id) values(3, 'Moskva', '31000', 2);
insert into city(id, name, ptt_number, country_id) values(4, 'Rim', '41000', 3);

insert into payment_type(id, payment_type_name) values(1, 'Kes');
insert into payment_type(id, payment_type_name) values(2, 'Cekovi');
insert into payment_type(id, payment_type_name) values(3, 'Kredit');
insert into payment_type(id, payment_type_name) values(4, 'Transfer novca');

insert into user(id, email, name, password, surname) values (1, 'mirko@gmail.com', 'Mirko', '$2a$10$.BJjJ3CVqWoSukqyxqx0z.zVm79kcWDcIdDbfYKM/BcI4rdIn2Nn.', 'Mikac');
insert into user(id, email, name, password, surname) values (2, 'marko@gmail.com', 'Marko', '$2a$10$.BJjJ3CVqWoSukqyxqx0z.zVm79kcWDcIdDbfYKM/BcI4rdIn2Nn.', 'Kljajic');
insert into user(id, email, name, password, surname) values (3, 'stefan@gmail.com', 'Stefan', '$2a$10$.BJjJ3CVqWoSukqyxqx0z.zVm79kcWDcIdDbfYKM/BcI4rdIn2Nn.', 'Varajic');
insert into user(id, email, name, password, surname) values (4, 'darko@gmail.com', 'Darko', '$2a$10$.BJjJ3CVqWoSukqyxqx0z.zVm79kcWDcIdDbfYKM/BcI4rdIn2Nn.', 'Tacic');
insert into user(id, email, name, password, surname) values (5, 'sajic@gmail.com', 'Nikola', '$2a$10$.BJjJ3CVqWoSukqyxqx0z.zVm79kcWDcIdDbfYKM/BcI4rdIn2Nn.', 'Sajic');

insert into client(id,address,date_of_birth) values (1,'Bulevar narodnog fronta 2','1994-10-15');
insert into client(id,address,date_of_birth) values (2,'Dunavska 6, Backa Topola 24300','1994-02-24');
insert into client(id,address,date_of_birth) values (3,'Doze Djerdja 13, Novi Sad 21000','1994-10-15');
insert into client(id,address,date_of_birth) values (4,'Bulevar despota Stefana 51, Novi Sad 21000','1994-10-15');
insert into client(id,address,date_of_birth) values (5,'Bulevar narodnog fronta 44, Novi Sad 21000','1994-10-15');

insert into account(account_id,account_num,account_date,account_active,bank_bank_id,client_id, currency_id) values(1,'333-111-333','2017-05-05',true,1,1, 1);
insert into account(account_id,account_num,account_date,account_active,bank_bank_id,client_id, currency_id) values(2,'222-111-444','2017-05-06',true,3,2, 1);
insert into account(account_id,account_num,account_date,account_active,bank_bank_id,client_id, currency_id) values(3,'666-111-333','2017-06-03',true,2,3, 3);
insert into account(account_id,account_num,account_date,account_active,bank_bank_id,client_id, currency_id) values(4,'333-222-444','2017-06-01',true,1,5, 1);

insert into daily_account_status(id, date,account_account_id, previous_amount, transfer_expenses, number_of_changes, transfer_in_favor, current_amount)
values(1, '2017-05-09', 1, 50000, 15000, 1, 0, 35000);
insert into daily_account_status(id, date,account_account_id, previous_amount, transfer_expenses, number_of_changes, transfer_in_favor, current_amount)
values(2, '2017-05-16', 1, 35000, 0, 1, 13500, 48500);
insert into daily_account_status(id, date,account_account_id, previous_amount, transfer_expenses, number_of_changes, transfer_in_favor, current_amount)
values(3, '2017-06-03', 3, 0, 1500, 1, 0, -1500);

insert into analytical_statement(id, amount, approval_authorization_number, approval_model, currency_date, date_of_receipt, 
								 debit_authorization_number, error_type, model, originator, originator_account, purpose, recipient,
								 recipient_account, uplata, urgently, currency_id, daily_account_status_id, payment_type_id,
								 place_of_acceptance_id) 
	   value (1, 15000.00, '123456789', '97', '2017-05-09', '2017-05-09', '100018356', 'IZVRSEN_NALOG', '97',
	   		  'Mirko Mikac, Novi Sad', '333-111-333', 'Uplata poreza na promet proizvoda', 'Poreska uprava',
	   		  '222-714121843-73', false, false, 1, 1 , 4, 2);
insert into analytical_statement(id, amount, approval_authorization_number, approval_model, currency_date, date_of_receipt, 
								 debit_authorization_number, error_type, model, originator, originator_account, purpose, recipient,
								 recipient_account, uplata, urgently, currency_id, daily_account_status_id, payment_type_id,
								 place_of_acceptance_id) 
	   value (2, 13500.00, '123456789', '97', '2017-05-16', '2017-05-16', '100018356', 'IZVRSEN_NALOG', '97',
	   		  'Igre na srecu, Beograd', '222-111-333', 'Dobitag na greb greb kartici', 'Mirko Mikac, Novi Sad',
	   		  '333-111-333', true, false, 1, 2 , 4, 1);	   		  
insert into analytical_statement(id, amount, approval_authorization_number, approval_model, currency_date, date_of_receipt, 
								 debit_authorization_number, error_type, model, originator, originator_account, purpose, recipient,
								 recipient_account, uplata, urgently, currency_id, daily_account_status_id, payment_type_id,
								 place_of_acceptance_id) 
	   value (3, 1500.00, '123456789', '97', '2017-06-03', '2017-06-03', '100018356', 'IZVRSEN_NALOG', '97',
	   		  'Stefan Varajic, Doze Djerdja, Novi Sad', '666-111-333', 'Uplata za overu semestra', 'Fakultet tehnickih nauka, Trg Dositeja Obradovica 1, Novi Sad',
	   		  '840-714121843-73', false, false, 3, 3 , 4, 1);

insert into role(id, name) values (1, 'BANK_ADMIN');
insert into role(id, name) values (2, 'BANK_STAFF');
insert into role(id, name) values (3, 'CLIENT');

insert into privilege(id, name) values (1, 'readCountries');
insert into privilege(id, name) values (2, 'writeCountry');
insert into privilege(id, name) values (3, 'deleteCountry');
insert into privilege(id, name) values (4, 'editCountry');
insert into privilege(id, name) values (5, 'searchCountries');

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (2, 2);
insert into users_roles (user_id, role_id) values (3, 2);
insert into users_roles (user_id, role_id) values (4, 3);

insert into roles_privileges(role_id, privilege_id) values(1, 1);
insert into roles_privileges(role_id, privilege_id) values(1, 2);
insert into roles_privileges(role_id, privilege_id) values(1, 3);
insert into roles_privileges(role_id, privilege_id) values(1, 4);

insert into roles_privileges(role_id, privilege_id) values(2, 1);
insert into roles_privileges(role_id, privilege_id) values(2, 2);
insert into roles_privileges(role_id, privilege_id) values(2, 3);

insert into roles_privileges(role_id, privilege_id) values(3, 1);
insert into roles_privileges(role_id, privilege_id) values(3, 5);