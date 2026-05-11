/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.operaciones;

import itson.edu.mx.interfaces.ITransaccion;
import java.util.Date;

/**
 *
 * @author torre
 */
public abstract class Transaccion implements ITransaccion {
    
    protected int transaccionId;
    protected Date fecha;
    protected String descripcion;
    protected int cuentaID;
    protected int atmId;

    public Transaccion() {
        this.fecha = new Date();
    }

    public Transaccion(int transaccionId, Date fecha, String descripcion, int cuentaID, int atmId) {
        this.transaccionId = transaccionId;
        this.fecha = fecha != null ? fecha : new Date();
        this.descripcion = descripcion;
        this.cuentaID = cuentaID;
        this.atmId = atmId;
    }
    
    @Override
    public abstract boolean ejecutar();
    
    
    
    
    
    
    
    

    /**
     * @return the transaccionId
     */
    public int getTransaccionId() {
        return transaccionId;
    }

    /**
     * @param transaccionId the transaccionId to set
     */
    public void setTransaccionId(int transaccionId) {
        this.transaccionId = transaccionId;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the cuentaID
     */
    public int getCuentaID() {
        return cuentaID;
    }

    /**
     * @param cuentaID the cuentaID to set
     */
    public void setCuentaID(int cuentaID) {
        this.cuentaID = cuentaID;
    }

    /**
     * @return the atmId
     */
    public int getAtmId() {
        return atmId;
    }

    /**
     * @param atmId the atmId to set
     */
    public void setAtmId(int atmId) {
        this.atmId = atmId;
    }
    
    
    
}
