package com.miguel.seatcode.bootcamp.sgdb.banco.database;

public class Cliente {
    public Long id;
    public String nombre;
    public String apellido;
    public String dni;
    public String usuario;
    public Integer pin;
    public Boolean activo;

    public Cliente(Long id, String nombre, String apellido, String dni, String usuario, Integer pin, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.usuario = usuario;
        this.pin = pin;
        this.activo = activo;
    }

    public Cliente(String nombre, String apellido, String dni, String usuario, Integer pin, Boolean activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.usuario = usuario;
        this.pin = pin;
        this.activo = activo;
    }

    public String insCliente(){
        String sql;
        sql = "insert into usuarios values (default,\'"+
                this.nombre+"\'," +
                "\'"+this.apellido+"\'," +
                "\'"+this.dni+"\'," +
                "\'"+this.usuario+"\'," +
                this.pin+"," +
                this.activo+")";
        return sql;
    }

    @Override
    public String toString() {
        return "Cliente{" +
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
