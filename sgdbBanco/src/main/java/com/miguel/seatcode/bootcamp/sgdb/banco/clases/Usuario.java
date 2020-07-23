package com.miguel.seatcode.bootcamp.sgdb.banco.clases;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Usuario implements UsuarioActions{
    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private String usuario;
    private Integer pin;
    private Boolean activo;

    public Usuario(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    //metodo para crear un usuario en la bd
    //devuelvo la sentencia sql lista para ser usada
    //falta control de datos para verificar que son validos
    public String crearUsuario() {
        String sql=null;
        Scanner reader = new Scanner(System.in);
        System.out.println("\nCrear nuevo usuario:");
        try {
            System.out.println("Nombre de usuario");
            this.setNombre(reader.nextLine().toUpperCase());

            System.out.println("Apellido de usuario");
            this.setApellido(reader.nextLine().toUpperCase());

            System.out.println("DNI de usuario:");
            this.setDni(reader.nextLine());

            this.setUsuario(this.getNombre().substring(0,2)+this.getApellido().substring(0,2));
            this.setPin(1234);

        } catch (InputMismatchException exception) {
            System.out.println("Error al introducir datos de usuario " + exception);
            reader.next();
            return null;
        }
        // insert into usuarios values (default,'Miguel', 'Crown','12345689Z','miguel','1234',true);

        sql = "insert into usuarios values (default,\'"+this.getNombre()+"\', " +
              "\'"+this.getApellido()+"\'," +
              "\'"+this.getDni()+"\'," +
              "\'"+this.getUsuario()+"\'," +
              "\'"+this.getPin()+"\',true)";
        return sql;
    }

    @Override
    //metodo para consultar un usuario
    public String consultarUsuario(Connection conn) {
        Scanner reader = new Scanner(System.in);
        try {
            System.out.println("\nDame el usuario:");
            this.setNombre(reader.nextLine());

        } catch (InputMismatchException exception) {
            System.out.println("Error al introducir datos de usuario " + exception);
            reader.next();
            return null;
        }

        try {
            java.sql.Statement stmt = conn.createStatement();
            String SQL = "SELECT * FROM usuarios WHERE ( `nombre`= \'"+this.getNombre()+"\' )";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                this.setId(rs.getInt("id_usuario"));
                this.setNombre(rs.getString("nombre"));
                this.setApellido(rs.getString("apellido"));
                this.setDni(rs.getString("dni"));
                this.setUsuario(rs.getString("usuario"));
                this.setPin(rs.getInt("pin"));
                this.setActivo(rs.getBoolean("activo"));
                System.out.println(this.toString());
                //System.out.println("ID: " + id + ", Age: " + age + ", First: " + first + ", Last: " + last);
            }
            stmt.close();

        } catch (SQLException exception) {
            System.out.println("Error al rollback and save point" + exception);
        }

        return "ok";
    }


    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", usuario='" + usuario + '\'' +
                ", pin=" + pin +
                ", activo=" + activo +
                '}';
    }
}
