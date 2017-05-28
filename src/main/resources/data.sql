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