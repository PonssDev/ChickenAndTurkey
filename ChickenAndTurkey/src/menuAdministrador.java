import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class menuAdministrador {
    static Scanner teclado = new Scanner(System.in);

    public static void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("******* MENU ADMINISTRADOR *******");
            System.out.println();
            System.out.println("1. Usuarios");
            System.out.println("2. Vehiculos");
            System.out.println("3. Informes");
            System.out.println("4. Salir");
            System.out.println("Opcion: ");
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    menuUsuariosAdministrador();
                    break;
                case 2:
                    menuVehiculosADministrador();
                    break;
                case 3:
                    informes();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    break;
            }

        }
    }

    public static void menuUsuariosAdministrador() {
        boolean salir = false;
        while (!salir) {
            System.out.println("******* MENU ADMINISTRADOR *******");
            System.out.println();
            System.out.println("USUARIOS");
            System.out.println();
            System.out.println("1. Consultar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Volver atrás");
            System.out.println("Opcion: ");
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    consultarUsuarios();
                    break;
                case 2:
                    crearUsuario();
                    break;
                case 3:
                    salir = true;
                    break;
                case 4:
                    return; // Volver atrás al menú principal
                default:
                    break;
            }
        }

    }

    public static void menuVehiculosADministrador() {
        System.out.println("******* MENU ADMINISTRADOR *******");
        System.out.println();
        System.out.println("1. Consualtar vehiculos");
        System.out.println("Crear vehiculo");
        System.out.println("Editar vehiculo");
    }

    public static void informes() {
        System.out.println("******* MENU ADMINISTRADOR *******");
        System.out.println("1. Crear informe");
    }

    public static void crearUsuario() {
        System.out.println("Nombre: ");
    }

    public static void consultarUsuario() {
        System.out.println("DNI: ");
        String dni = teclado.next();

        String urlBD = "jdbc:mysql://localhost:3306/Chickenandturkey";
        String username = "admin";
        String password = "chicken123";

        String query = "SELECT t.DNI, t.nombre, t.apellidos, t.edad, r.rol " +
                "FROM Trabajadores t " +
                "JOIN roles r ON t.id_roles = r.id " +
                "WHERE t.DNI = ?";

        try (Connection conn = DriverManager.getConnection(urlBD, username, password);
                PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String DNI = resultSet.getString("DNI");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                int edad = resultSet.getInt("edad");
                String rol = resultSet.getString("rol");

                System.out.println("DNI: " + DNI);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellidos);
                System.out.println("Edad: " + edad);
                System.out.println("Rol: " + rol);
            } else {
                System.out.println("No se encontró ningún usuario con el DNI proporcionado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el usuario: " + e.getMessage());
        }

    }

    public static void consultarUsuarios() {
        boolean consultarOtro = true;
        do {
            consultarUsuario();
            System.out.println();
            System.out.println("¿Desea consultar otro usuario? (s/n)");
            String respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("n")) {
                consultarOtro = false;
            }
        } while (consultarOtro);
    }

    public static void carniceros() {
        System.out.println("******* MENU ADMINISTRADOR *******");
        System.out.println();
    }
}
