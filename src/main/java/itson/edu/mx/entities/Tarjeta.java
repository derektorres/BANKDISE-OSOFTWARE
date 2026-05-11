/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.entities;

import itson.edu.mx.enums.EstadoTarjeta;
import java.util.Date;
        
/**
 *
 * @author torre
 */
public class Tarjeta {

    private int tarjetaId;
    private String numeroTarjeta;
    private String nipCifrado;
    private Date fechaVencimiento;
    private EstadoTarjeta estadoTarjeta;
    
    public Tarjeta() {}

    public Tarjeta(int tarjetaId, String numeroTarjeta, String nipCifrado, Date fechaVencimiento, EstadoTarjeta estadoTarjeta) {
        this.tarjetaId = tarjetaId;
        this.numeroTarjeta = numeroTarjeta;
        this.nipCifrado = nipCifrado;
        this.fechaVencimiento = fechaVencimiento;
        this.estadoTarjeta = estadoTarjeta;
    }
    
    public void bloquear() {
        this.estadoTarjeta = EstadoTarjeta.BLOQUEADA;
    }

    public void desbloquear() {
        this.estadoTarjeta = EstadoTarjeta.ACTIVA;
    }

    public boolean estaVencida(Date fechaActual) {
        return fechaActual.after(this.fechaVencimiento);
    }
    
    public boolean verificarNip(String nipIngresado) {
        return this.nipCifrado.equals(nipIngresado) && this.estadoTarjeta == EstadoTarjeta.ACTIVA;
    }
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @return the tarjetaId
     */
    public int getTarjetaId() {
        return tarjetaId;
    }

    /**
     * @param tarjetaId the tarjetaId to set
     */
    public void setTarjetaId(int tarjetaId) {
        this.tarjetaId = tarjetaId;
    }

    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the nipCifrado
     */
    public String getNipCifrado() {
        return nipCifrado;
    }

    /**
     * @param nipCifrado the nipCifrado to set
     */
    public void setNipCifrado(String nipCifrado) {
        this.nipCifrado = nipCifrado;
    }

    /**
     * @return the fechaVencimiento
     */
    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * @param fechaVencimiento the fechaVencimiento to set
     */
    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * @return the estadoTarjeta
     */
    public EstadoTarjeta getEstadoTarjeta() {
        return estadoTarjeta;
    }

    /**
     * @param estadoTarjeta the estadoTarjeta to set
     */
    public void setEstadoTarjeta(EstadoTarjeta estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }
    
    
}
