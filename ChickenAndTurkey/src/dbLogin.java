import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Clase que representa un sistema de inicio de sesión en una base de datos.
 * Permite autenticar usuarios mediante una conexión a la base de datos y
 * verificar las credenciales proporcionadas.
 *
 */
class dbLogin {
    private String urlBD;
    private String username;
    private String password;

    /**
     * Crea una nueva instancia de dbLogin con los parámetros de conexión
     * especificados.
     *
     * @param urlBD    URL de la base de datos
     * @param username Nombre de usuario para la conexión a la base de datos
     * @param password Contraseña para la conexión a la base de datos
     */
    public dbLogin(String urlBD, String username, String password) {
        this.urlBD = urlBD;
        this.username = username;
        this.password = password;
    }

    /**
     * Inicia el sistema de inicio de sesión.
     * Solicita al usuario el nombre de usuario y la contraseña, y verifica las
     * credenciales en la base de datos.
     * Muestra el menú correspondiente al tipo de usuario si las credenciales son
     * correctas.
     * Cierra la conexión y los recursos utilizados.
     */
    public void loginSystem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu usuario:");
        String userInput = sc.nextLine();
        System.out.println("Introduce tu contraseña:");
        String userPassword = sc.nextLine();

        try {
            Connection conn = DriverManager.getConnection(urlBD, username, password);

            // Consulta SQL
            String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userInput);
            statement.setString(2, userPassword);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int userType = result.getInt("user_role");
                userRole userMenu = new userRole(userType);
                userMenu.showMenu();
                sc.close();
            } else {
                System.out.println("Credenciales incorrectas");
                sc.close();
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
