/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.Dao;

import itson.edu.mx.db.Conexion;
import itson.edu.mx.entities.Cuenta;
import itson.edu.mx.enums.EstadoCuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author torre
 */
public class CuentaDao implements ICuentaDao {

    private Conexion conexion;

    public CuentaDao() {
        this.conexion = new Conexion();
    }

    @Override
    public Cuenta autenticar(String numeroCuenta, String password) {
       
        String sql = "SELECT cu.*, cl.nombre AS nombre_cliente " +
                     "FROM cuentas cu " +
                     "INNER JOIN clientes cl ON cu.cliente_id = cl.id " +
                     "WHERE cu.numero_cuenta = ? AND cu.password = ?";
                     
        Cuenta cuentaEncontrada = null;

        try (Connection conn = conexion.obtener();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroCuenta);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                cuentaEncontrada = new Cuenta();
                cuentaEncontrada.setCuentaId(rs.getInt("id"));
                cuentaEncontrada.setNumeroCuenta(rs.getString("numero_cuenta"));
                cuentaEncontrada.setSaldoDisponible(rs.getDouble("saldo"));
                String estadoStr = rs.getString("estado");
                cuentaEncontrada.setEstadoCuenta(EstadoCuenta.valueOf(estadoStr));
                cuentaEncontrada.setNombreCliente(rs.getString("nombre_cliente"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al autenticar en la BD: " + e.getMessage());
        }
        
        return cuentaEncontrada;
    }
    
    @Override
    public boolean actualizarSaldo(int cuentaId, double nuevoSaldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";
        
        try (Connection conn = conexion.obtener();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setDouble(1, nuevoSaldo);
                pstmt.setInt(2, cuentaId);

                int filasAfectadas = pstmt.executeUpdate();
                return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar el saldo en la BD: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public Cuenta buscarCuentaPorNumero(String numeroCuenta) {
        String sql = "SELECT cu.*, cl.nombre AS nombre_cliente " +
                     "FROM cuentas cu " +
                     "INNER JOIN clientes cl ON cu.cliente_id = cl.id " +
                     "WHERE cu.numero_cuenta = ?";
                     
        Cuenta cuentaEncontrada = null;

        try (Connection conn = conexion.obtener(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, numeroCuenta);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                cuentaEncontrada = new Cuenta();
                cuentaEncontrada.setCuentaId(rs.getInt("id"));
                cuentaEncontrada.setNumeroCuenta(rs.getString("numero_cuenta"));
                cuentaEncontrada.setSaldoDisponible(rs.getDouble("saldo"));
                String estadoStr = rs.getString("estado");
                cuentaEncontrada.setEstadoCuenta(EstadoCuenta.valueOf(estadoStr));
                cuentaEncontrada.setNombreCliente(rs.getString("nombre_cliente"));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar la cuenta destino: " + e.getMessage());
        }
        
        return cuentaEncontrada;
    }
}
