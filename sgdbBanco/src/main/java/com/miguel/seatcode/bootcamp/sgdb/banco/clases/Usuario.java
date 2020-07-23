package com.miguel.seatcode.bootcamp.sgdb.banco.clases;



import java.util.InputMismatchException;
import java.util.Scanner;

public class Usuario {
    private String nombre;
    private String apellido;
    private String dni;
    private String usuario;
    private Integer pin;
    private Boolean activo;

    public Usuario(){

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

    //devuelvo la sentencia sql lista para ser usada
    //falta control de datos para verificar que son validos
    public String crearUsuario() {
        String sql=null;
        Scanner reader = new Scanner(System.in);
        System.out.println("\nCrear nuevo usuario:");
        try {
            System.out.println("Nombre de usuario");
            this.setNombre(reader.nextLine());

            System.out.println("Apellido de usuario");
            this.setApellido(reader.nextLine());

            System.out.println("DNI de usuario:");
            this.setDni(reader.nextLine());

            this.setUsuario(this.getNombre().substring(0,2).toLowerCase()+this.getApellido().substring(0,2).toLowerCase());
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
}
