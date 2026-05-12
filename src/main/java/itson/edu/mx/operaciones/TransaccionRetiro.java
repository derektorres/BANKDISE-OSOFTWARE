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
            throw new RuntimeException("Operacion rechazada: NIP incorrecto o la Tarjeta se encuentra bloqueada/inactiva");
        }

        if (!cuenta.estaActiva()) {
            throw new RuntimeException("Operacion rechazada: La cuenta bancaria se encuentra bloqueada");
        }

        if (getMonto() > getCuenta().getLimiteDiarioRetiro()) {
            throw new RuntimeException("Operacion rechazada: El monto supera su limite diario de $" + getCuenta().getLimiteDiarioRetiro());
        }

        if (getMonto() > getCuenta().getSaldoDisponible()) {
            throw new RuntimeException("Operacion rechazada: Fondos insuficientes su saldo actual es: $" + getCuenta().getSaldoDisponible());
        }

        double nuevoSaldo = getCuenta().getSaldoDisponible() - getMonto();

        boolean actualizadoEnBD = getCuentaDao().actualizarSaldo(cuentaID, nuevoSaldo);

        if (actualizadoEnBD) {
            getCuenta().setSaldoDisponible(nuevoSaldo);
            return true; 
        } else {
            throw new RuntimeException("Error del sistema: No se pudo comunicar con el banco central");
        }
    }
    

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }

    /**
     * @return the cuenta
     */
    public Cuenta getCuenta() {
        return cuenta;
    }

    /**
     * @param cuenta the cuenta to set
     */
    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the nip
     */
    public String getNip() {
        return nip;
    }

    /**
     * @param nip the nip to set
     */
    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     * @return the cuentaDao
     */
    public ICuentaDao getCuentaDao() {
        return cuentaDao;
    }

    /**
     * @param cuentaDao the cuentaDao to set
     */
    public void setCuentaDao(ICuentaDao cuentaDao) {
        this.cuentaDao = cuentaDao;
    }

    /**
     * @return the tarjetaDao
     */
    public ITarjetaDao getTarjetaDao() {
        return tarjetaDao;
    }

    /**
     * @param tarjetaDao the tarjetaDao to set
     */
    public void setTarjetaDao(ITarjetaDao tarjetaDao) {
        this.tarjetaDao = tarjetaDao;
    }
    
}
