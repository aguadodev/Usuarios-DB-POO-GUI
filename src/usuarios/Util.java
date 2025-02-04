package usuarios;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Util {

    /*
     * FUNCIONES BCRYPT: generar hash y validar hash
     */
    /**
     * Valida un hash de BCrypt
     *
     * @param password Contraseña en texto claro
     * @param hash2y   Hash de BCrypt
     * @return true si la contraseña es correcta
     */
    public static boolean validarHash2Y(String password, String hash2y) {
        return BCrypt.verifyer(BCrypt.Version.VERSION_2Y)
                .verifyStrict(password.getBytes(StandardCharsets.UTF_8),
                        hash2y.getBytes(StandardCharsets.UTF_8)).verified;
    }

    /**
     * Genera un hash de BCrypt
     *
     * @param password Contraseña en texto claro
     * @return Hash de BCrypt
     */
    public static String generarStringHash2Y(String password) {
        char[] bcryptChars = BCrypt.with(BCrypt.Version.VERSION_2Y).hashToChar(13, password.toCharArray());
        return String.valueOf(bcryptChars);
    }














    // MÉTODOS YA REVISADOS: SE PODRÍAN BORRAR
    /**
     * Comprueba si un usuario y contraseña son correctos
     *
     * @param username Usuario
     * @param password Contraseña
     * @return true si el usuario y contraseña son correctos
     */
    public static boolean loginUsuario(String username, String password) {
        boolean loginOk = false;
        Connection conexion = Conexion.conectar();

        Statement sentencia;
        try {
            sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery("SELECT * FROM user WHERE username LIKE '" + username + "'");

            if (resultado.next()) {
                // Si existe el usuario valida la contraseña con BCrypt
                byte[] passwordHashed = resultado.getString("password").getBytes(StandardCharsets.UTF_8);
                BCrypt.Result resultStrict = BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verifyStrict(
                        password.getBytes(StandardCharsets.UTF_8),
                        passwordHashed);
                loginOk = resultStrict.verified;
                loginOk = validarHash2Y(password, resultado.getString("password"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginOk;
    }



    /**
     * Inicia sesión de usuario
     * Solicita credenciales de inicio de sesión, y si son correctas devuelve el
     * nombre de usuario.
     *
     * @return Usuario que ha iniciado sesión
     */
    public static String iniciarSesion() {
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("LOGIN DE USUARIO");
            System.out.print("Usuario: ");
            //String usuario = System.console().readLine();
            String usuario = sc.nextLine();
            System.out.print("Contraseña: ");
            //String password = new String(System.console().readPassword());
            String password = sc.nextLine();
            if (loginUsuario(usuario, password)) {
                return usuario;
            } else {
                System.out.println("Usuario o contraseña incorrectos");
            }
        } while (true);
    }


    /**
     * Crea un nuevo usuario
     * Solicita credenciales de nuevo usuario, y si se crea correctamente devuelve
     * true
     *
     * @return true si se creó el usuario
     */
    public static boolean crearUsuario() {
        Connection conexion = Conexion.conectar();
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            System.out.print("Usuario: ");
            String usuario = System.console().readLine();
            System.out.print("Contraseña: ");
            String password = new String(System.console().readPassword());
            int resultado = sentencia.executeUpdate("INSERT INTO user (username, password) VALUES ('" + usuario + "', '"
                    + generarStringHash2Y(password) + "')");
            sentencia.close();
            conexion.close();
            return resultado == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            //System.out.println("Error al crear el usuario");
            return false;
        }
    }


    /**
     * Cambia la contraseña de un usuario
     *
     * @param username Usuario
     * @param password Nueva contraseña
     * @return true si se cambió la contraseña
     */
    public static boolean cambiarPassword(String username, String password) {
        boolean cambiarPassword = false;
        Connection conexion = Conexion.conectar();

        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            int resultado = sentencia.executeUpdate("UPDATE user SET password='" + generarStringHash2Y(password)
                    + "' WHERE username LIKE '" + username + "'");

            if (resultado == 1) {
                // Si se cambió la contraseña
                cambiarPassword = true;
            }

            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cambiarPassword;
    }

}
