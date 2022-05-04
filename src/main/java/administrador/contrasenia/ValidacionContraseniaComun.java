package administrador.contrasenia;

import administrador.contrasenia.excepciones.ExcepcionContraseniaComun;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class ValidacionContraseniaComun extends Validacion {

  @Override
  public boolean condicion(String password) {
    /*
    Devuelve true si la contraseña no está en la lista de contraseñas comunes
    */

    try {
      File file = new File("/home/juli/Documents/utn/dds/2022-tpa-ju-ma-grupo-05/src/main/java/administrador/contrasenia/common-passwords.txt");
      List<String> passwords = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
      return !passwords.stream().anyMatch(pass -> pass.equals(password));
    }

    catch (FileNotFoundException exception) {
      exception.printStackTrace();
      return false;
    }

    catch (IOException exception) {
      exception.printStackTrace();
      return false;
    }
  }


  @Override
  public RuntimeException error() {
    return new ExcepcionContraseniaComun();
  }
}