/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.operaciones;

import java.util.Date;

/**
 *
 * @author torre
 */
public class TransaccionRetiro extends Transaccion {
    private double monto;

    public TransaccionRetiro() {
        super();
    }

    public TransaccionRetiro(int transaccionId, Date fecha, String descripcion, int cuentaID, int atmId, double monto) {
        super(transaccionId, fecha, descripcion, cuentaID, atmId);
        this.monto = monto;
    }

    @Override
    public boolean ejecutar() {
        System.out.println("Ejecutando retiro de $" + monto + " en cuenta " + cuentaID);
        return true; 
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
}
