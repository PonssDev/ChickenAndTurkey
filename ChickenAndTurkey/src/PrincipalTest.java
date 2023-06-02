import static org.junit.Assert.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PrincipalTest {

    @Test
    public void testMain_WithSuccessfulConnection() {
        // Configurar la conexión a una base de datos de prueba en memoria
        String urlBD = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String username = "admin";
        String password = "chicken123";

        // Configurar la salida estándar para capturarla en un objeto
        // ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        try (Connection connection = DriverManager.getConnection(urlBD, username, password)) {
            // Ejecutar el método main
            Principal.main(null);

            // Verificar si el mensaje de conexión exitosa se muestra correctamente en la
            // consola
            String expectedOutput = "Conexión exitosa a la base de datos chickenandturkey.";
            assertEquals(expectedOutput, outputStream.toString().trim());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMain_WithFailedConnection() {
        // Configurar la conexión a una base de datos inválida
        // Configurar la salida estándar para capturarla en un objeto
        // ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Ejecutar el método main
        Principal.main(null);

        // Verificar si se muestra el mensaje de error de conexión en la consola
        String expectedOutput = "Error al conectar a la base de datos:";
        assertTrue(outputStream.toString().trim().startsWith(expectedOutput));
    }
}
