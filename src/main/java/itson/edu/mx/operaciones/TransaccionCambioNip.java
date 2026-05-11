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

public class TransaccionCambioNip extends Transaccion {
    private String nuevoNipCifrado;

    public TransaccionCambioNip() {
        super();
    }

    public TransaccionCambioNip(int idTransaccion, Date fecha, String descripcion, int cuentaID, int atmId, String nuevoNipCifrado) {
        super(idTransaccion, fecha, descripcion, cuentaID, atmId);
        this.nuevoNipCifrado = nuevoNipCifrado;
    }

    @Override
    public boolean ejecutar() {
        System.out.println("Actualizando NIP para la cuenta " + cuentaID);
        return true; 
    }

    public String getNuevoNipCifrado() { return nuevoNipCifrado; }
    public void setNuevoNipCifrado(String nuevoNipCifrado) { this.nuevoNipCifrado = nuevoNipCifrado; }
}
  