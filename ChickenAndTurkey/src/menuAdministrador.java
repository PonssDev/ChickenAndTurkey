import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Muestra el menú principal del administrador y permite realizar diferentes
 * acciones.
 */
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
                    menuVehiculosAdministrador();
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

    /**
     * Muestra el menú de vehículos del administrador y permite realizar diferentes
     * acciones relacionadas con vehículos.
     */
    public static void menuVehiculosAdministrador() {
        boolean salir = false;
        while (!salir) {
            System.out.println("******* MENU ADMINISTRADOR *******");
            System.out.println();
            System.out.println("VEHICULOS");
            System.out.println();
            System.out.println("1. Consultar vehiculos");
            System.out.println("2. Crear vehiculo");
            System.out.println("3. Editar vehiculo");
            System.out.println("4. Volver atr�s");
            System.out.println("Opcion: ");
            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    consultarVehiculos();
                    break;
                case 2:
                    crearVehiculo();
                    break;
                case 3:
                    editarVehiculo();
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Permite editar la información de un vehículo existente en la base de datos.
     */
    public static void editarVehiculo() {
        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el ID del veh�culo a editar:");
            int idVehiculo = Integer.parseInt(scanner.nextLine());

            // Verificar si el veh�culo existe
            String existenciaQuery = "SELECT * FROM vehiculos WHERE id_vehiculo = ?";
            PreparedStatement existenciaStatement = connection.prepareStatement(existenciaQuery);
            existenciaStatement.setInt(1, idVehiculo);
            ResultSet existenciaResult = existenciaStatement.executeQuery();

            if (existenciaResult.next()) {
                System.out.println("Ingrese el nuevo modelo del veh�culo:");
                String nuevoModelo = scanner.nextLine();
                System.out.println("Ingrese la nueva marca del veh�culo:");
                String nuevaMarca = scanner.nextLine();
                System.out.println("Ingrese el nuevo combustible del veh�culo:");
                String nuevoCombustible = scanner.nextLine();
                System.out.println("Ingrese el nuevo color del veh�culo:");
                String nuevoColor = scanner.nextLine();
                System.out.println("Ingrese el nuevo motor del veh�culo:");
                String nuevoMotor = scanner.nextLine();
                System.out.println("Ingrese la nueva matr�cula del veh�culo:");
                String nuevaMatricula = scanner.nextLine();
                System.out.println("Ingrese la nueva carga m�xima del veh�culo:");
                int nuevaCargaMaxima = Integer.parseInt(scanner.nextLine());

                // Actualizar los datos del veh�culo
                String editarQuery = "UPDATE vehiculos SET modelo = ?, marca = ?, combustible = ?, color = ?, motor = ?, matricula = ?, carga_maxima = ? WHERE id_vehiculo = ?";
                PreparedStatement editarStatement = connection.prepareStatement(editarQuery);
                editarStatement.setString(1, nuevoModelo);
                editarStatement.setString(2, nuevaMarca);
                editarStatement.setString(3, nuevoCombustible);
                editarStatement.setString(4, nuevoColor);
                editarStatement.setString(5, nuevoMotor);
                editarStatement.setString(6, nuevaMatricula);
                editarStatement.setInt(7, nuevaCargaMaxima);
                editarStatement.setInt(8, idVehiculo);
                editarStatement.executeUpdate();

                System.out.println("El veh�culo ha sido editado correctamente.");
            } else {
                System.out.println("No se encontr� ning�n veh�culo con el ID proporcionado.");
            }

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que permite al administrador consultar los vehículos existentes en la
     * base de datos.
     */
    public static void consultarVehiculos() {
        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            String query = "SELECT * FROM vehiculos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                do {
                    int id_vehiculo = resultSet.getInt("id_vehiculo");
                    String modelo = resultSet.getString("modelo");
                    String marca = resultSet.getString("marca");
                    String combustible = resultSet.getString("combustible");
                    String color = resultSet.getString("color");
                    String motor = resultSet.getString("motor");
                    String matricula = resultSet.getString("matricula");
                    int carga_maxima = resultSet.getInt("carga_maxima");

                    System.out.println("ID Vehículo: " + id_vehiculo);
                    System.out.println("Modelo: " + modelo);
                    System.out.println("Marca: " + marca);
                    System.out.println("Combustible: " + combustible);
                    System.out.println("Color: " + color);
                    System.out.println("Motor: " + motor);
                    System.out.println("Matrícula: " + matricula);
                    System.out.println("Carga Máxima: " + carga_maxima);
                    System.out.println();
                } while (resultSet.next());
            } else {
                System.out.println("No se encontraron vehículos en la base de datos.");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al consultar los vehículos: " + e.getMessage());
        }
    }

    /**
     * Método que permite al administrador crear un nuevo vehículo en la base de
     * datos.
     */
    public static void crearVehiculo() {
        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el modelo del veh�culo:");
            String modelo = scanner.nextLine();
            System.out.println("Ingrese la marca del veh�culo:");
            String marca = scanner.nextLine();
            System.out.println("Ingrese el combustible del veh�culo:");
            String combustible = scanner.nextLine();
            System.out.println("Ingrese el color del veh�culo:");
            String color = scanner.nextLine();
            System.out.println("Ingrese el motor del veh�culo:");
            String motor = scanner.nextLine();
            System.out.println("Ingrese la matr�cula del veh�culo:");
            String matricula = scanner.nextLine();
            System.out.println("Ingrese la carga m�xima del veh�culo:");
            int carga_maxima = Integer.parseInt(scanner.nextLine());

            String query = "INSERT INTO vehiculos (modelo, marca, combustible, color, motor, matricula, carga_maxima) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, modelo);
            statement.setString(2, marca);
            statement.setString(3, combustible);
            statement.setString(4, color);
            statement.setString(5, motor);
            statement.setString(6, matricula);
            statement.setInt(7, carga_maxima);
            statement.executeUpdate();
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que muestra el menú de usuarios y permite al administrador seleccionar
     * una opción.
     */
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
                    salir = true; // TODO crear editar usuario.
                    break;
                case 4:
                    return; // Volver atrás al menú principal
                default:
                    break;
            }
        }

    }

    /**
     * Método que muestra el menú de vehículos y permite al administrador
     * seleccionar una opción.
     */
    

    /**
     * Método que muestra el menú de informes y permite al administrador seleccionar
     * una opción.
     */
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
        scanner.close();
    }

    /**
     * Método que permite al administrador crear un nuevo informe y guardarlo en un
     * archivo de texto.
     */
    public static void crearInforme() {

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
        scanner.close();
    }

    /**
     * Método que permite al administrador crear un nuevo usuario en la base de
     * datos.
     */
    public static void crearUsuario() {
        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("¿Desea asignar un vehículo al usuario? (s/n):");
            String asignarVehiculo = scanner.nextLine();
            
            int idVehiculo = -1; // Valor predeterminado si no se asigna un vehículo
            
            if (asignarVehiculo.equalsIgnoreCase("s")) {
                System.out.println("Ingrese la ID del vehículo:");
                idVehiculo = Integer.parseInt(scanner.nextLine());
            }
            
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

            String query = "INSERT INTO trabajadores (dni, nombre, apellidos, edad, numSS, salario, id_roles, id_vehiculo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, dni);
            statement.setString(2, nombre);
            statement.setString(3, apellidos);
            statement.setInt(4, edad);
            statement.setString(5, numSS);
            statement.setDouble(6, salario);
            statement.setInt(7, id_roles);
            statement.setInt(8, idVehiculo);
            statement.executeUpdate();
            
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    /**
     * Método que permite al administrador consultar un usuario en la base de datos.
     */
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

    /**
     * Método que permite al administrador consultar varios usuarios en la base de
     * datos.
     */
    public static void consultarUsuarios() {
        boolean consultarOtro = true;
        do {
            consultarUsuario();
            System.out.println();
            System.out.println("¿Desea consultar otro usuario? (s/n) ");
            String respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("n")) {
                consultarOtro = false;
            }
        } while (consultarOtro);
    }

    /**
     * Método que muestra el menú de carniceros.
     */
    public static void carniceros() {
        System.out.println("******* MENU ADMINISTRADOR *******");
        System.out.println();
    }
}
