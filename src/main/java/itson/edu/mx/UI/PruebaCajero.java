/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package itson.edu.mx.UI;

/**
 *
 * @author torre
 */

import itson.edu.mx.operaciones.TransaccionReporteRobo;
import java.util.Date;

public class PruebaCajero {

    public static void main(String[] args) {
        
        System.out.println("--- SIMULADOR DE CAJERO AUTOMÁTICO ---");
        System.out.println("Ejecutando Caso de Uso 8: Reporte de Tarjeta Robada...\n");
        
        // 1. Datos simulados de la operación
        int cuentaId = 1; // ID de la cuenta que acabamos de insertar en BD
        int atmId = 1;    // ID del cajero que acabamos de insertar en BD
        String numeroTarjetaRobada = "1111222233334444"; // La tarjeta de prueba
        
        // 2. Instanciamos la transacción del Paso 2
        TransaccionReporteRobo reporte = new TransaccionReporteRobo(
                0, // ID Transaccion (se autogenera en BD si la guardáramos)
                new Date(), // Fecha actual
                "Reporte de robo por el cliente desde pantalla principal",
                cuentaId,
                atmId,
                numeroTarjetaRobada
        );
        
        // 3. Ejecutamos la lógica de negocio (Esto llama a tu DAO y actualiza la BD)
        boolean resultado = reporte.ejecutar();
        
        // 4. Mostramos el resultado
        System.out.println("\n--- RESULTADO DE LA PRUEBA ---");
        if (resultado) {
            System.out.println("✅ ÉXITO: El sistema indica que la tarjeta fue bloqueada.");
            System.out.println("👉 Ve a MySQL y revisa si el estado cambió a 'BLOQUEADA'.");
        } else {
            System.out.println("❌ FALLO: No se pudo bloquear la tarjeta. Revisa tu Conexion.java.");
        }
    }
}