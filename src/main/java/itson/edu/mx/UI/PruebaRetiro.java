package itson.edu.mx.UI;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.ICuentaDao;
import itson.edu.mx.entities.Cuenta;
import itson.edu.mx.operaciones.TransaccionRetiro;
import java.util.Date;

public class PruebaRetiro {
    public static void main(String[] args) {
        
        ICuentaDao cuentaDao = new CuentaDao();
        Cuenta miCuenta = cuentaDao.autenticar("1234567890", "pass123");
        
        if (miCuenta != null) {
            
            // Datos del retiro incluyendo la validación de la tarjeta insertada
            double cantidadARetirar = 500.00; 
            int cajeroID = 1;
            String tarjetaInsertada = "1111222233334444";
            String nipTecleado = "1234"; // Prueba poner uno malo (ej. "0000") para ver cómo falla
            
            try {
                // Pasamos la tarjeta y el NIP al final
                TransaccionRetiro retiro = new TransaccionRetiro(
                        0, new Date(), "Retiro", cajeroID, cantidadARetirar, miCuenta, tarjetaInsertada, nipTecleado
                );
                
                if(retiro.ejecutar()){
                    System.out.println("✅ GUI Muestra: Retiro exitoso. Por favor tome su dinero.");
                    System.out.println("GUI Muestra: Nuevo saldo: $" + miCuenta.getSaldoDisponible());
                }
                
            } catch (RuntimeException e) {
                System.out.println("❌ GUI Muestra Alerta: " + e.getMessage());
            }
            
        }
    }
}