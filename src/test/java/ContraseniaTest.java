import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.passwords.Contrasenia;
import domain.passwords.exceptions.*;
import domain.passwords.validaciones.*;

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

    assertDoesNotThrow(() -> contrasenia.setContrasenia("MarmotaSalvaje917"));

  }

  @Test
  public void contraseniaQueEsMuyComun() {
    Contrasenia contraseniaComun = new Contrasenia();
    contraseniaComun.setValidaciones(validacionContraseniaComun);

    assertThrows(ContraseniaComunException.class, () -> contraseniaComun.setContrasenia("dragonballz"));
  }

  @Test
  public void contraseniaDemasiadoCorta() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(LongitudException.class, () -> altaContrasenia.setContrasenia("Ds1S"));
  }

  @Test
  public void contraseniaQueNoTieneNumeros() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(NumerosException.class, () -> altaContrasenia.setContrasenia("MarmotaSalvaje"));
  }

  @Test
  public void contraseniaQueNoTieneMayusculas() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(MayusculasException.class, () -> altaContrasenia.setContrasenia("marmotasalvaje917"));
  }

  @Test
  public void contraseniaQueNoTieneMinusculas() {
    // El único problema que tiene la contraseña es que no contiene números
    Contrasenia altaContrasenia = new Contrasenia();
    altaContrasenia.setValidaciones(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);

    assertThrows(MinusculasException.class, () -> altaContrasenia.setContrasenia("MARMOTASALVAJE917"));
  }

}
