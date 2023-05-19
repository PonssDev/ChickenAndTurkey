import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class dbLogin {
    private String urlBD;
    private String username;
    private String password;

    public dbLogin(String urlBD, String username, String password) {
        this.urlBD = urlBD;
        this.username = username;
        this.password = password;
    }

    public void dbLogin() {
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
                System.out.println("Correct"); // Credenciales son correctas
            } else {
                System.out.println("Incorrect"); // Credenciales incorrectas
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}