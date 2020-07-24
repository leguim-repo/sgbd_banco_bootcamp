use mazebank;
SELECT * FROM mazebank.usuarios;

SELECT * FROM usuarios WHERE ( `nombre`='Jason' );

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

select * from cuentas;
SELECT GETDATE();
insert into cuentas values (default, 4000, CURRENT_TIMESTAMP, 3); # cuenta lisa
insert into cuentas values (default,'0.0', CURRENT_TIMESTAMP,'7');

insert into historicos values (default,'Creacion de la Cuenta', '0.0', CURRENT_TIMESTAMP,'1');