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
public class TransaccionDeposito extends Transaccion {
    private double monto;

    public TransaccionDeposito() {
        super();
    }

    public TransaccionDeposito(int idTransaccion, Date fecha, String descripcion, int cuentaID, int atmId, double monto) {
        super(idTransaccion, fecha, descripcion, cuentaID, atmId);
        this.monto = monto;
    }

    @Override
    public boolean ejecutar() {
        // Lógica para depositar en la cuenta y BD
        System.out.println("Ejecutando depósito de $" + monto + " en cuenta " + cuentaID);
        return true; 
    }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
}
