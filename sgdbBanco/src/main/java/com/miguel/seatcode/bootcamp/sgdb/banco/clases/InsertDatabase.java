package com.miguel.seatcode.bootcamp.sgdb.banco.clases;


import java.sql.Connection;
import java.sql.SQLException;

public class InsertDatabase {
    public InsertDatabase (Connection conn,String SQL) throws SQLException {
        try {
            java.sql.Statement stmt = conn.createStatement();
            /*
            String SQL = "INSERT INTO Employees " +
                    "VALUES (108, 20, 'save', 'point')";

             */
            stmt.executeUpdate(SQL);
        }
        catch (SQLException exception) {
            System.out.println("Error en InsertDatabase" + exception);
        }

    }
}
