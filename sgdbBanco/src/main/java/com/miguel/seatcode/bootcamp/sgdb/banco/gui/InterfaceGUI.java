package com.miguel.seatcode.bootcamp.sgdb.banco.gui;

import com.miguel.seatcode.bootcamp.sgdb.banco.clases.InsertDatabase;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class InterfaceGUI {
    private static Connection condb;

    static void getUser() {

    }

    static boolean selectorOpciones(int option){
        switch (option) {

            case 0:
                System.out.println("Bye bye have a nice day!");
                return true;

            case 1:
                // Crear Usuario
                System.out.println("\n");
                    try {
                        InsertDatabase insert = new InsertDatabase(condb,"use mazebank");
                    }
                    catch (SQLException exception) {
                        exception.printStackTrace();
                    }

                break;

            case 2:
                // Consultar Usuario
                break;

            case 3:
                // Reintegro en cuenta
                break;

            case 4:
                // Ingreso en cuenta
                break;

            case 5:
                // Ver Movimientos cuenta
                break;


            default:
                System.out.print("Opcion desconocida");
                break;
        }
        return false;
    }

    static void menu() {
        boolean flagexit = false;

        do {
            System.out.println(
                "* * Maze Bank * *\n"+
                "------------------------\n"+
                "( 1 ) Crear Usuario\n"+
                "( 2 ) Consultar Usuario\n"+
                "( 3 ) Reintegro en cuenta\n"+
                "( 4 ) Ingreso en cuenta\n"+
                "( 5 ) Ver Movimientos cuenta\n"+

                "\n( 0 ) Salir\n"+
                "------------------------\n" +
                "Escoja una opcion... ");

            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            flagexit = selectorOpciones(option);

        } while (flagexit != true);
    }
    public InterfaceGUI(Connection con) throws SQLException {
        this.condb = con;
        menu();
    }
}
