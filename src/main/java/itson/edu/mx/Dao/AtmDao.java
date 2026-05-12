/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.Dao;

import itson.edu.mx.db.Conexion;
import itson.edu.mx.entities.AtmEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author torre
 */
public class AtmDao implements IAtmDao {
    
    private Conexion conexion;

    public AtmDao() {
        this.conexion = new Conexion(); 
    }
    
    @Override
    public AtmEntity consultarCajero(int id) {
        String sql = "SELECT id, direccion, fondos_disponibles, estado FROM atms WHERE id = ?";
        AtmEntity cajero = null;

        try (Connection conn = conexion.obtener();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cajero = new AtmEntity(
                    rs.getInt("id"), 
                    rs.getString("direccion"), 
                    rs.getDouble("fondos_disponibles"), 
                    rs.getString("estado")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error BD al consultar ATM: " + e.getMessage());
        }
        return cajero;
    }
    
    @Override
    public boolean actualizarSaldoCajero(int id, double nuevoSaldo) {
        String sql = "UPDATE atms SET fondos_disponibles = ? WHERE id = ?";
        
        try (Connection conn = conexion.obtener();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, nuevoSaldo);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error BD al actualizar ATM: " + e.getMessage());
            return false;
        }
    }
}
