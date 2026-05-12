/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.entities;

/**
 *
 * @author torre
 */
public class AtmEntity {
    
    private int id;
    private String direccion;
    private double fondosDisponibles;
    private String estado;

    public AtmEntity() {}

    public AtmEntity(int id, String direccion, double fondosDisponibles, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.fondosDisponibles = fondosDisponibles;
        this.estado = estado;
    }
    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the fondosDisponibles
     */
    public double getFondosDisponibles() {
        return fondosDisponibles;
    }

    /**
     * @param fondosDisponibles the fondosDisponibles to set
     */
    public void setFondosDisponibles(double fondosDisponibles) {
        this.fondosDisponibles = fondosDisponibles;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
    
    
}
