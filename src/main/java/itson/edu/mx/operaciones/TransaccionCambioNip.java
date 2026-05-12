/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.operaciones;
import itson.edu.mx.Dao.ITarjetaDao;
import itson.edu.mx.Dao.TarjetaDao;
import java.util.Date;
/**
 *
 * @author torre
 */

public class TransaccionCambioNip extends Transaccion {
    private String numeroTarjeta;
    private String nipAnterior;
    private String nipNuevo;
    private ITarjetaDao tarjetaDao;

    public TransaccionCambioNip(int transaccionId, Date fecha, String descripcion, int cuentaID, int atmId, String numeroTarjeta, String nipAnterior, String nipNuevo) {
        super(transaccionId, fecha, descripcion, cuentaID, atmId);
        this.numeroTarjeta = numeroTarjeta;
        this.nipAnterior = nipAnterior;
        this.nipNuevo = nipNuevo;
        this.tarjetaDao = new TarjetaDao();
    }

    @Override
    public boolean ejecutar() {
        
        if (nipAnterior.equals(nipNuevo)) {
            throw new RuntimeException("El nuevo NIP no puede ser igual al actual");
        }

        if (nipNuevo == null || !nipNuevo.matches("\\d{4}")) {
            throw new RuntimeException("El NIP debe ser de 4 digitos numericos");
        }

        if (!tarjetaDao.validarTarjetaActivaYNip(numeroTarjeta, nipAnterior)) {
            throw new RuntimeException("El NIP actual es incorrecto no se puede realizar el cambio");
        }

        boolean exito = tarjetaDao.actualizarNip(numeroTarjeta, nipNuevo);

        if (exito) {
            return true;
        } else {
            throw new RuntimeException("Error interno: No se pudo actualizar el NIP en el sistema.");
        }
    }
}
  