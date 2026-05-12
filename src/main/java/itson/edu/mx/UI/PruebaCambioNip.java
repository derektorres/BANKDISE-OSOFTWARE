package itson.edu.mx.UI;

import itson.edu.mx.operaciones.TransaccionCambioNip;
import java.util.Date;

public class PruebaCambioNip {
    public static void main(String[] args) {
        
        
        String tarjetaJuan = "1111222233334444";
        int cuentaIdJuan = 1; 
        
       
        String nipActual = "1234";
        String nipNuevo = "4321"; 
        
        try {
            TransaccionCambioNip operacion = new TransaccionCambioNip(
                0, new Date(), "Cambio de NIP", cuentaIdJuan, 1, tarjetaJuan, nipActual, nipNuevo
            );
            
            if (operacion.ejecutar()) {
                System.out.println("✅ GUI Muestra: ¡NIP actualizado con éxito!");
            }
            
        } catch (RuntimeException e) {
            System.out.println("❌ GUI Muestra Alerta: " + e.getMessage());
        }
    }
}