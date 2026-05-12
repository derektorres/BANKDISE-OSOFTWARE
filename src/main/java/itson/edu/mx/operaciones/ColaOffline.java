/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.operaciones;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author torre
 */
public class ColaOffline {
    
    private Queue<Transaccion> transaccionesPendientes;

    public ColaOffline() {
        this.transaccionesPendientes = new LinkedList<>();
    }

    public void agregarTransaccion(Transaccion t) {
        transaccionesPendientes.add(t);
    }

    public boolean hayPendientes() {
        return !transaccionesPendientes.isEmpty();
    }

    public Transaccion obtenerSiguiente() {
        return transaccionesPendientes.poll(); 
    }
    
    
}
