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
  private Contrasenia validadorDeContrasenias;

  @BeforeEach
  void init() {
    validacionContraseniaComun = new ValidacionContraseniaComun();
    validacionLongitud = new ValidacionLongitud();
    validacionMayusculas = new ValidacionMayusculas();
    validacionMinusculas = new ValidacionMinusculas();
    validacionNumeros = new ValidacionNumeros();

    validadorDeContrasenias = new Contrasenia(validacionContraseniaComun, validacionLongitud, validacionMayusculas,
        validacionMinusculas, validacionNumeros);
  }


  @Test
  public void contraseniaQueCumpleConTodasLasValidaciones() {
    assertDoesNotThrow(() -> validadorDeContrasenias.validarContrasenia("MarmotaSalvaje917"));

  }

  @Test
  public void contraseniaQueEsMuyComun() {
    // assertThrows(ContraseniaComunException.class, () -> contraseniaComun.setContrasenia("dragonballz"));
    assertThrows(PasswordException.class, () -> validadorDeContrasenias.validarContrasenia("dragonballz"));
  }

  @Test
  public void contraseniaDemasiadoCorta() {
    // El único problema que tiene la contraseña es que no contiene números
    assertThrows(PasswordException.class, () -> validadorDeContrasenias.validarContrasenia("Ds1S"));
  }

  @Test
  public void contraseniaQueNoTieneNumeros() {
    // El único problema que tiene la contraseña es que no contiene números
    assertThrows(PasswordException.class, () -> validadorDeContrasenias.validarContrasenia("MarmotaSalvaje"));
  }

  @Test
  public void contraseniaQueNoTieneMayusculas() {
    // El único problema que tiene la contraseña es que no contiene números
    assertThrows(PasswordException.class, () -> validadorDeContrasenias.validarContrasenia("marmotasalvaje917"));
  }

  @Test
  public void contraseniaQueNoTieneMinusculas() {
    // El único problema que tiene la contraseña es que no contiene minusculas
    assertThrows(PasswordException.class, () -> validadorDeContrasenias.validarContrasenia("MARMOTASALVAJE917"));
  }
}
