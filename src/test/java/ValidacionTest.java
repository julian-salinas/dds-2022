import static org.junit.jupiter.api.Assertions.*;

import administrador.contrasenia.ValidacionContraseniaComun;
import administrador.contrasenia.ValidacionLongitud;
import administrador.contrasenia.excepciones.ExcepcionContraseniaComun;
import administrador.contrasenia.excepciones.ExcepcionLongitudContrasenia;
import org.junit.jupiter.api.Test;

public class ValidacionTest {
  @Test
  public void dragonballzNoEsUnaClaveSegura() {
    ValidacionContraseniaComun contraseniaComun = new ValidacionContraseniaComun();
    assertThrows(ExcepcionContraseniaComun.class, () -> contraseniaComun.validarContrasenia("dragonballz"));
  }

  @Test
  public void messiEsUnaContraseniaMuyCorta() {
    ValidacionLongitud contraseniaCorta = new ValidacionLongitud();
    assertThrows(ExcepcionLongitudContrasenia.class, () -> contraseniaCorta.validarContrasenia("messi"));
  }

}
