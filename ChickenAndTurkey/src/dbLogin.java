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

    public void loginSystem() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce tu usuario:");
        String userInput = sc.nextLine();
        System.out.println("Introduce tu contraseña:");
        String userPassword = sc.nextLine();

        try {
            Connection conn = DriverManager.getConnection(urlBD, username, password);

            // SQL Query
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
