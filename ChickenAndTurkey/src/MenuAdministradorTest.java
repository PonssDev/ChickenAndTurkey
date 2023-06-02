import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
// TODO Revisar si está correcto
public class MenuAdministradorTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(errorStream));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Before
    public void setUpDatabase() {
        // Set up a temporary test database
        String urlBD = "jdbc:mysql://localhost:3306/";
        String username = "admin";
        String password = "chicken123";
        try (Connection connection = DriverManager.getConnection(urlBD, username, password);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS test_db");
            statement.executeUpdate("USE test_db");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS vehiculos (" +
                    "id_vehiculo INT PRIMARY KEY AUTO_INCREMENT," +
                    "modelo VARCHAR(255)," +
                    "marca VARCHAR(255)," +
                    "combustible VARCHAR(255)," +
                    "color VARCHAR(255)," +
                    "motor VARCHAR(255)," +
                    "matricula VARCHAR(255)," +
                    "carga_maxima INT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDownDatabase() {
        // Clean up the temporary test database
        String urlBD = "jdbc:mysql://localhost:3306/";
        String username = "admin";
        String password = "chicken123";
        try (Connection connection = DriverManager.getConnection(urlBD, username, password);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP DATABASE IF EXISTS test_db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMostrarMenu_OpcionSalir() {
        // Simulate user input: choose option 4 to exit
        InputStream inputStream = new ByteArrayInputStream("4".getBytes());
        System.setIn(inputStream);

        menuAdministrador.mostrarMenu();

        String output = outputStream.toString().trim();
        assertEquals("******* MENU ADMINISTRADOR *******\n" +
                "\n" +
                "1. Usuarios\n" +
                "2. Vehiculos\n" +
                "3. Informes\n" +
                "4. Salir\n" +
                "Opcion: ", output);
    }

    @Test
    public void testMenuVehiculosAdministrador_OpcionSalir() {
        // Simulate user input: choose option 4 to exit
        InputStream inputStream = new ByteArrayInputStream("4".getBytes());
        System.setIn(inputStream);

        menuAdministrador.menuVehiculosAdministrador();

        String output = outputStream.toString().trim();
        assertEquals("******* MENU ADMINISTRADOR *******\n" +
                "\n" +
                "VEHICULOS\n" +
                "\n" +
                "1. Consultar vehiculos\n" +
                "2. Crear vehiculo\n" +
                "3. Editar vehiculo\n" +
                "4. Volver atrás\n" +
                "Opcion: ", output);
    }
}
