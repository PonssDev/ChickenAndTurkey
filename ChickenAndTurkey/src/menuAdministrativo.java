import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Clase que representa el menú administrativo de la aplicación.
 * Extiende la clase Principal.
 */

public class menuAdministrativo extends Principal {
    static Scanner teclado = new Scanner(System.in);

    /**
     * Muestra el menú administrativo y permite al usuario seleccionar opciones.
     */
    public static void mostrarMenu() {
        boolean salir = false;
        while (!salir) {
            System.out.println("** MENU ADMINISTRATIVO **");
            System.out.println();
            System.out.println("1. Usuarios");
            System.out.println("2. Vehículos");
            System.out.println("3. Salir");
            System.out.println("Opción: ");
            
            if (teclado.hasNextInt()) {
                int opcion = teclado.nextInt();
                
                switch (opcion) {
                    case 1:
                        menuUsuariosAdministrativo();
                        break;
                    case 2:
                        buscarVehiculo();
                        break;
                    case 3:
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                        break;
                }
            } else {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
                teclado.next(); // Consumir la entrada inválida
            }
        }
    }


    /**
     * Muestra el menú de usuarios administrativo y permite al usuario seleccionar
     * opciones.
     */
    public static void menuUsuariosAdministrativo() {
        System.out.println("** MENU USUARIOS **");
        System.out.println();
        System.out.println("1. Consultar usuarios");
        System.out.println("2. Listar usuarios");
        System.out.println("3. Salir");
        System.out.println("Opción: ");
        
        if (teclado.hasNextInt()) { // Verificar si hay un entero disponible
            int opcion = teclado.nextInt();
            
            switch (opcion) {
                case 1:
                    consultarUsuarios();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    // Salir del menú usuarios
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
                    break;
            }
        } else {
            System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            teclado.next(); // Consumir la entrada inválida para evitar un bucle infinito
        }
    }


    /**
     * Consulta un usuario por su DNI.
     */
    public static void consultarUsuarios() {
        System.out.println("** CONSULTAR USUARIOS **");
        System.out.println();
        System.out.println("Ingrese el DNI del usuario: ");
        String dni = teclado.next();

        // Lógica para consultar usuario por DNI
        boolean usuarioEncontrado = consultarUsuarioPorDNI(dni);
        if (usuarioEncontrado) {
            System.out.println("Usuario encontrado con el DNI: " + dni);
        } else {
            System.out.println("No se encontró ningún usuario con el DNI: " + dni);
            System.out.println("1. Ingresar otro DNI");
            System.out.println("2. Volver atrás");
            System.out.println("Opción: ");
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    consultarUsuarios(); // Vuelve a llamar al método para ingresar otro DNI
                    break;
                case 2:
                    // Volver al menú usuarios administrativo
                    break;
                default:
                    System.out.println("Opción inválida. Volviendo al menú anterior...");
                    break;
            }
        }
    }

    /**
     * Lista todos los usuarios.
     */
    public static void listarUsuarios() {
        String urlBD = "jdbc:mysql://localhost:3306/Chickenandturkey";
        String username = "admin";
        String password = "chicken123";

        String query = "SELECT t.DNI, t.nombre, t.apellidos, t.edad, r.rol " +
                "FROM Trabajadores t " +
                "JOIN roles r ON t.id_roles = r.id";

        try (Connection conn = DriverManager.getConnection(urlBD, username, password);
                PreparedStatement statement = conn.prepareStatement(query)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dni = resultSet.getString("DNI");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                int edad = resultSet.getInt("edad");
                String rol = resultSet.getString("rol");

                System.out.println("DNI: " + dni);
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellidos: " + apellidos);
                System.out.println("Edad: " + edad);
                System.out.println("Rol: " + rol);
                System.out.println("-------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar los usuarios: " + e.getMessage());
        }
    }

    /**
     * Consulta un usuario por su DNI en la base de datos.
     *
     * @param dni el DNI del usuario a consultar.
     * @return true si el usuario es encontrado, false si no se encuentra.
     */
    static boolean consultarUsuarioPorDNI(String dni) {
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

                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                int edad = resultSet.getInt("edad");
                String rol = resultSet.getString("rol");
                System.out.println("Nombre: " + nombre);
                System.out.println("Apellido: " + apellidos);
                System.out.println("Edad: " + edad);
                System.out.println("Rol: " + rol);
                System.out.println("");
                System.out.println("");

                return true; // Usuario encontrado
            } else {
                return false; // Usuario no encontrado
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar el usuario: " + e.getMessage());
            return false; // Error en la consulta
        }
    }

    /**
     * Busca un vehículo por su matrícula.
     */
    public static void buscarVehiculo() {
        System.out.println("** BUSCAR VEHÍCULO **");
        System.out.println();
        System.out.println("Ingrese la matrícula del vehículo: ");
        String matricula = teclado.next();

        // Lógica para buscar vehículo por matrícula
        boolean vehiculoEncontrado = buscarVehiculoPorMatricula(matricula);
        if (!vehiculoEncontrado) {
            System.out.println("No se encontró ningún vehículo con la matrícula: " + matricula);
            System.out.println("1. Ingresar otra matrícula");
            System.out.println("2. Volver atrás");
            System.out.println("Opción: ");
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    buscarVehiculo(); // Vuelve a llamar al método para ingresar otra matrícula
                    break;
                case 2:
                    // Volver al menú administrativo
                    break;
                default:
                    System.out.println("Opción inválida. Volviendo al menú anterior...");
                    break;
            }
        }
    }

    /**
     * Busca un vehículo por su matrícula en la base de datos.
     *
     * @param matricula la matrícula del vehículo a buscar.
     * @return true si el vehículo es encontrado, false si no se encuentra.
     */
    public static boolean buscarVehiculoPorMatricula(String matricula) {
        String urlBD = "jdbc:mysql://localhost:3306/Chickenandturkey";
        String username = "admin";
        String password = "chicken123";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establecer la conexión con la base de datos
            connection = DriverManager.getConnection(urlBD, username, password);

            // Preparar la consulta SQL
            String query = "SELECT * FROM vehiculos WHERE matricula = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, matricula);

            // Ejecutar la consulta
            resultSet = statement.executeQuery();

            // Verificar si se encontró algún vehículo con la matrícula dada
            if (resultSet.next()) {
                // Se encontró el vehículo
                return true;
            } else {
                // No se encontró el vehículo
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Cerrar los recursos
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
