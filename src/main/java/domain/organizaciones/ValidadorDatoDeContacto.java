package domain.organizaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorDatoDeContacto {

  private static ValidadorDatoDeContacto validadorDatoDeContacto = null;

  private static final String WHATSAPP_PATTERN = "[0-9]{13}";
  private static final String EMAIL_PATTERN =
      "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
          + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

  private static final Pattern email_pattern = Pattern.compile(EMAIL_PATTERN);
  private static final Pattern whatsApp_pattern = Pattern.compile(WHATSAPP_PATTERN);

  public static ValidadorDatoDeContacto getInstancia() {
    if (validadorDatoDeContacto == null) {
      validadorDatoDeContacto = new ValidadorDatoDeContacto();
    }
    return validadorDatoDeContacto;
  }

  public boolean emailEsValido(String email) {
    Matcher matcher = email_pattern.matcher(email);
    return matcher.matches();
  }

  public boolean whatsAppEsValido(String whatsapp) {
    Matcher matcher = whatsApp_pattern.matcher(whatsapp);
    return matcher.matches();
  }

  public void validarEmail(String email) throws RuntimeException {
    if (!emailEsValido(email)) {
      throw new RuntimeException("El email ingresado no es válido");
    }
  }

  public void validarWhatsapp(String whatsapp) throws RuntimeException {
    if (!whatsAppEsValido(whatsapp)) {
      throw new RuntimeException("El número de WhatsApp ingresado no es válido");
    }
  }
}
