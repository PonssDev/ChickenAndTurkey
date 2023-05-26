import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class menuAdministrador {
    static Scanner teclado = new Scanner(System.in);

    static String urlBD = "jdbc:mysql://localhost:3306/Chickenandturkey";
    static String username = "admin";
    static String password = "chicken123";

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
            int opcion;

            try {
                opcion = teclado.nextInt();
            } catch (Exception e) {
                System.out.println("Error: entrada inválida. Por favor, ingrese un número entero válido.");
                teclado.nextLine();
                continue;
            }

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
        System.out.println("2. Crear vehiculo");
        System.out.println("3. Editar vehiculo");
    }

    public static void informes() {
        System.out.println("******* MENU ADMINISTRADOR *******");
        System.out.println("1. Crear informe");

        Scanner scanner = new Scanner(System.in);
        int opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                crearInforme();
                break;

            default:
                System.out.println("Opción no valida.");
                break;
        }
    }

    private static void crearInforme() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre del informe: ");
        String nombreInforme = scanner.nextLine();

        System.out.println("Introducta el asunto del informe: ");
        String asuntoInforme = scanner.nextLine();

        System.out.println("Introduzca el mensaje del informe: ");
        String mensajeInforme = scanner.nextLine();

        String rutaCarpeta = "C://informes";
        String rutaArchivo = rutaCarpeta + "//" + nombreInforme + ".txt";

        File carpeta = new File(rutaCarpeta);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        try {
            FileWriter escritor = new FileWriter(rutaArchivo);
            escritor.write("Nombre: " + nombreInforme + "\n");
            escritor.write("Asunto: " + asuntoInforme + "\n");
            escritor.write("Mensaje: " + mensajeInforme + "\n");
            escritor.close();

            System.out.println("Informe creado exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al crear el informe: " + e.getMessage());
        }
    }

    public static void crearUsuario() {

        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el DNI del usuario:");
            String dni = scanner.nextLine();
            System.out.println("Ingrese el nombre del usuario:");
            String nombre = scanner.nextLine();
            System.out.println("Ingrese los apellidos del usuario:");
            String apellidos = scanner.nextLine();
            System.out.println("Ingrese la edad del usuario:");
            int edad = Integer.parseInt(scanner.nextLine());
            System.out.println("Ingrese el número de seguridad social del usuario:");
            String numSS = scanner.nextLine();
            System.out.println("Ingrese el salario del usuario:");
            double salario = Double.parseDouble(scanner.nextLine());
            System.out.println("Ingrese el ID de roles del usuario:");
            int id_roles = Integer.parseInt(scanner.nextLine());

            String query = "INSERT INTO trabajadores (dni, nombre, apellidos, edad, numSS, salario, id_roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, dni);
            statement.setString(2, nombre);
            statement.setString(3, apellidos);
            statement.setInt(4, edad);
            statement.setString(5, numSS);
            statement.setDouble(6, salario);
            statement.setInt(7, id_roles);
            statement.executeUpdate();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
