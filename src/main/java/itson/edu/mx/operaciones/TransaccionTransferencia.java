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
public class TransaccionTransferencia extends Transaccion {
    private double monto;
    private Cuenta cuentaOrigen;
    private String numeroCuentaDestino;
    private ICuentaDao cuentaDao;

    public TransaccionTransferencia(int transaccionId, Date fecha, String descripcion, int atmId, double monto, Cuenta cuentaOrigen, String numeroCuentaDestino) {
        super(transaccionId, fecha, descripcion, cuentaOrigen.getCuentaId(), atmId);
        this.monto = monto;
        this.cuentaOrigen = cuentaOrigen;
        this.numeroCuentaDestino = numeroCuentaDestino;
        this.cuentaDao = new CuentaDao();
    }

    @Override
    public boolean ejecutar() {
        if (monto <= 0) throw new RuntimeException("Monto debe ser mayor a 0.");
        if (!cuentaOrigen.estaActiva()) throw new RuntimeException("Tu cuenta está bloqueada.");
        if (monto > cuentaOrigen.getSaldoDisponible()) throw new RuntimeException("Fondos insuficientes.");
        if (cuentaOrigen.getNumeroCuenta().equals(numeroCuentaDestino)) throw new RuntimeException("No puedes transferirte a ti mismo.");

        Cuenta cuentaDestino = cuentaDao.buscarCuentaPorNumero(numeroCuentaDestino);
        
        if (cuentaDestino == null) throw new RuntimeException("La cuenta destino no existe.");
        if (!cuentaDestino.estaActiva()) throw new RuntimeException("La cuenta destino está bloqueada.");

        double nuevoSaldoOrigen = cuentaOrigen.getSaldoDisponible() - monto;
        double nuevoSaldoDestino = cuentaDestino.getSaldoDisponible() + monto;

        if (!cuentaDao.actualizarSaldo(cuentaOrigen.getCuentaId(), nuevoSaldoOrigen)) {
            throw new RuntimeException("Error al descontar de tu cuenta.");
        }

        if (!cuentaDao.actualizarSaldo(cuentaDestino.getCuentaId(), nuevoSaldoDestino)) {
            cuentaDao.actualizarSaldo(cuentaOrigen.getCuentaId(), cuentaOrigen.getSaldoDisponible());
            throw new RuntimeException("Error al depositar en destino. Dinero devuelto.");
        }

        cuentaOrigen.setSaldoDisponible(nuevoSaldoOrigen);
        return true;
    }
}