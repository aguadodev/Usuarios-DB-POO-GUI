import usuarios.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsuariosGUI {
    private JPanel frmPanel;
    private JTextField txtUsername;
    private JPasswordField passPassword;
    private JButton btnLogin;
    private JLabel lblMensajes;
    private JButton btnCrearUsuario;
    private JButton btnCambiarPassword;

    public UsuariosGUI() {
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario(txtUsername.getText(), passPassword.getText());
                String mensaje = usuario.login() ? "Login OK" : "Login KO";
                lblMensajes.setText(mensaje);
            }
        });
        btnCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario(txtUsername.getText(), passPassword.getText());
                String mensaje = usuario.crearBD() ? "Usuario " + txtUsername.getText() + " creado en la BD" : "ERROR al crear el usuario";
                lblMensajes.setText(mensaje);
            }
        });
        btnCambiarPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario usuario = new Usuario(txtUsername.getText(), passPassword.getText());
                String mensaje = usuario.cambiarPassword() ? "Se ha cambiado la contraseña del usuario " + txtUsername.getText() : "ERROR al cambiar la contraseña";
                lblMensajes.setText(mensaje);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("UsuariosGUI");
        frame.setContentPane(new UsuariosGUI().frmPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
