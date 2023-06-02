
import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class dbLoginTest {

    @Test
    public void testLoginSystem_WithValidCredentials() {
        // Configurar la conexión a una base de datos de prueba en memoria
        String urlBD = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String username = "admin";
        String password = "chicken123";

        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            // Configurar la conexión de la base de datos en la clase de prueba
            dbLogin login = new dbLogin(urlBD, username, password);

            // Crear una tabla de usuarios para la prueba
            crearTablaUsuarios(connection);

            // Insertar un usuario de prueba en la base de datos
            String usernamePrueba = "testuser";
            String passwordPrueba = "testpassword";
            insertarUsuarioPrueba(connection, usernamePrueba, passwordPrueba);

            // Configurar la entrada estándar con las credenciales de prueba
            String input = usernamePrueba + "\n" + passwordPrueba + "\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            // Ejecutar el método de inicio de sesión
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(outputStream));
            login.loginSystem();

            // Verificar si el menú de usuario se muestra correctamente en la consola
            String expectedOutput = "Mostrando menú de usuario";
            assertEquals(expectedOutput, outputStream.toString().trim());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoginSystem_WithInvalidCredentials() {
        // Configurar la conexión a una base de datos de prueba en memoria
        String urlBD = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String username = "admin";
        String password = "chicken123";

        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            // Configurar la conexión de la base de datos en la clase de prueba
            dbLogin login = new dbLogin(urlBD, username, password);

            // Crear una tabla de usuarios para la prueba
            crearTablaUsuarios(connection);

            // Configurar la entrada estándar con credenciales incorrectas
            String input = "invaliduser\ninvalidpassword\n";
            System.setIn(new ByteArrayInputStream(input.getBytes()));

            // Ejecutar el método de inicio de sesión
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new java.io.PrintStream(outputStream));
            login.loginSystem();

            // Verificar si se muestra el mensaje de credenciales incorrectas en la consola
            String expectedOutput = "Credenciales incorrectas";
            assertEquals(expectedOutput, outputStream.toString().trim());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método de utilidad para crear una tabla de usuarios en la base de datos de
    // prueba
    private void crearTablaUsuarios(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE usuarios (username VARCHAR(50), password VARCHAR(50))";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
            statement.executeUpdate();
        }
    }

    // Método de utilidad para insertar un usuario de prueba en la base de datos
    private void insertarUsuarioPrueba(Connection connection, String username, String password) throws SQLException {
        String insertSQL = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
        }
    }
}
