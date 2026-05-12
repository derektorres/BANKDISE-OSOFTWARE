package itson.edu.mx.UI;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.TarjetaDao;
import itson.edu.mx.entities.Cuenta;
import itson.edu.mx.operaciones.ATM;
import itson.edu.mx.operaciones.TransaccionCambioNip;
import itson.edu.mx.operaciones.TransaccionDeposito;
import itson.edu.mx.operaciones.TransaccionRetiro;
import itson.edu.mx.operaciones.TransaccionTransferencia;
import itson.edu.mx.operaciones.TransaccionReporteRobo;

import java.util.Date;
import java.util.Scanner;

public class MainTerminalPrueba {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CuentaDao cuentaDao = new CuentaDao();
        TarjetaDao tarjetaDao = new TarjetaDao();

        System.out.println("=========================================");
        System.out.println("      SISTEMA BANCARIO - MODO TERMINAL   ");
        System.out.println("=========================================\n");

        try {
            // 1. LOGIN DE CUENTA
            System.out.println("--- INICIO DE SESIÓN ---");
            System.out.print("Introduce tu Número de Cuenta: ");
            String numCuenta = scanner.nextLine();
            System.out.print("Introduce tu Password de Cuenta: ");
            String pass = scanner.nextLine();

            Cuenta cuentaUser = cuentaDao.autenticar(numCuenta, pass);

            if (cuentaUser == null || !cuentaUser.estaActiva()) {
                throw new RuntimeException("Credenciales inválidas o cuenta bloqueada.");
            }

            // 2. VALIDACIÓN DE TARJETA
            System.out.println("\n--- VALIDACIÓN DE TARJETA FÍSICA ---");
            System.out.print("Número de Tarjeta: ");
            String numeroTarjeta = scanner.nextLine();
            System.out.print("NIP (4 dígitos): ");
            String nipIngresado = scanner.nextLine();

            if (!tarjetaDao.validarTarjetaActivaYNip(numeroTarjeta, nipIngresado)) {
                throw new RuntimeException("Tarjeta no válida, NIP incorrecto o ya está bloqueada.");
            }

            // 3. CONEXIÓN AL CAJERO
            ATM miCajero = new ATM(1); 
            System.out.println("\n✅ Acceso concedido a: " + miCajero.getDatosCajero().getDireccion());

            // Variable para rastrear si el internet está prendido o apagado en nuestra simulación
            boolean internetPrendido = true;

            // 4. MENÚ DE OPERACIONES
            boolean continuar = true;
            while (continuar) {
                System.out.println("\n=========================================");
                System.out.println("               MENÚ PRINCIPAL            ");
                System.out.println("=========================================");
                System.out.println("ESTADO DE RED: " + (internetPrendido ? "🟢 CONECTADO" : "🔴 MODO OFFLINE"));
                System.out.println("-----------------------------------------");
                System.out.println("1. Retiro de Efectivo");
                System.out.println("2. Depósito");
                System.out.println("3. Transferencia");
                System.out.println("4. Cambio de NIP");
                System.out.println("5. REPORTAR ROBO/EXTRAVÍO (Bloqueo)");
                System.out.println("6. [SIMULADOR] Apagar / Encender Internet"); // <-- EL SWITCH MÁGICO
                System.out.println("7. Salir");
                System.out.print("Selección: ");
                
                int op = Integer.parseInt(scanner.nextLine());
                String resultado = "";

                switch (op) {
                    case 1:
                        System.out.print("Monto a retirar: $");
                        double mR = Double.parseDouble(scanner.nextLine());
                        resultado = miCajero.procesarTransaccion(new TransaccionRetiro(0, new Date(), "Retiro", miCajero.getDatosCajero().getId(), mR, cuentaUser, numeroTarjeta, nipIngresado));
                        break;
                    case 2:
                        System.out.print("Monto a depositar: $");
                        double mD = Double.parseDouble(scanner.nextLine());
                        resultado = miCajero.procesarTransaccion(new TransaccionDeposito(0, new Date(), "Deposito", miCajero.getDatosCajero().getId(), mD, cuentaUser));
                        break;
                    case 3:
                        System.out.print("Cuenta destino: ");
                        String cD = scanner.nextLine();
                        System.out.print("Monto: $");
                        double mT = Double.parseDouble(scanner.nextLine());
                        resultado = miCajero.procesarTransaccion(new TransaccionTransferencia(0, new Date(), "Transferencia", miCajero.getDatosCajero().getId(), mT, cuentaUser, cD));
                        break;
                    case 4:
                        System.out.print("Nuevo NIP: ");
                        String nN = scanner.nextLine();
                        resultado = miCajero.procesarTransaccion(new TransaccionCambioNip(0, new Date(), "Cambio NIP", cuentaUser.getCuentaId(), miCajero.getDatosCajero().getId(), numeroTarjeta, nipIngresado, nN));
                        // Solo actualizamos si había internet (si no, está en la cola)
                        if (internetPrendido) nipIngresado = nN; 
                        break;
                    case 5:
                        System.out.println("\n⚠️ ¡ADVERTENCIA! Esta acción bloqueará su tarjeta permanentemente.");
                        System.out.print("¿Está seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            // Por seguridad, el reporte de robo ignora la cola offline, pero para simplificar usamos el proceso estándar.
                            TransaccionReporteRobo reporte = new TransaccionReporteRobo(0, new Date(), "Reporte Robo", cuentaUser.getCuentaId(), miCajero.getDatosCajero().getId(), numeroTarjeta);
                            if (internetPrendido) {
                                reporte.ejecutar();
                                System.out.println("\n✅ TARJETA BLOQUEADA CON ÉXITO.");
                                continuar = false;
                                resultado = "Tarjeta inactivada por reporte.";
                            } else {
                                resultado = "No hay conexión para procesar bloqueos de seguridad inmediatos.";
                            }
                        }
                        break;
                    case 6:
                        // INVERTIMOS EL ESTADO DEL INTERNET
                        internetPrendido = !internetPrendido;
                        miCajero.setTieneInternet(internetPrendido); 
                        
                        System.out.println("\n🔌 Has " + (internetPrendido ? "ENCENDIDO" : "APAGADO") + " la conexión de red del cajero.");
                        // Cuando lo prendes, la clase ATM sincroniza todo automáticamente en el fondo
                        if (internetPrendido) {
                            System.out.println("🔄 Sincronización en segundo plano completada. Revise su saldo.");
                            // Como el cajero ya proceso todo, actualizamos nuestro objeto cuenta desde la BD
                            cuentaUser = cuentaDao.buscarCuentaPorNumero(numCuenta); 
                        }
                        continue;
                    case 7:
                        continuar = false;
                        continue;
                    default:
                        System.out.println("Opción no válida.");
                        continue;
                }

                if (!resultado.isEmpty()) {
                    System.out.println("\n>>> TICKET: " + resultado);
                    // Si el internet está apagado, te recordamos que el saldo no se reflejará aún
                    if (!internetPrendido) {
                        System.out.println("ℹ️ Nota: Su saldo se actualizará cuando se restablezca la conexión.");
                    } else {
                        System.out.println("Saldo actual: $" + cuentaUser.getSaldoDisponible());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("\n[!] ERROR: " + e.getMessage());
        } finally {
            System.out.println("\n🏦 Sesión terminada. Gracias por su confianza.");
            scanner.close();
        }
    }
}