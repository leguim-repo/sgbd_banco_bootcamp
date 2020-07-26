package com.miguel.seatcode.bootcamp.sgdb.banco.gui;

import com.miguel.seatcode.bootcamp.sgdb.banco.clases.Cuenta;
import com.miguel.seatcode.bootcamp.sgdb.banco.clases.Historico;
import com.miguel.seatcode.bootcamp.sgdb.banco.database.EngineSQL;
import com.miguel.seatcode.bootcamp.sgdb.banco.database.InsertDatabase;
import com.miguel.seatcode.bootcamp.sgdb.banco.clases.Usuario;

import java.util.*;
import java.sql.Connection;
import java.sql.SQLException;


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

    static boolean bucleSioNo(String pregunta) {
        Scanner reader = new Scanner(System.in);
        String respuesta="";
        while ( !(respuesta.toLowerCase().equals("s")) && !(respuesta.toLowerCase().equals("n"))) {
            System.out.println(pregunta+" [ S ] o [ N ]:");
            respuesta=reader.nextLine();
        }
        if (respuesta.toLowerCase().equals("s") ) {
            return true;
        }
        else {
            return false;
        }
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
    Scanner reader = null;
    List<Map<String, Object>> datos,datosConsulta, datosCuenta, datosUsuario, datosHistorico = null;
    String numeroCuenta=null;
    String cantidadReintegro=null;
    String cantidadIngreso=null;
    Integer nuevoBalance=null;

        switch (option) {

            case 0:
                System.out.println("Bye bye have a nice day!");
                return true;

            case 1:
                // Crear Usuario obtener datos del usuario a traves de la consola
                // todo se deberia recodifica para usar el EngineSQL
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
                reader = new Scanner(System.in);

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
                // Inicializamos variables
                /*
                Este codigo inicialmente deberia ir en el metodo CuentaActions.ingreso
                pero ahora creo que es mejor que las clases Cuenta, Usuario, Historico fuesen clases sin metodos
                Las sentencias sql inicialmente las pense generar en esas clases, pero al crear una clase solo para
                la gestion de sql, lo de generar el sql en las clases no me parece tan interesante
                de hecho List<Map<String, Object>> deberia ser sustituido por un array de cada clase
                Aunque de esta forma si que es cierto que se entregan los datos en un formato "universal"

                */
                reader = new Scanner(System.in);
                numeroCuenta=null;
                cantidadReintegro=null;

                try {
                    System.out.println("Dame el id de cuenta en el que hacer el reintegro:");
                    numeroCuenta=reader.nextLine();
                    datosCuenta=misql.getValuesDatabase("SELECT * from cuentas WHERE( id_cuenta = '"+numeroCuenta+"')");
                    datosUsuario=misql.getValuesDatabase("SELECT * from usuarios WHERE( id_usuario = '"+((HashMap) datosCuenta.toArray()[0]).get("usuarios_id_usuario")+"')");
                    imprimirDatos("Datos Cuenta",datosCuenta);
                    imprimirDatos("Datos Usuario", datosUsuario);
                    if (bucleSioNo("Esta es la cuenta donde desea hacer el reintegro?")) {
                        //preguntar cantidad a reintegrar
                        System.out.println("Cantidad a reintegrar:");
                        cantidadReintegro=reader.nextLine();
                        String balance=((HashMap) datosCuenta.toArray()[0]).get("balance").toString();
                        nuevoBalance= Integer.valueOf(balance) - Integer.valueOf(cantidadReintegro);
                        // actualizamos los datos de la cuenta
                        misql.updateDatabase("UPDATE cuentas SET balance = "+nuevoBalance.toString()+", fecha = CURRENT_TIMESTAMP WHERE ( id_cuenta  = "+numeroCuenta+")");
                        //creo el registro en el historico
                        misql.updateDatabase("INSERT INTO historicos ( movimiento,  balance, fecha, cuentas_id_cuenta) VALUES ('Reintegro', "+nuevoBalance.toString()+", CURRENT_TIMESTAMP, "+numeroCuenta+")");
                    }
                    else {
                        System.out.println("Operacion de reintegro cancelada");
                    }
                }

                catch (InputMismatchException exception) {
                    System.out.println("Error al introducir datos de usuario " + exception);
                    reader.next();
                }

                break;

            case 4:
                // Ingreso en cuenta
                // Inicializamos variables
                reader = new Scanner(System.in);
                numeroCuenta=null;
                cantidadIngreso=null;

                try {
                    System.out.println("Dame el id de cuenta en el que hacer el ingreo:");
                    numeroCuenta=reader.nextLine();
                    datosCuenta=misql.getValuesDatabase("SELECT * from cuentas WHERE( id_cuenta = '"+numeroCuenta+"')");
                    datosUsuario=misql.getValuesDatabase("SELECT * from usuarios WHERE( id_usuario = '"+((HashMap) datosCuenta.toArray()[0]).get("usuarios_id_usuario")+"')");
                    imprimirDatos("Datos Cuenta",datosCuenta);
                    imprimirDatos("Datos Usuario", datosUsuario);
                    if (bucleSioNo("Esta es la cuenta donde desea hacer el ingreso?")) {
                        //preguntar cantidad a ingresar
                        System.out.println("Cantidad a ingresar:");
                        cantidadIngreso=reader.nextLine();
                        String balance=((HashMap) datosCuenta.toArray()[0]).get("balance").toString();
                        nuevoBalance= Integer.valueOf(balance) + Integer.valueOf(cantidadIngreso);
                        // actualizamos los datos de la cuenta
                        misql.updateDatabase("UPDATE cuentas SET balance = "+nuevoBalance.toString()+", fecha = CURRENT_TIMESTAMP WHERE ( id_cuenta  = "+numeroCuenta+")");
                        //creo el registro en el historico
                        misql.updateDatabase("INSERT INTO historicos ( movimiento,  balance, fecha, cuentas_id_cuenta) VALUES ('Ingreso', "+nuevoBalance.toString()+", CURRENT_TIMESTAMP, "+numeroCuenta+")");
                    }
                    else {
                        System.out.println("Operacion de ingreso cancelada");
                    }
                }

                catch (InputMismatchException exception) {
                    System.out.println("Error al introducir datos de usuario " + exception);
                    reader.next();
                }

                break;
            case 5:
                // Ver historico cuenta
                reader = new Scanner(System.in);
                numeroCuenta=null;
                datos=null;

                try {
                    System.out.println("\nDame el ID de la cuenta:");
                    numeroCuenta=reader.nextLine();

                } catch (InputMismatchException exception) {
                    System.out.println("Error al introducir el numero de ID de la cuenta " + exception);
                    reader.next();
                }
                5
                datos = misql.getValuesDatabase("SELECT * FROM historicos WHERE (cuentas_id_cuenta = 1 )");
                imprimirDatos("Historico de la cuenta",datos);

                break;

            case 6:
                // Ver todas los usuarios tambien le pongo cuentas para probar la funcionalidad del metodo getValuesDatabase
                datos=null;
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
                "( 5 ) Ver historico cuenta\n"+
                "( 6 ) Ver todos los usuarios y cuentas\n"+

                "\n( 0 ) Salir\n"+
                "------------------------\n" +
                "Escoja una opcion... ");

            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            flagexit = selectorOpciones(option);

        } while (flagexit != true);
    }

}
