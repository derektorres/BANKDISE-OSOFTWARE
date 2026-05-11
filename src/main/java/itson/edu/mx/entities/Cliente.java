/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.entities;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author torre
 */
public class Cliente {

    
    private int clienteId;
    private String nombre;
    private String datosContacto;
    private List<Cuenta> cuentas;
    
    public Cliente() {
        this.cuentas = new ArrayList<>();
    }

    public Cliente(int clienteId, String nombre, String datosContacto) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.datosContacto = datosContacto;
        this.cuentas = new ArrayList<>();
    }

    public List<Cuenta> obtenerCuentas() {
        return this.cuentas;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * @return the clienteId
     */
    public int getClienteId() {return clienteId;}
    /**
     * @param clienteId the clienteId to set
     */
    public void setClienteId(int clienteId) {this.clienteId = clienteId;}

    /**
     * @return the nombre
     */
    public String getNombre() {return nombre;}

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {this.nombre = nombre;}

    /**
     * @return the datosContacto
     */
    public String getDatosContacto() {return datosContacto;}

    /**
     * @param datosContacto the datosContacto to set
     */
    public void setDatosContacto(String datosContacto) {this.datosContacto = datosContacto;}

    /**
     * @return the cuentas
     */
    public List<Cuenta> getCuentas() {return cuentas;}

    /**
     * @param cuentas the cuentas to set
     */
    public void setCuentas(List<Cuenta> cuentas) {this.cuentas = cuentas;}
    
    
    
    
}
