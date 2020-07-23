package com.miguel.seatcode.bootcamp.sgdb.banco;

import com.miguel.seatcode.bootcamp.sgdb.banco.clases.SelectDatabase;
import com.miguel.seatcode.bootcamp.sgdb.banco.gui.InterfaceGUI;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )  throws SQLException
    {
        SelectDatabase seldb = new SelectDatabase("mazebank");
        Connection con = seldb.getConnection();
        InterfaceGUI gui = new InterfaceGUI(con);

    }
}
