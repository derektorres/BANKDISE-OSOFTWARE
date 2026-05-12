/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.operaciones;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.ICuentaDao;
import itson.edu.mx.Dao.ITarjetaDao;
import itson.edu.mx.Dao.TarjetaDao;
import itson.edu.mx.entities.Cuenta;
import java.util.Date;

/**
 *
 * @author torre
 */
public class TransaccionRetiro extends Transaccion {
    
    private double monto;
    private Cuenta cuenta; 
    private String numeroTarjeta; 
    private String nip;          
    private ICuentaDao cuentaDao;
    private ITarjetaDao tarjetaDao; 

    public TransaccionRetiro() {
        super();
        this.cuentaDao = new CuentaDao();
        this.tarjetaDao = new TarjetaDao();
    }

    // Constructor actualizado con tarjeta y NIP
    public TransaccionRetiro(int transaccionId, Date fecha, String descripcion, int atmId, double monto, Cuenta cuenta, String numeroTarjeta, String nip) {
        super(transaccionId, fecha, descripcion, cuenta.getCuentaId(), atmId);
        this.monto = monto;
        this.cuenta = cuenta;
        this.numeroTarjeta = numeroTarjeta;
        this.nip = nip;
        
        this.cuentaDao = new CuentaDao();
        this.tarjetaDao = new TarjetaDao();
    }

    @Override
    public boolean ejecutar() {
        
        if (!tarjetaDao.validarTarjetaActivaYNip(numeroTarjeta, nip)) {
            throw new RuntimeException("Operacion rechazada: NIP incorrecto o la Tarjeta se encuentra bloqueada/inactiva.");
        }

        if (!cuenta.estaActiva()) {
            throw new RuntimeException("Operacion rechazada: La cuenta bancaria se encuentra bloqueada.");
        }

        if (monto > cuenta.getLimiteDiarioRetiro()) {
            throw new RuntimeException("Operacion rechazada: El monto supera su limite diario de $" + cuenta.getLimiteDiarioRetiro());
        }

        if (monto > cuenta.getSaldoDisponible()) {
            throw new RuntimeException("Operacion rechazada: Fondos insuficientes. Su saldo actual es: $" + cuenta.getSaldoDisponible());
        }

        double nuevoSaldo = cuenta.getSaldoDisponible() - monto;

        boolean actualizadoEnBD = cuentaDao.actualizarSaldo(cuentaID, nuevoSaldo);

        if (actualizadoEnBD) {
            cuenta.setSaldoDisponible(nuevoSaldo);
            return true; 
        } else {
            throw new RuntimeException("Error del sistema: No se pudo comunicar con el banco central.");
        }
    }

    
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }
    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }
    public String getNip() { return nip; }
    public void setNip(String nip) { this.nip = nip; }
}
