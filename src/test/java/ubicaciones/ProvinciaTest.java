package ubicaciones;

import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.Provincia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProvinciaTest {
  //  ServicioGeoDds apiClient;
  //  Provincia buenosAires;
  //  Provincia quito;
  //
  //  @BeforeEach
  //  void init() throws IOException {
  //    apiClient = mock(ServicioGeoDds.class);
  //    when(apiClient.verificarNombreProvincia("Rio Negro")).thenReturn(7);  //id Provincia = 7
  //  }
  //
  //  @Test
  //  void sePuedeInstanciarProvinciaBuenosAiresTodoMayuscula() {
  //    assertDoesNotThrow(() -> buenosAires = new Provincia("BUENOS AIRES", apiClient));
  //  }
  //
  //  @Test
  //  void sePuedeInstanciarProvinciaBuenosAiresTodoMinuscula() {
  //    assertDoesNotThrow(() -> buenosAires = new Provincia("buenos aires", apiClient));
  //  }
  //
  //  @Test
  //  void sePuedeInstanciarProvinciaBuenosAiresCapitalized() {
  //    assertDoesNotThrow(() -> buenosAires = new Provincia("Buenos Aires", apiClient));
  //  }
  //
  //  @Test
  //  void noSePuedeInstanciarQuito() {
  //    assertThrows(RuntimeException.class, () -> quito = new Provincia("QUITO", apiClient));
  //  }
}
