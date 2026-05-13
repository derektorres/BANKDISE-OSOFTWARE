/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.entities;

import itson.edu.mx.enums.EstadoCuenta;

/**
 *
 * @author torre
 */
public class Cuenta {

    private int cuentaId;
    private String numeroCuenta;
    private String password;
    private double saldoDisponible;
    private double limiteDiarioRetiro;
    private EstadoCuenta estadoCuenta;
    private String nombreCliente;
    
    public Cuenta() {}

    public Cuenta(int cuentaId, String numeroCuenta,String password, double saldoDisponible, double limiteDiarioRetiro, EstadoCuenta estadoCuenta) {
        this.cuentaId = cuentaId;
        this.numeroCuenta = numeroCuenta;
        this.password = password;
        this.saldoDisponible = saldoDisponible;
        this.limiteDiarioRetiro = limiteDiarioRetiro;
        this.estadoCuenta = estadoCuenta;
    }
    
    public boolean estaActiva() {
        return this.estadoCuenta == EstadoCuenta.ACTIVA;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the cuentaId
     */
    public int getCuentaId() {
        return cuentaId;
    }

    /**
     * @param cuentaId the cuentaId to set
     */
    public void setCuentaId(int cuentaId) {
        this.cuentaId = cuentaId;
    }

    /**
     * @return the numeroCuenta
     */
    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    /**
     * @param numeroCuenta the numeroCuenta to set
     */
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    /**
     * @return the saldoDisponible
     */
    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    /**
     * @param saldoDisponible the saldoDisponible to set
     */
    public void setSaldoDisponible(double saldoDisponible) {
        this.saldoDisponible = saldoDisponible;
    }

    /**
     * @return the limiteDiarioRetiro
     */
    public double getLimiteDiarioRetiro() {
        return limiteDiarioRetiro;
    }

    /**
     * @param limiteDiarioRetiro the limiteDiarioRetiro to set
     */
    public void setLimiteDiarioRetiro(double limiteDiarioRetiro) {
        this.limiteDiarioRetiro = limiteDiarioRetiro;
    }

    /**
     * @return the estadoCuenta
     */
    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    /**
     * @param estadoCuenta the estadoCuenta to set
     */
    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    
    
    
    
    
}
