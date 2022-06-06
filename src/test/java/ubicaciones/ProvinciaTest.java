package ubicaciones;

import domain.ubicaciones.Pais;
import domain.ubicaciones.Provincia;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProvinciaTest {
  Provincia buenosAires;
  Provincia quito;

  @Test
  void sePuedeInstanciarProvinciaBuenosAiresTodoMayuscula() {
    assertDoesNotThrow(() -> buenosAires = new Provincia("BUENOS AIRES"));
  }

  @Test
  void sePuedeInstanciarProvinciaBuenosAiresTodoMinuscula() {
    assertDoesNotThrow(() -> buenosAires = new Provincia("buenos aires"));
  }

  @Test
  void sePuedeInstanciarProvinciaBuenosAiresCapitalized() {
    assertDoesNotThrow(() -> buenosAires = new Provincia("Buenos Aires"));
  }

  @Test
  void noSePuedeInstanciarQuito() {
    assertThrows(RuntimeException.class, () -> quito = new Provincia("QUITO"));
  }
}
