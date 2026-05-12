package itson.edu.mx.UI;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.ICuentaDao;
import itson.edu.mx.entities.Cuenta;
import itson.edu.mx.operaciones.TransaccionTransferencia;
import java.util.Date;

public class PruebaTransferencia {
    public static void main(String[] args) {
        
        ICuentaDao cuentaDao = new CuentaDao();
        
        // 1. Iniciamos sesión con la cuenta de Juan
        Cuenta miCuenta = cuentaDao.autenticar("1234567890", "pass123");
        
        if (miCuenta != null) {
            System.out.println("--- NUEVA TRANSFERENCIA ---");
            System.out.println("Saldo actual origen: $" + miCuenta.getSaldoDisponible());
            
            // 2. Datos de la transferencia
            double cantidadATransferir = 300.00;
            String cuentaParaMaria = "9876543210"; // La cuenta que creamos en MySQL
            int cajeroID = 1;
            
            try {
                TransaccionTransferencia transferencia = new TransaccionTransferencia(
                        0, new Date(), "Transferencia a terceros", cajeroID, cantidadATransferir, miCuenta, cuentaParaMaria
                );
                
                // 3. Ejecutar
                if(transferencia.ejecutar()){
                    System.out.println("✅ GUI Muestra: Transferencia exitosa por $" + cantidadATransferir);
                    System.out.println("GUI Muestra: Su nuevo saldo es: $" + miCuenta.getSaldoDisponible());
                    
                    // Solo para comprobar que funcionó en la base de datos (Esto no iría en la GUI final)
                    Cuenta cuentaMaria = cuentaDao.buscarCuentaPorNumero(cuentaParaMaria);
                    System.out.println("🔎 (Validación secreta DB) - Saldo de la cuenta destino es ahora: $" + cuentaMaria.getSaldoDisponible());
                }
                
            } catch (RuntimeException e) {
                System.out.println("❌ GUI Muestra Alerta: " + e.getMessage());
            }
            
        } else {
            System.out.println("Error de sesión.");
        }
    }
}