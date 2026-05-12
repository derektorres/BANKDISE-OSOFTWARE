package itson.edu.mx.UI;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.TarjetaDao;
import itson.edu.mx.entities.Cuenta;
import itson.edu.mx.operaciones.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class CajeroGUI extends JFrame {

    // Lógica y DAOs
    private CuentaDao cuentaDao = new CuentaDao();
    private TarjetaDao tarjetaDao = new TarjetaDao();
    private ATM miCajero;
    private Cuenta cuentaUser;
    private String numeroTarjeta;
    private String nipIngresado;
    private boolean internetPrendido = true;

    // Componentes UI
    private JPanel contenedor;
    private CardLayout cardLayout;
    private JLabel lblEstadoRed;
    private JLabel lblSaldo;

    public CajeroGUI() {
        setTitle("ITSON - Cajero Automático");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        // Inicializar pantallas
        contenedor.add(crearPanelLogin(), "LOGIN");
        // El panel de menú se agrega después de loguearse con éxito
        
        add(contenedor);
        cardLayout.show(contenedor, "LOGIN");
    }

    private JPanel crearPanelLogin() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JTextField txtCuenta = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JTextField txtTarjeta = new JTextField();
        JPasswordField txtNip = new JPasswordField();
        JButton btnEntrar = new JButton("ACCEDER");

        panel.add(new JLabel("Número de Cuenta:"));
        panel.add(txtCuenta);
        panel.add(new JLabel("Password de Cuenta:"));
        panel.add(txtPass);
        panel.add(new JLabel("Número de Tarjeta / NIP:"));
        JPanel filaTarjeta = new JPanel(new GridLayout(1, 2));
        filaTarjeta.add(txtTarjeta);
        filaTarjeta.add(txtNip);
        panel.add(filaTarjeta);
        panel.add(new JLabel("")); // Espacio
        panel.add(btnEntrar);

        btnEntrar.addActionListener(e -> {
            try {
                String numC = txtCuenta.getText();
                String passC = new String(txtPass.getPassword());
                String numT = txtTarjeta.getText();
                String nipT = new String(txtNip.getPassword());

                // 1. Validar Cuenta
                cuentaUser = cuentaDao.autenticar(numC, passC);
                if (cuentaUser == null || !cuentaUser.estaActiva()) {
                    throw new Exception("Cuenta inválida o bloqueada.");
                }

                // 2. Validar Tarjeta
                if (!tarjetaDao.validarTarjetaActivaYNip(numT, nipT)) {
                    throw new Exception("Tarjeta o NIP incorrectos.");
                }

                // 3. Iniciar sesión exitosa
                this.numeroTarjeta = numT;
                this.nipIngresado = nipT;
                this.miCajero = new ATM(1); // ID de cajero 1

                contenedor.add(crearPanelMenu(), "MENU");
                cardLayout.show(contenedor, "MENU");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel botones = new JPanel(new GridLayout(4, 2, 10, 10));
        botones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblEstadoRed = new JLabel("🟢 CONECTADO", SwingConstants.CENTER);
        lblEstadoRed.setFont(new Font("Arial", Font.BOLD, 12));
        
        lblSaldo = new JLabel("Saldo disponible: $" + cuentaUser.getSaldoDisponible(), SwingConstants.CENTER);

        // Definición de Botones
        JButton btnRetiro = new JButton("1. Retiro");
        JButton btnDeposito = new JButton("2. Depósito");
        JButton btnTransfer = new JButton("3. Transferencia");
        JButton btnNip = new JButton("4. Cambiar NIP");
        JButton btnRobo = new JButton("5. Reportar Robo");
        JButton btnNet = new JButton("6. Switch Internet");
        JButton btnSalir = new JButton("7. Salir");

        botones.add(btnRetiro); botones.add(btnDeposito);
        botones.add(btnTransfer); botones.add(btnNip);
        botones.add(btnRobo); botones.add(btnNet);
        botones.add(btnSalir);

        panel.add(lblEstadoRed, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.CENTER);
        panel.add(lblSaldo, BorderLayout.SOUTH);

        // ACCIONES
        btnRetiro.addActionListener(e -> {
            String monto = JOptionPane.showInputDialog("Monto a retirar:");
            if(monto != null) procesar(new TransaccionRetiro(0, new Date(), "Retiro", miCajero.getDatosCajero().getId(), Double.parseDouble(monto), cuentaUser, numeroTarjeta, nipIngresado));
        });

        btnDeposito.addActionListener(e -> {
            String monto = JOptionPane.showInputDialog("Monto a depositar:");
            if(monto != null) procesar(new TransaccionDeposito(0, new Date(), "Deposito", miCajero.getDatosCajero().getId(), Double.parseDouble(monto), cuentaUser));
        });

        btnTransfer.addActionListener(e -> {
            String cuentaDestino = JOptionPane.showInputDialog("Cuenta destino:");
            String monto = JOptionPane.showInputDialog("Monto a transferir:");
            if(cuentaDestino != null && monto != null) 
                procesar(new TransaccionTransferencia(0, new Date(), "Transferencia", miCajero.getDatosCajero().getId(), Double.parseDouble(monto), cuentaUser, cuentaDestino));
        });

        btnNip.addActionListener(e -> {
            String nuevoNip = JOptionPane.showInputDialog("Nuevo NIP (4 dígitos):");
            if(nuevoNip != null) {
                String res = procesar(new TransaccionCambioNip(0, new Date(), "Cambio NIP", cuentaUser.getCuentaId(), miCajero.getDatosCajero().getId(), numeroTarjeta, nipIngresado, nuevoNip));
                if (internetPrendido && res.toLowerCase().contains("éxito")) nipIngresado = nuevoNip;
            }
        });

        btnRobo.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Seguro que desea bloquear su tarjeta permanentemente?", "¡ADVERTENCIA!", JOptionPane.YES_NO_OPTION);
            if (resp == JOptionPane.YES_OPTION) {
                if (internetPrendido) {
                    new TransaccionReporteRobo(0, new Date(), "Reporte Robo", cuentaUser.getCuentaId(), miCajero.getDatosCajero().getId(), numeroTarjeta).ejecutar();
                    JOptionPane.showMessageDialog(this, "TARJETA BLOQUEADA. Saliendo...");
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(this, "No hay conexión para procesar bloqueos.");
                }
            }
        });

        btnNet.addActionListener(e -> {
            internetPrendido = !internetPrendido;
            miCajero.setTieneInternet(internetPrendido);
            lblEstadoRed.setText(internetPrendido ? "🟢 CONECTADO" : "🔴 MODO OFFLINE");
            if(internetPrendido) {
                cuentaUser = cuentaDao.buscarCuentaPorNumero(cuentaUser.getNumeroCuenta());
                actualizarSaldoUI();
                JOptionPane.showMessageDialog(this, "🔄 Sincronización completada.");
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));

        return panel;
    }

    private String procesar(Transaccion t) {
        try {
            String resultado = miCajero.procesarTransaccion(t);
            JOptionPane.showMessageDialog(this, "TICKET:\n" + resultado);
            actualizarSaldoUI();
            return resultado;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR: " + ex.getMessage(), "Error de Operación", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    private void actualizarSaldoUI() {
        if (internetPrendido) {
            lblSaldo.setText("Saldo disponible: $" + cuentaUser.getSaldoDisponible());
        } else {
            lblSaldo.setText("Saldo: [Pendiente de sincronizar]");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CajeroGUI().setVisible(true));
    }
}