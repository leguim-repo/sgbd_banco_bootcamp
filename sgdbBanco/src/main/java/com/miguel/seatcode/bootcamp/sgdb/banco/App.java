package com.miguel.seatcode.bootcamp.sgdb.banco;

import com.miguel.seatcode.bootcamp.sgdb.banco.database.ConnectDatabase;
import com.miguel.seatcode.bootcamp.sgdb.banco.database.EngineSQL;
import com.miguel.seatcode.bootcamp.sgdb.banco.gui.InterfaceGUI;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )  throws SQLException
    {
        EngineSQL misql = new EngineSQL();
        //misql.setConnectionCredentials();
        misql.ConnectDatabase("127.0.0.1",3306,"mazebank","root","secret1234");

        InterfaceGUI gui = new InterfaceGUI(misql);

        //ConnectDatabase seldb = new ConnectDatabase("mazebank");
        //InterfaceGUI gui = new InterfaceGUI(seldb.getConnection());

    }
}