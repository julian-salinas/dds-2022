package domain.contrasenias.validaciones;

import domain.contrasenias.excepciones.PasswordException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

public class ValidacionContraseniaComun implements Validacion {

  static final String COMMON_PASSWORDS_LOCATION = "public/files/common-passwords.txt";

  @Override
  public boolean condicion(String password) {
    /*
    Devuelve true si la contraseña no está en la lista de contraseñas comunes
    */

    return !palabraEstaEnArchivo(password);

  }

  private InputStream getFileFromResourceAsStream() {
    // The class loader that loaded the class
    ClassLoader classLoader = getClass().getClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream(COMMON_PASSWORDS_LOCATION);

    // the stream holding the file content
    if (inputStream == null) {
      throw new IllegalArgumentException("Archivo no encontrado en la ruta: " + COMMON_PASSWORDS_LOCATION);
    } else {
      return inputStream;
    }
  }

  public boolean palabraEstaEnArchivo(String unPassword) {
    Scanner unArchivo = new Scanner(getFileFromResourceAsStream(), "UTF-8");
    while (unArchivo.hasNextLine()) {
      String line = unArchivo.nextLine();
      if (line.equals(unPassword)) {
        unArchivo.close();
        return true;
      }
    }
    return false;
  }

  @Override
  public PasswordException error() {
    return new PasswordException("Tu contraseña es muy debil");
  }
}