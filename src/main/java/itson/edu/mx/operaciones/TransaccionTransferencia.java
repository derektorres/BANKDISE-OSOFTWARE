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
public class TransaccionTransferencia extends Transaccion {
    private double monto;
    private int cuentaDestinoId;

    public TransaccionTransferencia() {
        super();
    }

    public TransaccionTransferencia(int idTransaccion, Date fecha, String descripcion, int cuentaID, int atmId, double monto, int cuentaDestinoId) {
        super(idTransaccion, fecha, descripcion, cuentaID, atmId);
        this.monto = monto;
        this.cuentaDestinoId = cuentaDestinoId;
    }

    @Override
    public boolean ejecutar() {
        System.out.println("Transfiriendo $" + monto + " de " + cuentaID + " a " + cuentaDestinoId);
        return true; 
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public int getCuentaDestinoId() { return cuentaDestinoId; }
    public void setCuentaDestinoId(int cuentaDestinoId) { this.cuentaDestinoId = cuentaDestinoId; }
}