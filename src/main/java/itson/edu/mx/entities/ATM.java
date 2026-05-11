/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.entities;

import itson.edu.mx.enums.EstadoATM;

/**
 *
 * @author torre
 */
public class ATM {
    
    private int ATMId;
    private String direccion;
    private double fondosDisponible;
    private EstadoATM estadoAtm;
    
    public ATM(int ATMId, String direccion, double fondosDisponible, EstadoATM estadoAtm) {
        this.ATMId = ATMId;
        this.direccion = direccion;
        this.fondosDisponible = fondosDisponible;
        this.estadoAtm = estadoAtm;
    }
    
    
    public boolean dispensarDinero(double monto) {
        if (monto <= getFondosDisponible() && getEstadoAtm() == EstadoATM.ACTIVO) {
            this.setFondosDisponible(this.getFondosDisponible() - monto);
            return true;
        }
        return false;
    }    

    /**
     * @return the ATMId
     */
    public int getATMId() {
        return ATMId;
    }

    /**
     * @param ATMId the ATMId to set
     */
    public void setATMId(int ATMId) {
        this.ATMId = ATMId;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the fondosDisponible
     */
    public double getFondosDisponible() {
        return fondosDisponible;
    }

    /**
     * @param fondosDisponible the fondosDisponible to set
     */
    public void setFondosDisponible(double fondosDisponible) {
        this.fondosDisponible = fondosDisponible;
    }

    /**
     * @return the estadoAtm
     */
    public EstadoATM getEstadoAtm() {
        return estadoAtm;
    }

    /**
     * @param estadoAtm the estadoAtm to set
     */
    public void setEstadoAtm(EstadoATM estadoAtm) {
        this.estadoAtm = estadoAtm;
    }
    
    
}
