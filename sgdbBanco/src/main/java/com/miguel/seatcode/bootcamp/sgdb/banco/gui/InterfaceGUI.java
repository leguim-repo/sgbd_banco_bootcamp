package com.miguel.seatcode.bootcamp.sgdb.banco.gui;

import com.miguel.seatcode.bootcamp.sgdb.banco.clases.InsertDatabase;
import com.miguel.seatcode.bootcamp.sgdb.banco.clases.Usuario;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;


public class InterfaceGUI {
    private static Connection condb;

    public InterfaceGUI(Connection con) throws SQLException {
        this.condb = con;
        menu();
    }

    static boolean selectorOpciones(int option){
        switch (option) {

            case 0:
                System.out.println("Bye bye have a nice day!");
                return true;

            case 1:
                // Crear Usuario obtener datos del usuario a traves de la consola
                Usuario nuevoUsuario = new Usuario();
                String sql=nuevoUsuario.crearUsuario();
                if (sql !=null) {
                    //Tengo todos los datos ok los meto en la db
                    try {
                        InsertDatabase insert = new InsertDatabase(condb,sql);
                    }
                    catch (SQLException exception) {
                        System.out.println("Error al insertar nuevo usuario en la bd" + exception);
                        exception.printStackTrace();
                    }
                }
                else {
                    System.out.println("Algun datos es incorrecto");
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

}
