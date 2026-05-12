/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.operaciones;

import itson.edu.mx.Dao.AtmDao;
import itson.edu.mx.Dao.IAtmDao;
import itson.edu.mx.entities.AtmEntity;

/**
 *
 * @author torre
 */
public class ATM {
    
    private AtmEntity datosCajero;
    private IAtmDao atmDao;
    private boolean tieneInternet; 
    private ColaOffline cola;

    public ATM(int idCajero) {
        this.atmDao = new AtmDao();
        this.datosCajero = atmDao.consultarCajero(idCajero);
        this.tieneInternet = true; 
        this.cola = new ColaOffline(); 
        
        if (this.datosCajero == null) {
            throw new RuntimeException("Error de conexion: El cajero no existe en la base de datos");
        }
        if (!this.datosCajero.getEstado().equalsIgnoreCase("ACTIVO")) {
            throw new RuntimeException("Este cajero se encuentra " + datosCajero.getEstado() + ". Disculpe las molestias");
        }
    }

    
    public String procesarTransaccion(Transaccion transaccion) {
        
        if (!this.tieneInternet) {
            cola.agregarTransaccion(transaccion);
            return "Operacion recibida No hay conexion temporal con el banco, pero su transaccion ha sido encolada de forma segura";
        }
        
        if (transaccion instanceof TransaccionRetiro) {
            TransaccionRetiro retiro = (TransaccionRetiro) transaccion;
            if (retiro.getMonto() > datosCajero.getFondosDisponibles()) {
                throw new RuntimeException("El cajero no cuenta con los billetes suficientes para esta cantidad");
            }
        }
        
        if (transaccion.ejecutar()) {
            if (transaccion instanceof TransaccionRetiro) {
                TransaccionRetiro retiro = (TransaccionRetiro) transaccion;
                double nuevoSaldoFisico = datosCajero.getFondosDisponibles() - retiro.getMonto();
                
                atmDao.actualizarSaldoCajero(datosCajero.getId(), nuevoSaldoFisico);
                datosCajero.setFondosDisponibles(nuevoSaldoFisico); 
            }
            return "Retiro procesado con exito Por favor tome su dinero";
        } else {
            throw new RuntimeException("La transaccion fue rechazada por el banco");
        }
    }
    
    
    public void setTieneInternet(boolean estado) {
        this.tieneInternet = estado;
        if (this.tieneInternet && cola.hayPendientes()) {
            sincronizarColaOffline();
        }
    }
    
    private void sincronizarColaOffline() {
        while (cola.hayPendientes()) {
            Transaccion t = cola.obtenerSiguiente();
            try {
                this.procesarTransaccion(t); 
            } catch (RuntimeException e) {
            }
        }
    }
    
    public AtmEntity getDatosCajero() {
        return datosCajero;
    }
    
    
}
