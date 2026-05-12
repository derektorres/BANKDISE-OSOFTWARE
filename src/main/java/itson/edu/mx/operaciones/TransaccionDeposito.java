/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.operaciones;
import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.ICuentaDao;
import itson.edu.mx.entities.Cuenta;
import java.util.Date;
/**
 *
 * @author torre
 */
public class TransaccionDeposito extends Transaccion {
    private double monto;
    private Cuenta cuenta;
    private ICuentaDao cuentaDao;

    public TransaccionDeposito() {
        super();
        this.cuentaDao = new CuentaDao();
    }

    public TransaccionDeposito(int transaccionId, Date fecha, String descripcion, int atmId, double monto, Cuenta cuenta) {
        super(transaccionId, fecha, descripcion, cuenta.getCuentaId(), atmId);
        this.monto = monto;
        this.cuenta = cuenta;
        this.cuentaDao = new CuentaDao();
    }

    @Override
    public boolean ejecutar() {
        
        if (getMonto() <= 0) {
            throw new RuntimeException("Operacion rechazada El monto a depositar debe ser mayor a $0.00.");
        }

        if (!cuenta.estaActiva()) {
            throw new RuntimeException("Operacion rechazada No se pueden recibir depositos en una cuenta bloqueada.");
        }

        double nuevoSaldo = getCuenta().getSaldoDisponible() + getMonto();

        boolean actualizadoEnBD = getCuentaDao().actualizarSaldo(cuentaID, nuevoSaldo);

        if (actualizadoEnBD) {
            getCuenta().setSaldoDisponible(nuevoSaldo);
            return true; 
        } else {
            throw new RuntimeException("Error del sistema No se pudo registrar el deposito en el banco central.");
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
    
}
