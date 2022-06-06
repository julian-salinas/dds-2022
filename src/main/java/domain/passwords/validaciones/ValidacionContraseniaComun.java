package domain.passwords.validaciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import domain.passwords.exceptions.ContraseniaComunException;

public class ValidacionContraseniaComun extends Validacion {

  @Override
  public boolean condicion(String password) {
    /*
    Devuelve true si la contraseña no está en la lista de contraseñas comunes
    */

    try {

      String pathToFile = new File(".")
          .getCanonicalPath()
          .concat("/src/main/java/domain/passwords/validaciones/common-passwords.txt");

      File file = new File(pathToFile);
      List<String> passwords = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));

      return !passwords.stream().anyMatch(pass -> pass.equals(password));

    } catch (FileNotFoundException exception) {

      exception.printStackTrace();
      return false;

    } catch (IOException exception) {

      exception.printStackTrace();
      return false;

    }
  }

  @Override
  public RuntimeException error() {
    return new ContraseniaComunException();
  }
}