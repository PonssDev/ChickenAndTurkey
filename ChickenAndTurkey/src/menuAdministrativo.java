import java.util.Scanner;

public class menuAdministrativo {
    private Scanner scanner;
    
    public menuAdministrativo() {
        scanner = new Scanner(System.in);
    }
    
    public void mostrarMenu() {
        int opcion;
        
        do {
            System.out.println("------ Menú ------");
            System.out.println("1. Opción 1");
            System.out.println("2. Opción 2");
            System.out.println("3. Opción 3");
            System.out.println("4. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();
            
            switch (opcion) {
                case 1:
                    System.out.println("Ha seleccionado la opción 1.");
                    // Lógica para la opción 1
                    break;
                    
                case 2:
                    System.out.println("Ha seleccionado la opción 2.");
                    // Lógica para la opción 2
                    break;
                    
                case 3:
                    System.out.println("Ha seleccionado la opción 3.");
                    // Lógica para la opción 3
                    break;
                    
                case 4:
                    System.out.println("Saliendo del programa...");
                    break;
                    
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
            
            System.out.println();
        } while (opcion != 4);
    }
}

