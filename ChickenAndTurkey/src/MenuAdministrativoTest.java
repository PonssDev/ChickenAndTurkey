import org.junit.Assert;
import org.junit.Test;

public class MenuAdministrativoTest {

    @Test
    public void testConsultarUsuarioPorDNI() {
        // Arrange
        String dniExistente = "45185631S";
        String dniNoExistente = "00000000X";

        // Act
        boolean usuarioExistente = menuAdministrativo.consultarUsuarioPorDNI(dniExistente);
        boolean usuarioNoExistente = menuAdministrativo.consultarUsuarioPorDNI(dniNoExistente);

        // Assert
        Assert.assertTrue(usuarioExistente);
        Assert.assertFalse(usuarioNoExistente);
    }

    @Test
    public void testBuscarVehiculoPorMatricula() {
        // Arrange
        String matriculaExistente = "XYZ789";
        String matriculaNoExistente = "XYZ789876f";

        // Act
        boolean vehiculoExistente = menuAdministrativo.buscarVehiculoPorMatricula(matriculaExistente);
        System.out.println(vehiculoExistente);
        boolean vehiculoNoExistente = menuAdministrativo.buscarVehiculoPorMatricula(matriculaNoExistente);

        // Assert
        Assert.assertTrue(vehiculoExistente);
        Assert.assertFalse(vehiculoNoExistente);
    }
}