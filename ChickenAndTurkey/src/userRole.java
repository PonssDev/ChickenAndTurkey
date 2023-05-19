public class userRole {
    private int userType;

    public userRole(int userType) {
        this.userType = userType;
    }

    public void showMenu() {
        // Tipos de usuario
        // 1 = Administrador
        // 2 = Administrativo
        if (userType == 1) {
            // Menu administrador
            menuAdministrador.mostrarMenu();
        } else if (userType == 2) {
            // TODO: Menú administrativo
            System.out.println("Menu administrativo");
        } else {
            System.out.println("Tipo de usuario incorrecto");
        }
    }
}
