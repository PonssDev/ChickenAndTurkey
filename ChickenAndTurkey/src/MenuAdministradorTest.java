import org.junit.Assert;
import org.junit.Test;

import java.sql.*;

public class MenuAdministradorTest {

    // ...

    @Test
    public void testConsultaTrabajador() {
        // Configuración de la conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/chickenandturkey";
        String username = "admin";
        String password = "chicken123";

        try {
            // Establecer conexión con la base de datos
            Connection connection = DriverManager.getConnection(url, username, password);

            // Consulta SQL para obtener los datos del trabajador
            String sql = "SELECT dni, nombre, apellido, edad, numss, salario, id_roles, id_vehiculo " +
                    "FROM trabajadores " +
                    "WHERE dni = '45185631S'";

            // Crear una declaración para ejecutar la consulta
            Statement statement = connection.createStatement();

            // Ejecutar la consulta y obtener los resultados
            ResultSet resultSet = statement.executeQuery(sql);

            // Verificar si se encontraron resultados
            Assert.assertTrue(resultSet.next());

            // Obtener los datos del trabajador
            String dni = resultSet.getString("dni");
            String nombre = resultSet.getString("nombre");
            String apellido = resultSet.getString("apellido");
            int edad = resultSet.getInt("edad");
            String numss = resultSet.getString("numss");
            double salario = resultSet.getDouble("salario");
            int idRoles = resultSet.getInt("id_roles");
            int idVehiculo = resultSet.getInt("id_vehiculo");

            // Verificar los valores obtenidos
            Assert.assertEquals("45185631S", dni);
            Assert.assertEquals("Daniel", nombre);
            Assert.assertEquals("Juan", apellido);
            Assert.assertEquals(18, edad);
            Assert.assertEquals("123124", numss);
            Assert.assertEquals(12000.0, salario, 0.01);
            Assert.assertEquals(5, idRoles);
            Assert.assertEquals(1, idVehiculo);

            // Cerrar la conexión y liberar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConsultaVehiculo() {
        // Configuración de la conexión a la base de datos
        String url = "jdbc:mysql://localhost:3306/chickenandturkey";
        String username = "admin";
        String password = "chicken123";

        try {
            // Establecer conexión con la base de datos
            Connection connection = DriverManager.getConnection(url, username, password);

            // Consulta SQL para obtener los datos del vehículo
            String sql = "SELECT modelo, id_vehiculo, marca, combustible, color, motor, matricula, carga_maxima " +
                    "FROM vehiculos " +
                    "WHERE id_vehiculo = 1";

            // Crear una declaración para ejecutar la consulta
            Statement statement = connection.createStatement();

            // Ejecutar la consulta y obtener los resultados
            ResultSet resultSet = statement.executeQuery(sql);

            // Verificar si se encontraron resultados
            Assert.assertTrue(resultSet.next());

            // Obtener los datos del vehículo
            String modelo = resultSet.getString("modelo");
            int idVehiculo = resultSet.getInt("id_vehiculo");
            String marca = resultSet.getString("marca");
            String combustible = resultSet.getString("combustible");
            String color = resultSet.getString("color");
            String motor = resultSet.getString("motor");
            String matricula = resultSet.getString("matricula");
            double cargaMaxima = resultSet.getDouble("carga_maxima");

            // Verificar los valores obtenidos
            Assert.assertEquals("Toyota Camry", modelo);
            Assert.assertEquals(1, idVehiculo);
            Assert.assertEquals("Toyota", marca);
            Assert.assertEquals("Gasolina", combustible);
            Assert.assertEquals("Blanco", color);
            Assert.assertEquals("2.5L", motor);
            Assert.assertEquals("XYZ789", matricula);
            Assert.assertEquals(550, cargaMaxima, 0.01);

            // Cerrar la conexión y liberar recursos
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
