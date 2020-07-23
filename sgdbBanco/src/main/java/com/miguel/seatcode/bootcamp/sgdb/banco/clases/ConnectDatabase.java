package com.miguel.seatcode.bootcamp.sgdb.banco.clases;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {
    Connection connection;
    public ConnectDatabase(String target) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException ex) {
            System.out.println("Error al registrar el driver de MySQL: "+ ex);
        }

        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/" + target,
                    "root",
                    "secret1234");

            boolean valid = connection.isValid(50000);
            System.out.println(valid ? "Conexion a la BD OK" : "Conexion a la BD FAIL");

        }
        catch (SQLException exception) {
            System.out.println("Error: "+ exception);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}

