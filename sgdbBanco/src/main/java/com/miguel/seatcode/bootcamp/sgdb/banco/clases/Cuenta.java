package com.miguel.seatcode.bootcamp.sgdb.banco.clases;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Cuenta implements CuentaActions {
    private Long id;
    private float balance;
    private String fecha;
    private Long fk_id_usuario;


    public Cuenta() {
    }

    @Override
    public String crearCuenta(float balance, Long fk_id_usuario) {
        this.setBalance(balance);
        this.setFk_id_usuario(fk_id_usuario);

        String sql=null;

        sql = "insert into cuentas values (default," +
                "\'"+this.getBalance()+"\', " +
                "CURRENT_TIMESTAMP," +
                "\'"+this.getFk_id_usuario()+"\')";

        return sql;
    }

    @Override
    public String consultarCuenta(Connection conn) {

        return "ok";
    }

    @Override
    public String ingreso(Connection conn) {
        return null;
    }

    @Override
    public String reintegro(Connection conn) {
        // id de cuenta
        // cantidad de dinero a reintegrar
        // crear registro del historico
        // actualizar el saldo en la cuenta ( saldo = saldo - reintegro )


        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getFk_id_usuario() {
        return fk_id_usuario;
    }

    public void setFk_id_usuario(Long fk_id_usuario) {
        this.fk_id_usuario = fk_id_usuario;
    }


    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", balance=" + balance +
                ", fecha='" + fecha + '\'' +
                ", fk_id_usuario=" + fk_id_usuario +
                '}';
    }
}
