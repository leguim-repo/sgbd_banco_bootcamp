package com.miguel.seatcode.bootcamp.sgdb.banco.database;

/*
	Prueba de concepto para ver si puedo unificar todas las sentencias SQL en
	una clase y luego quizas extrapolarlo al proyecto del ecommerce
	La idea principal es aglutinar toda la gestion 	SQL una sola clase para gestionar
	la base de datos, desde la conexion hasta los metodos de insercion, update, delete, test de conexion...

	Por el momento no le pongo interface para que este el pack en un solo archivo.

 */



import java.sql.*;
import java.util.*;

public class EngineSQL {
	private Connection connection;
	private Long lastIdUpdated;

	// quizas este bien almacenar las credenciales... aunque almacenar passwords... fijo que es mala practica
	private String ip;
	private String port;
	private String nameDB;
	private String userDB;
	private String passwordDB;

	public EngineSQL() {
	}

	// metodo para obtener los datos de la conexion, aunque deberia ir en el gui
	public void setConnectionCredentials() {
		Scanner reader = new Scanner(System.in);
		try {
			System.out.println("Direccion ip de conexion (127.0.0.1): ");
			this.setIp(reader.nextLine().toUpperCase());
			System.out.println("Puerto de conexion (3306): ");
			this.setPort(reader.nextLine().toUpperCase());
			System.out.println("Nombre de la DB (mazebank): ");
			this.setNameDB(reader.nextLine());
			System.out.println("Usuario (root): ");
			this.setUserDB(reader.nextLine());
			System.out.println("Password (secret1234): ");
			this.setPasswordDB(reader.nextLine());

		} catch (InputMismatchException exception) {
			System.out.println("Error al introducir datos de usuario " + exception);
			reader.next();
		}

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getNameDB() {
		return nameDB;
	}

	public void setNameDB(String nameDB) {
		this.nameDB = nameDB;
	}

	public String getUserDB() {
		return userDB;
	}

	public void setUserDB(String userDB) {
		this.userDB = userDB;
	}

	public String getPasswordDB() {
		return passwordDB;
	}

	public void setPasswordDB(String passwordDB) {
		this.passwordDB = passwordDB;
	}

	// metodo de conexion a la db pasando parametros
	public void ConnectDatabase(String ip, Integer port,String nameDB, String userDB, String passwordDB) throws ClassNotFoundException,SQLException {

		this.setIp(ip);
		this.setPort(port.toString());
		this.setNameDB(nameDB);
		this.setUserDB(userDB);
		this.setPasswordDB(passwordDB);


		Class.forName("com.mysql.cj.jdbc.Driver");

		this.connection = DriverManager.getConnection(
				"jdbc:mysql://"+ip+":"+port+"/" + nameDB +"?serverTimezone=UTC",
				userDB,
				passwordDB);

		boolean valid = connection.isValid(50000);
		System.out.println(valid ? "Conexion a la BD "+this.getNameDB()+" OK" : "Conexion a la BD "+this.getNameDB()+" FAIL");

	}

	// overload para usar los datos seteados
	public void ConnectDatabase() {
		// todo falta verificar que los datos son correctos y no estan vacios
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch (ClassNotFoundException ex) {
			System.out.println("Error al registrar el driver de MySQL: "+ ex);
		}

		try {
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://"+this.getIp()+":"+this.getPort()+"/" + this.getNameDB(),
					this.getUserDB(),
					this.getPasswordDB());

			boolean valid = connection.isValid(50000);
			System.out.println(valid ? "Conexion a la BD "+this.getNameDB()+" OK" : "Conexion a la BD "+this.getNameDB()+" FAIL");

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

	public Long updateDatabase(String SQL) throws SQLException {
		Long lastId=null;
		try {
			Statement stmt = this.connection.createStatement();
			stmt.executeUpdate(SQL,Statement.RETURN_GENERATED_KEYS);
			try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					lastId=generatedKeys.getLong(1);
					this.setLastIdUpdated(lastId);
					stmt.close();
					return lastId;
				}
				else {
					/*
					Si la sentencia sql tiene un update no va a generar nunca un id, ya que esta actualizando un id existente
					devolvemos 0
					Si la sentencia sql tiene un insert y llegamos a este else significa que algo ha funcionado mal
					lanzamos un mensaje de error
					 */
					if (SQL.toLowerCase().contains("update")) {
						return Long.valueOf(0);
					}
					else {
						System.out.println("Error al obtener el ID insertado");
						return null;
					}

				}
			}

		}
		catch (SQLException exception) {
			System.out.println("Error en InsertDatabase" + exception);
		}
		return null;
	}

	public Long getLastIdUpdated() {
		return lastIdUpdated;
	}

	public void setLastIdUpdated(Long lastIdUpdated) {
		this.lastIdUpdated = lastIdUpdated;
	}

	/* getValuesDatabase metodo para realizar cualuquier consulta de datos
		Este metodo permite obterner los datos de cualquier consulta y los mete en una lista
	*/
	public  List<Map<String, Object>>  getValuesDatabase(String sql) throws SQLException {
		List<Map<String, Object>>  datos = new ArrayList<Map<String, Object>>();

		try {
			java.sql.Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData md = rs.getMetaData();
			int columns = md.getColumnCount();

			while (rs.next()) {
				Map<String, Object> row = new HashMap<String, Object>(columns);
				for(int i = 1; i <= columns; ++i){
					row.put(md.getColumnName(i), rs.getObject(i));
				}
				datos.add(row);
			}

			stmt.close();
			rs.close();
			return datos;

		}
		catch (Exception exception) {
			System.out.println("Error en la clase:\n\t"+
					this.getClass().getName()+"\n"+
					"Metodo: \n\t"+
					exception.getStackTrace()[0].getMethodName()+"\n"+exception);
			System.exit(1);
		}
		return null;
	}

	public Cliente getCliente() throws SQLException {
		Cliente datos = null;
		java.sql.Statement stmt = this.connection.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios WHERE (nombre = 'MIGUEL')");

		while (rs.next()) {
			System.out.println(rs);
			datos = new Cliente(rs.getLong("id_usuario"),
								rs.getString("nombre"),
								rs.getString("apellido"),
								rs.getString("dni"),
								rs.getString("usuario"),
								rs.getInt("pin"),
								rs.getBoolean("activo") );
		}
		stmt.close();

		return datos;
	}

}
