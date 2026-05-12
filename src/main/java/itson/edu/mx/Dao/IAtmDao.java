/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package itson.edu.mx.Dao;

import itson.edu.mx.entities.AtmEntity;

/**
 *
 * @author torre
 */
public interface IAtmDao {
    
    AtmEntity consultarCajero(int id);
    boolean actualizarSaldoCajero(int id, double nuevoSaldo);
}
