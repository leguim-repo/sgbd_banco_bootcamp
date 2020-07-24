package com.miguel.seatcode.bootcamp.sgdb.banco.clases;

import java.sql.Connection;

public interface CuentaActions {
    public String crearCuenta(float balance, Long fk_id_usuario);
    public String consultarCuenta(Connection conn);
    public String ingreso(Connection conn);
    public String reintegro(Connection conn);


}
