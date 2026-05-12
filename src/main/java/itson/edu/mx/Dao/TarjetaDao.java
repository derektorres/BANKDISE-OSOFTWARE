/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.Dao;

import itson.edu.mx.db.Conexion; // Importamos tu clase de conexión
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    @Override
    public boolean validarTarjetaActivaYNip(String numeroTarjeta, String nip) {
        String sql = "SELECT estado_tarjeta FROM tarjetas WHERE numero_tarjeta = ? AND nip = ?";
        
        try (Connection conn = conexion.obtener();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroTarjeta);
            pstmt.setString(2, nip);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String estado = rs.getString("estado_tarjeta");
                return estado.equals("ACTIVA"); 
            }
            
        } catch (SQLException e) {
            System.err.println("Error al consultar la tarjeta: " + e.getMessage());
        }
        
        return false; 
    }
    
    @Override
    public boolean actualizarNip(String numeroTarjeta, String nuevoNip) {
    String sql = "UPDATE tarjetas SET nip = ? WHERE numero_tarjeta = ?";

    try (Connection conn = conexion.obtener();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, nuevoNip);
        pstmt.setString(2, numeroTarjeta);

        return pstmt.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al cambiar el NIP: " + e.getMessage());
        return false;
    }
    }
}