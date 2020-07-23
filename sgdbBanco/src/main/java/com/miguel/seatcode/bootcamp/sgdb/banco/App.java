package com.miguel.seatcode.bootcamp.sgdb.banco;

import com.miguel.seatcode.bootcamp.sgdb.banco.clases.ConnectDatabase;
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
        ConnectDatabase seldb = new ConnectDatabase("mazebank");
        InterfaceGUI gui = new InterfaceGUI(seldb.getConnection());

    }
}
