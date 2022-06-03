package org.service.crud;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * WEB SERVICE SOAP:
 * 
 * WSDL: Web Service  Description Language - Es el lenguaje especificado del Web Service
 * 
 * UDDI: Universal Description and Discovery Integration - Permite Buscas y Descubrir Web Service (PUBLIC, PRIVATE)
 * 
 * SOAP: Simple Object Access Protocol - Protocolo de Datos que emplea el Web Service SOAP, es de tipo XML
 * 
 * -----------------------------------------------------------------------------------------------------------------
 * 
 * REGLAS DE CREACION DE WEB SERVICE SOAP
 * 
 * 1.- Todos los Metodos deben comenzar con MINUSCULA y no se pueden REESTRUCTURAR
 * 
 * 2.- Agregar las Anotaciones: @WebService y @WebMethod
 * 
 * ------------------------------------------------------------------------------------------------------------------
 * 
 * SI SE DESEA HACER ALGUN CAMBIO, UNA VEZ YA CREADO EL WEB SERVICE
 * 
 * 1.- Stop Server Apache...
 * 
 * 2.- Add and Remove - Remover el proyecto del servidor y limpiar CLEAN
 * 
 * 3.- WEBCONTENT - WSDL (Eliminar carpeta)
 * 
 * 4.- Volver a crear el Web Service SOAP
 * 
 * ------------------------------------------------------------------------------------------------------------------
 * 
 * 										      REMOTAMENTE
 * 
 * 		Web Service SOAP: Web Service Servidor ---------> Web Service Cliente
 * 						 Class Java Service 			 JSP's 
 * 
 * NOTA: IMPORTANTE!!!! - Guardar la clase antes de generar el Web Service SOAP
 * 
 */

@WebService
public class ServiceCrudPersona {

	static Connection connection = null;
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
	
	@WebMethod
	public static void connectDataBaseOracle()throws IOException, SQLException{
		try {
			Class.forName(driver).newInstance();
			System.out.println("Cargo Drive: ojdbc6.jar");
		} catch (Exception e) {
			System.out.println("Exception driver: " + e.getMessage());
		}
		try {
			connection = DriverManager.getConnection(URL, "System","03091999");
			System.out.println("Conexion exitosa: ORACLELIG");
		} catch (Exception e) {
			System.out.println("Exception connection: " + e.getMessage());
		}
	}
	
	@WebMethod
	public static String altaPersonabatch(int id, String nombre, String apepat, String tel) throws SQLException, IOException {
		connectDataBaseOracle();
		
		try {
			String sql = "INSERT INTO PERSONABATCH (ID, NOMBRE, APEPAT, TEL) VALUES (?,?,?,?)";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, nombre);
			ps.setString(3, apepat);
			ps.setString(4, tel);
			ps.execute();
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		
		return "REALIZÓ CORRECTAMENTE EL ALTA DE LA PERSONA";
	}
	
	@WebMethod
	public static String modificarPersonabatch(String nombre, String apepat, String tel, int id) throws SQLException, IOException{
		connectDataBaseOracle();
		
		try {
			
			String sql = "UPDATE PERSONABATCH SET NOMBRE = ?, APEPAT = ?, TEL = ? WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, apepat);
			ps.setString(3, tel);
			ps.setInt(4, id);
			ps.execute();
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		
		return "REALIZÓ CORRECTAMENTE LA MODIFICACION DE PERSONA";
	}
	
	@WebMethod
	public static String eliminarPersonabatch(int id) throws SQLException, IOException{
		connectDataBaseOracle();
		
		try {
			
			String sql = "DELETE FROM PERSONABATCH WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		
		return "REALIZÓ CORRECTAMENTE LA ELIMINACIÓN DE PERSONA";
	}
	
	/*
	public static void consultaGeneralPersonabatch(){
		try {
			connectDataBaseOracle();
			
			String sql = "SELECT * FROM PERSONABATCH";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				System.out.println(resultSet.getInt("id")
				+ ", " + resultSet.getString("nombre")
				+ ", " + resultSet.getString("apepat")
				+ ", " + resultSet.getString("tel"));
			}
			System.out.println("Realizó exitosamente la CONSULTA GENERAL");
			
			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
	*/
	
	@WebMethod
	public static String consultaIdPersonabatch(int id) throws SQLException, IOException {

		connectDataBaseOracle();
		String nombre = null;
		String apepat = null;
		String tel = null;
		
		try {
			String sql = "SELECT * FROM PERSONABATCH WHERE ID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,  id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				nombre = rs.getString("nombre");
				apepat = rs.getString("apepat");
				tel = rs.getString("tel");	
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		
		return "CONSULTA: " + id + ", " + nombre + ", " + apepat + ", "  + tel;
	}
	
	
	/*
	public static void invocarProcedimientoProc( int id, String name) throws IOException, SQLException {
		try {
			connectDataBaseOracle();
			
			String sql = "CALL proc(?,?)";
			CallableStatement cs = connection.prepareCall(sql);
			cs.setInt(1, id);
			cs.setString(2, name);
			cs.execute();
			System.out.println("EJECUTÓ CORRECTAMENTE EL PROCEDURE PROC");
					
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
	*/
	
	/*
	public static void main(String[] args) throws IOException, SQLException {
		//connectDataBaseOracle();
		//insertEstudiante(1002, "JUAN", "MORENO", "7331254847");
		//modificarEstudiante("JUAN", "MARTINEZ", "758489544", 1001);
		//eliminarEstudiante(1001);
		//consultaGeneralEstudiante();
		//consultaIndividualEstudiante(1000);
		//invocarProcedimientoProc(14, "EDO. MEX");
		
	}
	*/
}
