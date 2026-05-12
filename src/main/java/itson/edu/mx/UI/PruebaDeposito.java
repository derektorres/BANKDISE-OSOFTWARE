package itson.edu.mx.UI;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.ICuentaDao;
import itson.edu.mx.entities.Cuenta;
import itson.edu.mx.operaciones.TransaccionDeposito;
import java.util.Date;

public class PruebaDeposito {
    public static void main(String[] args) {
        
        ICuentaDao cuentaDao = new CuentaDao();
        
        // Simulamos que el usuario ya inició sesión
        Cuenta miCuenta = cuentaDao.autenticar("1234567890", "pass123");
        
        if (miCuenta != null) {
            
            System.out.println("GUI Muestra: Saldo actual antes del depósito: $" + miCuenta.getSaldoDisponible());
            
            // Datos del depósito
            double cantidadADepositar = 1500.00; // Intenta cambiarlo a -50 para probar la validación
            int cajeroID = 1;
            
            try {
                TransaccionDeposito deposito = new TransaccionDeposito(
                        0, new Date(), "Depósito en efectivo ATM", cajeroID, cantidadADepositar, miCuenta
                );
                
                // Ejecutamos la transacción
                if(deposito.ejecutar()){
                    System.out.println("✅ GUI Muestra: Depósito exitoso. Su dinero ha sido abonado.");
                    System.out.println("GUI Muestra: Nuevo saldo disponible: $" + miCuenta.getSaldoDisponible());
                }
                
            } catch (RuntimeException e) {
                // Atrapamos las alertas de negocio
                System.out.println("❌ GUI Muestra Alerta: " + e.getMessage());
            }
            
        } else {
            System.out.println("Error de sesión.");
        }
    }
}