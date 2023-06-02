/**
 * Clase que representa el rol de un usuario en el sistema.
 */
public class userRole {
    private int userType;
    
    private static final int ADMINISTRATOR = 1;
    private static final int ADMINISTRATIVO = 2;

    /**
     * Constructor de la clase userRole.
     *
     * @param userType el tipo de usuario.
     */
    public userRole(int userType) {
        this.userType = userType;
    }

    /**
     * Muestra el menú correspondiente al tipo de usuario.
     */
    public void showMenu() {
        if (userType == ADMINISTRATOR) {
            menuAdministrador.mostrarMenu();
        } else if (userType == ADMINISTRATIVO) {
            menuAdministrativo.mostrarMenu();
        } else {
            System.out.println("Tipo de usuario incorrecto");
        }
    }
}