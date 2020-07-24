package com.miguel.seatcode.bootcamp.sgdb.banco.clases;


import java.sql.Connection;
import java.sql.SQLException;

public class InsertDatabase {
    public Integer getLastIdInserterd() {
        return lastIdInserterd;
    }

    public void setLastIdInserterd(Integer lastIdInserterd) {
        this.lastIdInserterd = lastIdInserterd;
    }

    private Integer lastIdInserterd;

    public  InsertDatabase (Connection conn,String SQL) throws SQLException {
    Integer lastId=null;
        try {
            java.sql.Statement stmt = conn.createStatement();
            lastId=stmt.executeUpdate(SQL);
            this.setLastIdInserterd(lastId);
            //System.out.println(stmt.toString());
        }
        catch (SQLException exception) {
            System.out.println("Error en InsertDatabase" + exception);
        }
    }
}
