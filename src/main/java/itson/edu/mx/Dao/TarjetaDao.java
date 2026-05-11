/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.Dao;

import itson.edu.mx.db.Conexion; // Importamos tu clase de conexión
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author torre
 */
public class TarjetaDao implements ITarjetaDao {
    
    private Conexion conexion;

    public TarjetaDao() {
        this.conexion = new Conexion(); 
    }

    @Override
    public boolean bloquearTarjeta(String numeroTarjeta) {
        
        String sql = "UPDATE tarjetas SET estado_tarjeta = 'BLOQUEADA' WHERE numero_tarjeta = ?";
        
        try (Connection conn = conexion.obtener(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroTarjeta);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al intentar bloquear la tarjeta en la BD: " + e.getMessage());
            return false;
        }
    }
}