import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import passwords.Contrasenia;
import passwords.exceptions.*;
import passwords.validaciones.*;

import static org.junit.jupiter.api.Assertions.*;

public class ContraseniaTest {
  private ValidacionContraseniaComun validacionContraseniaComun;
  private ValidacionLongitud validacionLongitud;
  private ValidacionMayusculas validacionMayusculas;
  private ValidacionMinusculas validacionMinusculas;
  private ValidacionNumeros validacionNumeros;

  @BeforeEach
  void init() {
    validacionContraseniaComun = new ValidacionContraseniaComun();
    validacionLongitud = new ValidacionLongitud();
    validacionMayusculas = new ValidacionMayusculas();
    validacionMinusculas = new ValidacionMinusculas();
    validacionNumeros = new ValidacionNumeros();
  }

  @Test
  public void validacionesSeAgreganALaLista() {
    Contrasenia unaContraseniaCualquiera = new Contrasenia();
    unaContraseniaCualquiera.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
      validacionMinusculas, validacionNumeros);

    assertEquals(5, unaContraseniaCualquiera.getCantidadDeValidaciones());

  }

  @Test
  public void contraseniaQueCumpleConTodasLasValidaciones() {
    Contrasenia contrasenia = new Contrasenia();
    contrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertDoesNotThrow(() -> contrasenia.validarContrasenia("MarmotaSalvaje917"));
  }

  @Test
  public void contraseniaQueEsMuyComun() {
    Contrasenia contraseniaComun = new Contrasenia();
    contraseniaComun.setValidaciones(validacionContraseniaComun);

    assertThrows(ContraseniaComunException.class, () -> contraseniaComun.validarContrasenia("dragonballz"));
  }

  @Test
  public void contraseniaDemasiadoCorta() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(LongitudException.class, () -> altaContrasenia.validarContrasenia("Ds1S"));
  }

  @Test
  public void contraseniaQueNoTieneNumeros() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(NumerosException.class, () -> altaContrasenia.validarContrasenia("MarmotaSalvaje"));
  }

  @Test
  public void contraseniaQueNoTieneMayusculas() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(MayusculasException.class, () -> altaContrasenia.validarContrasenia("marmotasalvaje917"));
  }

  @Test
  public void contraseniaQueNoTieneMinusculas() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(MinusculasException.class, () -> altaContrasenia.validarContrasenia("MARMOTASALVAJE917"));
  }

}
