package itson.edu.mx.UI;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.ICuentaDao;
import itson.edu.mx.entities.Cuenta;

public class PruebaLogin {

    public static void main(String[] args) {
        System.out.println("--- SIMULADOR DE INICIO DE SESIÓN ---");
        
        // 1. Instanciamos el DAO
        ICuentaDao cuentaDao = new CuentaDao();
        
        // 2. Definimos credenciales de prueba 
        // (Asegúrate de que estos datos existan en tu tabla 'cuentas' en MySQL)
        String cuentaPrueba = "1234567890";
        String passPrueba = "pass123";
        
        System.out.println("Intentando ingresar con Cuenta: " + cuentaPrueba);
        
        // 3. Llamamos al método de autenticación
        Cuenta cuentaRecuperada = cuentaDao.autenticar(cuentaPrueba, passPrueba);
        
        // 4. Verificamos el resultado
        System.out.println("\n--- RESULTADO ---");
        if (cuentaRecuperada != null) {
            System.out.println("✅ Acceso Concedido.");
            System.out.println("Bienvenido/a.");
            System.out.println("ID de Cuenta: " + cuentaRecuperada.getCuentaId());
            System.out.println("Saldo Disponible: $" + cuentaRecuperada.getSaldoDisponible());
            System.out.println("Estado de la Cuenta: " + cuentaRecuperada.getEstadoCuenta());
            
            // Verificación extra de lógica de negocio
            if (cuentaRecuperada.getEstadoCuenta().toString().equals("BLOQUEADA")) {
                System.out.println("⚠️ Alerta: Esta cuenta está bloqueada y no puede operar.");
            }
            
        } else {
            System.out.println("❌ Acceso Denegado.");
            System.out.println("Error: El número de cuenta o la contraseña son incorrectos.");
        }
    }
}