package usuarios;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;

public class Usuario {
    Integer id;
    String username;
    String password;
    LocalDateTime createdAt;

    public Usuario(Integer id, String username, String password, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public Usuario(String username) {
        this.username = username;
    }

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public boolean login() {
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
                loginOk = Util.validarHash2Y(password, resultado.getString("password"));
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginOk;
    }


    public boolean crearBD() {
        Connection conexion = Conexion.conectar();
        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            int resultado = sentencia.executeUpdate("INSERT INTO user (username, password) VALUES ('" + username + "', '"
                    + Util.generarStringHash2Y(password) + "')");
            sentencia.close();
            conexion.close();
            return resultado == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cambiarPassword() {
        boolean cambiarPassword = false;
        Connection conexion = Conexion.conectar();

        Statement sentencia;
        try {
            sentencia = conexion.createStatement();
            int resultado = sentencia.executeUpdate("UPDATE user SET password='" + Util.generarStringHash2Y(password)
                    + "' WHERE username LIKE '" + username + "'");

            cambiarPassword = resultado == 1;

            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cambiarPassword;
    }

    /**
     * Lista los usuarios de la base de datos
     */
    public static void findAll() {
        Connection conexion = Conexion.conectar();

        Statement sentencia;
        try {
            sentencia = conexion.createStatement();

            ResultSet resultado = sentencia.executeQuery("SELECT * FROM user");

            while (resultado.next()) {
                // Procesa los datos
                int id = resultado.getInt("id");
                String username = resultado.getString("username");
                //String password = resultado.getString("password");
                Timestamp createdAt = resultado.getTimestamp("created_at");

                // Procesa los datos
                System.out.println(
                        "ID: " + id + ", username: " + username + ", createdAt: " + createdAt);
            }

            resultado.close();
            sentencia.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
