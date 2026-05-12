/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.edu.mx.Dao;

import itson.edu.mx.entities.Cuenta;

/**
 *
 * @author torre
 */
public interface ICuentaDao {
    
    Cuenta autenticar(String numeroCuenta, String password);
    boolean actualizarSaldo(int cuentaId, double nuevoSaldo);
    Cuenta buscarCuentaPorNumero(String numeroCuenta);
    
}
