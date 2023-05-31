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
                System.out.println("Error: entrada invÃ¡lida. Por favor, ingrese un nÃºmero entero vÃ¡lido.");
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
            System.out.println("4. Volver atrás");
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
    public static void editarVehiculo() {
        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el ID del vehículo a editar:");
            int idVehiculo = Integer.parseInt(scanner.nextLine());

            // Verificar si el vehículo existe
            String existenciaQuery = "SELECT * FROM vehiculos WHERE id_vehiculo = ?";
            PreparedStatement existenciaStatement = connection.prepareStatement(existenciaQuery);
            existenciaStatement.setInt(1, idVehiculo);
            ResultSet existenciaResult = existenciaStatement.executeQuery();

            if (existenciaResult.next()) {
                System.out.println("Ingrese el nuevo modelo del vehículo:");
                String nuevoModelo = scanner.nextLine();
                System.out.println("Ingrese la nueva marca del vehículo:");
                String nuevaMarca = scanner.nextLine();
                System.out.println("Ingrese el nuevo combustible del vehículo:");
                String nuevoCombustible = scanner.nextLine();
                System.out.println("Ingrese el nuevo color del vehículo:");
                String nuevoColor = scanner.nextLine();
                System.out.println("Ingrese el nuevo motor del vehículo:");
                String nuevoMotor = scanner.nextLine();
                System.out.println("Ingrese la nueva matrícula del vehículo:");
                String nuevaMatricula = scanner.nextLine();
                System.out.println("Ingrese la nueva carga máxima del vehículo:");
                int nuevaCargaMaxima = Integer.parseInt(scanner.nextLine());

                // Actualizar los datos del vehículo
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

                System.out.println("El vehículo ha sido editado correctamente.");
            } else {
                System.out.println("No se encontró ningún vehículo con el ID proporcionado.");
            }

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void consultarVehiculos() {
        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            String query = "SELECT * FROM vehiculos";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
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
                System.out.println("No se encontró ningún usuario con el DNI proporcionado.");
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al consultar el usuario: " + e.getMessage());
        }
    }
    public static void crearVehiculo() {
        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el modelo del vehículo:");
            String modelo = scanner.nextLine();
            System.out.println("Ingrese la marca del vehículo:");
            String marca = scanner.nextLine();
            System.out.println("Ingrese el combustible del vehículo:");
            String combustible = scanner.nextLine();
            System.out.println("Ingrese el color del vehículo:");
            String color = scanner.nextLine();
            System.out.println("Ingrese el motor del vehículo:");
            String motor = scanner.nextLine();
            System.out.println("Ingrese la matrícula del vehículo:");
            String matricula = scanner.nextLine();
            System.out.println("Ingrese la carga máxima del vehículo:");
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
            System.out.println("4. Volver atrÃ¡s");
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
                    return; // Volver atrÃ¡s al menÃº principal
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
                System.out.println("OpciÃ³n no valida.");
                break;
        }
        scanner.close();
    }

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
            System.out.println("Ingrese el nÃºmero de seguridad social del usuario:");
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
                System.out.println("No se encontrÃ³ ningÃºn usuario con el DNI proporcionado.");
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
            System.out.println("Â¿Desea consultar otro usuario? (s/n) ");
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
