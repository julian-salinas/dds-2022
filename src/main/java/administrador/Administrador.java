package administrador;

import passwords.Contrasenia;

public class Administrador {
  String user;
  Contrasenia contrasenia;

  public Administrador(String user, Contrasenia contrasenia) {
    this.user = user;
    this.contrasenia = contrasenia;
  }

}
