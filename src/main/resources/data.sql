insert into country(name) values ('Srbija');
insert into country(name) values ('Rusija');
insert into country(name) values ('Italija');
insert into country(name) values ('Peru');

insert into currency(name, official_code, domicilna, country_id) values ('Dinar', 'DIN', true, 1);
insert into currency(name, official_code, domicilna, country_id) values ('Rublja', 'RUB', true, 2);
insert into currency(name, official_code, domicilna, country_id) values ('Lira', 'LIR', true, 3);
insert into currency(name, official_code, domicilna, country_id) values ('Sol', 'SOL', true, 4);

insert into city(id, name, ptt_number, country_id) values(1, 'Beograd', '11000', 1);
insert into city(id, name, ptt_number, country_id) values(2, 'Novi sad', '21000', 1);
insert into city(id, name, ptt_number, country_id) values(3, 'Moskva', '31000', 2);
insert into city(id, name, ptt_number, country_id) values(4, 'Rim', '41000', 3);

insert into payment_type(id, payment_type_name) values(1, 'Kes');
insert into payment_type(id, payment_type_name) values(2, 'Cekovi');
insert into payment_type(id, payment_type_name) values(3, 'Kredit');

insert into bank(bank_id,bank_pib,bank_name,bank_address,bank_email,bank_web,bank_tel,bank_fax,bank_act,bank_swt,bank_tr_acc)
values(1,'123456789','Komercijalna banka','Bulvear nardonog fronta 20','komercijalna@mail.rs','www.komercijalna.com','024/555-111','444-444',true,'12345678','333-111-333');

insert into bank(bank_id,bank_pib,bank_name,bank_address,bank_email,bank_web,bank_tel,bank_fax,bank_act,bank_swt,bank_tr_acc)
values(2,'123456789','Erste banka','Bulvear nardonog fronta 100','erstelna@mail.rs','www.erste.com','024/555-111','123-123',true,'12345678','888-555-444');

insert into account(account_id,account_num,account_date,account_active,bank_bank_id) values(1,'333-111-333','2017-10-15',true,1);
insert into account(account_id,account_num,account_date,account_active,bank_bank_id) values(2,'222-111-444','2017-10-16',true,1);
insert into account(account_id,account_num,account_date,account_active,bank_bank_id) values(3,'666-111-333','2017-10-17',true,2);
insert into account(account_id,account_num,account_date,account_active,bank_bank_id) values(4,'666-111-333','2017-10-17',true,2);