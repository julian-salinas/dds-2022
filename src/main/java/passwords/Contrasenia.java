package passwords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import passwords.validaciones.Validacion;

public class Contrasenia {
  private List<Validacion> validaciones;
  private String contrasenia;

  public Contrasenia(String contrasenia, Validacion... validaciones) {

    // Validar contraseña antes de asignarla
    validarContrasenia(contrasenia);
    this.contrasenia = contrasenia;

    // Inicializar lista de validaciones y asignarle valores recibidos
    this.validaciones = new ArrayList<>();
    Collections.addAll(this.validaciones, validaciones);

  }

  public Contrasenia() {

    this.validaciones = new ArrayList<>();

  }

  public void setContrasenia(String contrasenia) {

    validarContrasenia(contrasenia);
    this.contrasenia = contrasenia;

  }

  public void setValidaciones(Validacion... validaciones) {

    Collections.addAll(this.validaciones, validaciones);

  }

  public void validarContrasenia(String unaContrasenia) {

    // Realizar la validacion con cada una de las clases de validación
    this.validaciones.stream().forEach(validacion -> validacion.validarContrasenia(unaContrasenia));

  }

  public int getCantidadDeValidaciones() {

    return this.validaciones.size();

  }

}
