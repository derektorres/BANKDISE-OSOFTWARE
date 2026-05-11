/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author torre
 */
public class Conexion {
    
    private static final String URL = "jdbc:mysql://localhost:3306/db_banksoftware";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
    
    public static Connection obtener() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con éxito.");
            
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el conector JDBC.");
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
    
}
