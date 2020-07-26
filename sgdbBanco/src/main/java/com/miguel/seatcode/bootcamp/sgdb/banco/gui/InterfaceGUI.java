package com.miguel.seatcode.bootcamp.sgdb.banco.gui;

import com.miguel.seatcode.bootcamp.sgdb.banco.clases.Cuenta;
import com.miguel.seatcode.bootcamp.sgdb.banco.clases.Historico;
import com.miguel.seatcode.bootcamp.sgdb.banco.database.EngineSQL;
import com.miguel.seatcode.bootcamp.sgdb.banco.database.InsertDatabase;
import com.miguel.seatcode.bootcamp.sgdb.banco.clases.Usuario;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;


public class InterfaceGUI {
    private static Connection condb;
    private static EngineSQL misql;

    public InterfaceGUI(Connection con) throws SQLException {
        this.condb = con;
        menu();
    }

    // overload del constructor para poder usar EngineSQL
    public InterfaceGUI(EngineSQL misql) throws SQLException {
        this.condb = misql.getConnection();
        this.misql = misql;
        menu();
    }

    static void imprimirDatos(String titulo, List<Map<String, Object>> datos) {
        System.out.println("* * * "+titulo+" * * *");
        Integer numRow=1;
        for (Map<String, Object> row:datos) {
            System.out.println("Numero registro: "+numRow+"/"+datos.size());
            for (Map.Entry<String, Object> rowEntry : row.entrySet()) {
                System.out.println(rowEntry.getKey() + " = " + rowEntry.getValue());
            }
            System.out.println("--------------------------");
            numRow+=1;
        }
        System.out.println("");
    }

    static boolean selectorOpciones(int option) throws SQLException {
    Usuario usuario;
    Cuenta cuenta;
    Historico historico;
    InsertDatabase insertDB = null;

        switch (option) {

            case 0:
                System.out.println("Bye bye have a nice day!");
                return true;

            case 1:
                // Crear Usuario obtener datos del usuario a traves de la consola
                usuario = new Usuario();
                String sqlUsuario=usuario.crearUsuario();


                if (sqlUsuario !=null) {
                    //Tengo todos los datos ok los meto en la db
                    insertDB = new InsertDatabase(condb,sqlUsuario);
                }
                else {
                    System.out.println("Algun datos es incorrecto");
                }

                //todo esto se debe modificar pq el metodo de insertar ya obtiene el id del registro insertado en la db
                //recargo los dato para obtner el id del usuario y poder crear una cuenta asignada a ese user
                usuario.consultarUsuario(condb);

                //creamos la cuenta corriente con balance de 0 €
                cuenta = new Cuenta();
                String sqlCuenta=cuenta.crearCuenta(0,usuario.getId());

                if (sqlCuenta !=null) {
                    //Tengo todos los datos ok los meto en la db
                    insertDB = new InsertDatabase(condb,sqlCuenta);
                }
                else {
                    System.out.println("Algun de la cuenta es incorrecto");
                }

                //seteo el id de la cuenta gracias al getLastIdInserted
                cuenta.setId(insertDB.getLastIdInserterd());

                //creo el registro en el historico para indicar que se ha creado la cuenta
                historico = new Historico();
                String sqlHistorico= historico.crearHistorico("Creacion de la Cuenta",cuenta.getBalance(),cuenta.getId());
                insertDB = new InsertDatabase(condb,sqlHistorico);
                historico.setId(insertDB.getLastIdInserterd());
                break;

            case 2:
                // Consultar Usuario
                usuario = new Usuario();

                Scanner reader = new Scanner(System.in);
                try {
                    System.out.println("\nDame el usuario:");
                    usuario.setNombre(reader.nextLine());

                } catch (InputMismatchException exception) {
                    System.out.println("Error al introducir datos de usuario " + exception);
                    reader.next();
                }

                usuario.consultarUsuario(condb);

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

            case 6:
                // Ver todas los usuarios tambien le pongo cuentas para probar la funcionalidad del metodo getValuesDatabase
                List<Map<String, Object>> datos;
                datos = misql.getValuesDatabase("SELECT * FROM usuarios");
                imprimirDatos("Listado de usuarios",datos);

                datos = misql.getValuesDatabase("SELECT * FROM cuentas");
                imprimirDatos("Listado de cuentas",datos);

                break;

            default:
                System.out.print("Opcion desconocida");
                break;
        }
        return false;
    }

    static void menu() throws SQLException {
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
                "( 6 ) Ver Todas las cuentas\n"+

                "\n( 0 ) Salir\n"+
                "------------------------\n" +
                "Escoja una opcion... ");

            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            flagexit = selectorOpciones(option);

        } while (flagexit != true);
    }

}
