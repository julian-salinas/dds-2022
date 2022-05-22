package administrador;

import passwords.Contrasenia;

public class Administrador {
  String user;
  Contrasenia password;

  public Administrador(String user, Contrasenia password) {
    this.user = user;
    this.password = password;
  }

}
