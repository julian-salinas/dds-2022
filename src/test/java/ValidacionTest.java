import static org.junit.jupiter.api.Assertions.*;

import administrador.contrasenia.ValidacionContraseniaComun;
import administrador.contrasenia.ValidacionLongitud;
import administrador.contrasenia.ValidacionMayusculas;
import administrador.contrasenia.ValidacionMinusculas;
import administrador.contrasenia.excepciones.ExcepcionContraseniaComun;
import administrador.contrasenia.excepciones.ExcepcionContraseniaNoContieneMayusculas;
import administrador.contrasenia.excepciones.ExcepcionContraseniaNoContieneMinusculas;
import administrador.contrasenia.excepciones.ExcepcionLongitudContrasenia;
import org.junit.jupiter.api.Test;

public class ValidacionTest {
  @Test
  public void validacionContraseniaComunTest() {
    ValidacionContraseniaComun contraseniaComun = new ValidacionContraseniaComun();
    assertThrows(ExcepcionContraseniaComun.class, () -> contraseniaComun.validarContrasenia("dragonballz"));
  }

  @Test
  public void validacionLongitudTest() {
    ValidacionLongitud contraseniaCorta = new ValidacionLongitud();
    assertThrows(ExcepcionLongitudContrasenia.class, () -> contraseniaCorta.validarContrasenia("messi"));
  }

  @Test
  public void testValidacionMinusculas() {
    ValidacionMinusculas contraseniaSinMinusculas = new ValidacionMinusculas();
    assertThrows(ExcepcionContraseniaNoContieneMinusculas.class, () -> contraseniaSinMinusculas.validarContrasenia("TODOMAYUSCULA"));
  }

  @Test
  public void testValidacionMayusculas() {
    ValidacionMayusculas contraseniaSinMayusculas = new ValidacionMayusculas();
    assertThrows(ExcepcionContraseniaNoContieneMayusculas.class, () -> contraseniaSinMayusculas.validarContrasenia("todominuscula"));
  }

}
