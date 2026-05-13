package itson.edu.mx.UI;

import itson.edu.mx.Dao.CuentaDao;
import itson.edu.mx.Dao.TarjetaDao;
import itson.edu.mx.entities.Cuenta;
import itson.edu.mx.operaciones.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

public class CajeroGUI extends JFrame {
    
    private CuentaDao cuentaDao = new CuentaDao();
    private TarjetaDao tarjetaDao = new TarjetaDao();
    private ATM miCajero;
    private Cuenta cuentaUser;
    private String numeroTarjeta;
    private String nipIngresado;
    private boolean internetPrendido = true;

    private JPanel contenedor;
    private CardLayout cardLayout;
    private JLabel lblEstadoRed;
    private JLabel lblSaldo;

    private Color colorFondo = new Color(240, 244, 248);
    private Color colorPrimario = new Color(26, 54, 93);
    private Color colorAcento = new Color(49, 130, 206);
    private Color colorPeligro = new Color(229, 62, 62);

    public CajeroGUI() {
        setTitle("ITSON - Banca Premium");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);

        contenedor.add(crearPanelLogin(), "LOGIN");
        add(contenedor);
        cardLayout.show(contenedor, "LOGIN");
    }

    private JPanel crearPanelLogin() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel header = new JPanel();
        header.setBackground(colorPrimario);
        header.setPreferredSize(new Dimension(500, 80));
        JLabel lblTitulo = new JLabel("BIENVENIDO A ITSON BANK");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        header.add(Box.createVerticalStrut(50));
        header.add(lblTitulo);

        JPanel panelForm = new JPanel(new GridLayout(8, 1, 10, 5));
        panelForm.setBackground(colorFondo);
        panelForm.setBorder(new EmptyBorder(30, 60, 40, 60));

        JTextField txtCuenta = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JTextField txtTarjeta = new JTextField();
        JPasswordField txtNip = new JPasswordField();
        
        estilizarCampo(txtCuenta);
        estilizarCampo(txtPass);
        estilizarCampo(txtTarjeta);
        estilizarCampo(txtNip);

        panelForm.add(crearLabel("Número de Cuenta:"));
        panelForm.add(txtCuenta);
        panelForm.add(crearLabel("Contraseña de Cuenta:"));
        panelForm.add(txtPass);
        
        JPanel panelTarjeta = new JPanel(new GridLayout(1, 2, 10, 0));
        panelTarjeta.setBackground(colorFondo);
        panelTarjeta.add(crearLabel("No. Tarjeta:"));
        panelTarjeta.add(crearLabel("NIP (4 dígitos):"));
        panelForm.add(panelTarjeta);

        JPanel panelCamposTarjeta = new JPanel(new GridLayout(1, 2, 10, 0));
        panelCamposTarjeta.setBackground(colorFondo);
        panelCamposTarjeta.add(txtTarjeta);
        panelCamposTarjeta.add(txtNip);
        panelForm.add(panelCamposTarjeta);

        panelForm.add(Box.createVerticalStrut(10));

        JButton btnEntrar = new JButton("INICIAR SESIÓN");
        estilizarBoton(btnEntrar, colorAcento, Color.WHITE);
        panelForm.add(btnEntrar);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> {
            try {
                String numC = txtCuenta.getText();
                String passC = new String(txtPass.getPassword());
                String numT = txtTarjeta.getText();
                String nipT = new String(txtNip.getPassword());

                cuentaUser = cuentaDao.autenticar(numC, passC);
                if (cuentaUser == null || !cuentaUser.estaActiva()) {
                    throw new Exception("Cuenta inválida o bloqueada.");
                }

                // Parche de seguridad para el límite
                if (cuentaUser.getLimiteDiarioRetiro() <= 0) {
                    cuentaUser.setLimiteDiarioRetiro(10000.0);
                }

                if (!tarjetaDao.validarTarjetaActivaYNip(numT, nipT)) {
                    throw new Exception("Tarjeta o NIP incorrectos.");
                }

                this.numeroTarjeta = numT;
                this.nipIngresado = nipT;
                this.miCajero = new ATM(1);

                contenedor.add(crearPanelMenu(), "MENU");
                cardLayout.show(contenedor, "MENU");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panelPrincipal;
    }

    private JPanel crearPanelMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(colorFondo);

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.setBackground(colorPrimario);
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        // ---> AQUI ESTÁ EL CAMBIO PARA QUE USE TU NOMBRE <---
        JLabel lblBienvenida = new JLabel("👋 ¡Hola, " + obtenerNombreCliente() + "!");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBienvenida.setForeground(Color.WHITE);

        lblEstadoRed = new JLabel("🟢 ESTADO: CONECTADO", SwingConstants.RIGHT);
        lblEstadoRed.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstadoRed.setForeground(new Color(144, 238, 144));

        JPanel headerTop = new JPanel(new BorderLayout());
        headerTop.setOpaque(false);
        headerTop.add(lblBienvenida, BorderLayout.WEST);
        headerTop.add(lblEstadoRed, BorderLayout.EAST);
        
        lblSaldo = new JLabel("$ " + String.format("%.2f", cuentaUser.getSaldoDisponible()));
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblSaldo.setForeground(Color.WHITE);
        lblSaldo.setHorizontalAlignment(SwingConstants.CENTER);

        header.add(headerTop);
        header.add(lblSaldo);

        JPanel panelBotones = new JPanel(new GridLayout(4, 2, 15, 15));
        panelBotones.setBackground(colorFondo);
        panelBotones.setBorder(new EmptyBorder(30, 30, 30, 30));

        JButton btnRetiro = new JButton("💵 Retiro");
        JButton btnDeposito = new JButton("💰 Depósito");
        JButton btnTransfer = new JButton("🔄 Transferencia");
        JButton btnNip = new JButton("🔑 Cambiar NIP");
        JButton btnRobo = new JButton("🚨 Reportar Robo");
        JButton btnNet = new JButton("🌐 Modo Offline");
        JButton btnSalir = new JButton("🚪 Salir");

        estilizarBoton(btnRetiro, colorPrimario, Color.WHITE);
        estilizarBoton(btnDeposito, colorPrimario, Color.WHITE);
        estilizarBoton(btnTransfer, colorPrimario, Color.WHITE);
        estilizarBoton(btnNip, colorPrimario, Color.WHITE);
        estilizarBoton(btnRobo, colorPeligro, Color.WHITE);
        estilizarBoton(btnNet, Color.GRAY, Color.WHITE);
        estilizarBoton(btnSalir, Color.DARK_GRAY, Color.WHITE);

        panelBotones.add(btnRetiro); panelBotones.add(btnDeposito);
        panelBotones.add(btnTransfer); panelBotones.add(btnNip);
        panelBotones.add(btnRobo); panelBotones.add(btnNet);
        panelBotones.add(btnSalir);

        panel.add(header, BorderLayout.NORTH);
        panel.add(panelBotones, BorderLayout.CENTER);

        // --- EVENTOS ---
        btnRetiro.addActionListener(e -> {
            String monto = JOptionPane.showInputDialog(this, "Monto a retirar:", "Retiro", JOptionPane.QUESTION_MESSAGE);
            if(monto != null && !monto.trim().isEmpty()) procesar(new TransaccionRetiro(0, new Date(), "Retiro", miCajero.getDatosCajero().getId(), Double.parseDouble(monto), cuentaUser, numeroTarjeta, nipIngresado));
        });

        btnDeposito.addActionListener(e -> {
            String monto = JOptionPane.showInputDialog(this, "Monto a depositar:", "Depósito", JOptionPane.QUESTION_MESSAGE);
            if(monto != null && !monto.trim().isEmpty()) procesar(new TransaccionDeposito(0, new Date(), "Deposito", miCajero.getDatosCajero().getId(), Double.parseDouble(monto), cuentaUser));
        });

        btnTransfer.addActionListener(e -> {
            JTextField txtDestino = new JTextField();
            JTextField txtMonto = new JTextField();
            Object[] message = {"Cuenta Destino:", txtDestino, "Monto:", txtMonto};
            int option = JOptionPane.showConfirmDialog(this, message, "Transferencia", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                procesar(new TransaccionTransferencia(0, new Date(), "Transferencia", miCajero.getDatosCajero().getId(), Double.parseDouble(txtMonto.getText()), cuentaUser, txtDestino.getText()));
            }
        });

        btnNip.addActionListener(e -> {
            String nuevoNip = JOptionPane.showInputDialog(this, "Nuevo NIP (4 dígitos):", "Cambio de NIP", JOptionPane.QUESTION_MESSAGE);
            if(nuevoNip != null) {
                String res = procesar(new TransaccionCambioNip(0, new Date(), "Cambio NIP", cuentaUser.getCuentaId(), miCajero.getDatosCajero().getId(), numeroTarjeta, nipIngresado, nuevoNip));
                if (!res.isEmpty()) nipIngresado = nuevoNip;
            }
        });

        btnRobo.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Bloquear tarjeta permanentemente?", "SEGURIDAD", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (resp == JOptionPane.YES_OPTION) {
                if (internetPrendido) {
                    new TransaccionReporteRobo(0, new Date(), "Reporte Robo", cuentaUser.getCuentaId(), miCajero.getDatosCajero().getId(), numeroTarjeta).ejecutar();
                    JOptionPane.showMessageDialog(this, "Operación realizada con éxito. La sesión se cerrará.");
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(this, "No hay conexión para procesar bloqueos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNet.addActionListener(e -> {
            internetPrendido = !internetPrendido;
            miCajero.setTieneInternet(internetPrendido);
            if(internetPrendido) {
                lblEstadoRed.setText("🟢 ESTADO: CONECTADO");
                lblEstadoRed.setForeground(new Color(144, 238, 144));
                double limiteRespaldo = cuentaUser.getLimiteDiarioRetiro(); 
                cuentaUser = cuentaDao.buscarCuentaPorNumero(cuentaUser.getNumeroCuenta());
                cuentaUser.setLimiteDiarioRetiro(limiteRespaldo); 
                actualizarSaldoUI();
                JOptionPane.showMessageDialog(this, "Sincronización exitosa con el servidor central.");
            } else {
                lblEstadoRed.setText("🔴 ESTADO: OFFLINE");
                lblEstadoRed.setForeground(new Color(255, 100, 100));
                actualizarSaldoUI();
            }
        });

        btnSalir.addActionListener(e -> System.exit(0));

        return panel;
    }

    // ---> ESTE ES EL METODO QUE EXTRAE EL NOMBRE PARA LA VENTANA <---
    private String obtenerNombreCliente() {
        if (cuentaUser.getNombreCliente() == null || cuentaUser.getNombreCliente().trim().isEmpty()) {
            return "Cliente";
        }
        return cuentaUser.getNombreCliente();
    }
    
    private String procesar(Transaccion t) {
        try {
            miCajero.procesarTransaccion(t);
             
            String mensaje = "Operación realizada con éxito."; 
            
            if (t instanceof TransaccionRetiro) {
                mensaje = "💵 Su retiro ha sido procesado exitosamente. Por favor, tome su efectivo.";
            } else if (t instanceof TransaccionDeposito) {
                mensaje = "💰 Su depósito ha sido abonado a su cuenta correctamente.";
            } else if (t instanceof TransaccionTransferencia) {
                mensaje = "🔄 La transferencia se ha enviado con éxito a la cuenta destino.";
            } else if (t instanceof TransaccionCambioNip) {
                mensaje = "🔑 Su NIP ha sido actualizado con éxito. Recuerde no compartirlo con nadie.";
            }

            JOptionPane.showMessageDialog(this, mensaje, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            actualizarSaldoUI();
            return "EXITO";
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Operación Rechazada", JOptionPane.ERROR_MESSAGE);
            return "";
        }
    }

    private void actualizarSaldoUI() {
        if (internetPrendido) {
            lblSaldo.setText("$ " + String.format("%.2f", cuentaUser.getSaldoDisponible()));
        } else {
            lblSaldo.setText("En espera...");
        }
    }

    private JLabel crearLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(colorPrimario);
        return lbl;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    private void estilizarBoton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new CajeroGUI().setVisible(true));
    }
}