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
public class TransaccionReporteRobo extends Transaccion {
    
    private String numeroTarjeta;
    private ITarjetaDao tarjetaDao;

    public TransaccionReporteRobo() {
        super();
        this.tarjetaDao = new TarjetaDao();
    }

    
    public TransaccionReporteRobo(int transaccionId, Date fecha, String descripcion, int cuentaID, int atmId, String numeroTarjeta) {
        super(transaccionId, fecha, descripcion, cuentaID, atmId);
        this.numeroTarjeta = numeroTarjeta;
        this.tarjetaDao = new TarjetaDao(); 
    }

    @Override
    public boolean ejecutar() {
        System.out.println("Iniciando proceso de reporte de robo para la tarjeta: " + numeroTarjeta);
        
        boolean bloqueadaExitosamente = tarjetaDao.bloquearTarjeta(this.numeroTarjeta);
        
        if (bloqueadaExitosamente) {
            System.out.println("Transacción exitosa: La tarjeta ha sido bloqueada permanentemente.");
            return true;
        } else {
            System.err.println("Error en la transacción: No se pudo bloquear la tarjeta (Verifique el número).");
            return false;
        }
    }

    public String getNumeroTarjeta() { 
        return numeroTarjeta; 
    }
    
    public void setNumeroTarjeta(String numeroTarjeta) { 
        this.numeroTarjeta = numeroTarjeta; 
    }
}