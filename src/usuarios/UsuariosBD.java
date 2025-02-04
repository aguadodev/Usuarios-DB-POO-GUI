package usuarios;

import java.util.Scanner;

public class UsuariosBD {

    /**
     * Método principal de ejemplo
     */
    public static void main(String[] args) {
        String username, password;
        Usuario usuario = null;

        Scanner sc = new Scanner(System.in);
        System.out.println("\n*******************");
        System.out.println("GESTIÓN DE USUARIOS");
        System.out.println("*******************\n");
        System.out.println("BASE DE DATOS: " + Conexion.DATABASE + " en " + Conexion.HOST + ":" + Conexion.PORT);
        String opcion;
        do {
            System.out.println();
            System.out.println("1. LISTADO DE USUARIOS:");
            System.out.println("2. CREACIÓN DE USUARIO");
            System.out.println("3. LOGIN DE USUARIO");
            System.out.println("4. CAMBIO DE CONTRASEÑA");
            System.out.println("0. SALIR");

            System.out.println();
            System.out.print("Opción: ");
            //opcion = System.console().readLine();
            opcion = sc.nextLine();
            System.out.println();

            switch (opcion) {
                case "1":
                    Usuario.findAll();
                    break;
                case "2":
                    System.out.println("CREAR USUARIO");
                    usuario = leerUsuario();
                    System.out.println(usuario.crearBD() ? "Usuario creado en la BD" : "Error al crear el usuario en la BD");
                    break;
                case "3":
                    System.out.println("LOGIN DE USUARIO");
                    usuario = leerUsuario();
                    System.out.println(usuario.login() ? "Login OK" : "Login KO");
                    break;
                case "4":
                    System.out.println("CAMBIO DE CONTRASEÑA");
                    usuario = leerUsuario();
                    System.out.println(
                            usuario.cambiarPassword() ? "Contraseña cambiada en la BD"
                                    : "Error al cambiar la contraseña en la BD");
                    break;
                case "0":
                    System.out.println("Hasta pronto...\n");
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (!opcion.equals("0"));

    }

    private static Usuario leerUsuario() {
        String password;
        String username;
        Scanner sc = new Scanner(System.in);
        System.out.print("Usuario: ");
        username = sc.nextLine();
        System.out.print("Contraseña: ");
        password = sc.nextLine();
        Usuario usuario = new Usuario(username, password);
        return usuario;
    }

}
