use mazebank;
SELECT * FROM mazebank.usuarios;

# insert usuarios
insert into usuarios values (default,'Miguel', 'Crown','12345689Z','miguel','1234',true);
insert into usuarios values (default,'Jose', 'Perez','3411231D','jose','1234',true);
insert into usuarios values (default,'Lisa', 'Martin','45678712X','lisa','1234',true);

select * from cuentas;
#insert cuentas
insert into cuentas values (default, 1000,"2020-07-23",1);  # cuenta miguel
insert into cuentas values (default, 2000,"2020-07-23",2); # cuenta jose
insert into cuentas values (default, 2000,"2020-07-23",2); # cuenta jose
insert into cuentas values (default, 1000,"2020-07-23",3); # cuenta lisa
