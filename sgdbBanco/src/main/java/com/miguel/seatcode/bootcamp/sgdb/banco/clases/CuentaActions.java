package com.miguel.seatcode.bootcamp.sgdb.banco.clases;

import java.sql.Connection;

public interface CuentaActions {
    public String crearCuenta(float balance, int fk_id_usuario);
    public String consultarCuenta(Connection conn);
    public String ingreso(Connection conn);
    public String reintegro(Connection conn);


}
