package ubicaciones;

import domain.ubicaciones.Pais;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaisTest {
  Pais argentina;
  Pais uruguay;

  @Test
  void sePuedeInstanciarPaisArgentinaTodoMayuscula() {
    assertDoesNotThrow(() -> argentina = new Pais("ARGENTINA"));
  }

  @Test
  void sePuedeInstanciarPaisArgentinaTodoMinuscula() {
    assertDoesNotThrow(() -> argentina = new Pais("argentina"));
  }

  @Test
  void sePuedeInstanciarPaisArgentinaCapitalized() {
    assertDoesNotThrow(() -> argentina = new Pais("Argentina"));
  }

  @Test
  void noSePuedeInstanciarUruguay() {
    assertThrows(RuntimeException.class, () -> uruguay = new Pais("URUGUAY"));
  }
}
