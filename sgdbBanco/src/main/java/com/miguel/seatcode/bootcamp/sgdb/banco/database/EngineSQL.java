package com.miguel.seatcode.bootcamp.sgdb.banco.database;

/*
	Prueba de concepto para ver si puedo unificar todas las sentencias SQL en
	una clase y luego quizas extrapolarlo al proyecto del ecommerce
	La idea principal es aglutinar toda la gestion 	SQL una sola clase para gestionar
	la base de datos, desde la conexion hasta los metodos de insercion, update, delete, test de conexion...

	Por el momento no le pongo interface para que este el pack en un solo archivo.

 */

import java.sql.*;

public class EngineSQL {
	private Connection connection;
	private Long lastIdInserterd;

	public EngineSQL() {
	}
	// metodo de conexion a la db
	public void ConnectDatabase(String target) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException ex) {
			System.out.println("Error al registrar el driver de MySQL: "+ ex);
		}

		try {
			// todo los datos de conexion deben pasar a parametros y mas en el ecommerce
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/" + target,
					"root",
					"secret1234");

			boolean valid = connection.isValid(50000);
			System.out.println(valid ? "Conexion a la BD OK" : "Conexion a la BD FAIL");

		}
		catch (SQLException exception) {
			System.out.println("Error: "+ exception);
		}
	}

	public void closeConnection() throws SQLException {
		try {
			this.connection.close();
		}
		catch (Exception exception) {
			System.out.println("Error en la clase:\n\t"+
							   this.getClass().getName()+"\n"+
							   "Metodo: \n\t"+
							   exception.getStackTrace()[0].getMethodName());
			System.exit(1);
		}
	}

	public Connection getConnection() {
		return this.connection;
	}

	// inserta un registro en la db y obtengo el id de dicho registro
	// este id lo utilizo para encadenar los registro al crear el usuario
	// https://stackoverrun.com/es/q/384608
	// ahora me gusta mas, ya que el insert puede devolver el id del registro insertado o null si ha fallado
	// null si fall long si ok

	public Long InsertDatabase (String SQL) throws SQLException {
		Long lastId=null;
		try {
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate(SQL,Statement.RETURN_GENERATED_KEYS);
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					lastId=generatedKeys.getLong(1);
					this.setLastIdInserterd(lastId);
					stmt.close();
					return lastId;
				}
				else {
					throw new SQLException("Creating user failed, no ID obtained.");
				}
			}

		}
		catch (SQLException exception) {
			System.out.println("Error en InsertDatabase" + exception);
		}
		return null;
	}
	public Long getLastIdInserterd() {
		return lastIdInserterd;
	}
	public void setLastIdInserterd(Long lastIdInserterd) {
		this.lastIdInserterd = lastIdInserterd;
	}

	// Statement para obterner datos
	public ResultSet Statement(String sql) throws SQLException {
		try {
			System.out.println("Creating statement...");
			java.sql.Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			stmt.close();
			return rs;

		}
		catch (Exception exception) {
			System.out.println("Error en la clase:\n\t"+
					this.getClass().getName()+"\n"+
					"Metodo: \n\t"+
					exception.getStackTrace()[0].getMethodName());
			System.exit(1);
		}
		return null;
	}

}
