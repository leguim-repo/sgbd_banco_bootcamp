package com.miguel.seatcode.bootcamp.sgdb.banco.clases;

import java.sql.Connection;

public interface UsuarioActions {
    public String crearUsuario();
    public String consultarUsuario(Connection conn);
}
