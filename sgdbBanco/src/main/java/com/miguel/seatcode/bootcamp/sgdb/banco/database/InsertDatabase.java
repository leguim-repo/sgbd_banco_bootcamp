package com.miguel.seatcode.bootcamp.sgdb.banco.database;


import java.sql.*;

public class InsertDatabase {
    private Long lastIdInserterd;


    public Long getLastIdInserterd() {
        return lastIdInserterd;
    }

    public void setLastIdInserterd(Long lastIdInserterd) {
        this.lastIdInserterd = lastIdInserterd;
    }


    public InsertDatabase (Connection conn,String SQL) throws SQLException {
    Long lastId=null;
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(SQL,Statement.RETURN_GENERATED_KEYS);
            //https://stackoverrun.com/es/q/384608
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    lastId=generatedKeys.getLong(1);
                    this.setLastIdInserterd(lastId);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        }
        catch (SQLException exception) {
            System.out.println("Error en InsertDatabase" + exception);
        }
    }
}
