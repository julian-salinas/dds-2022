import static org.junit.jupiter.api.Assertions.*;

import administrador.contrasenia.*;
import administrador.contrasenia.excepciones.*;
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

  @Test
  public void testValidacionNumeros() {
    ValidacionNumeros contraseniaQueNoTieneNumeros = new ValidacionNumeros();
    assertThrows(ExcepcionContraseniaEsNumerica.class, () -> contraseniaQueNoTieneNumeros.validarContrasenia("hola"));
  }

}
