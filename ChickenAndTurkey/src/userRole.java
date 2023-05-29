public class userRole {
    private int userType;
    
    private static final int ADMINISTRATOR = 1;
    private static final int ADMINISTRATIVO = 2;

    public userRole(int userType) {
        this.userType = userType;
    }

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
