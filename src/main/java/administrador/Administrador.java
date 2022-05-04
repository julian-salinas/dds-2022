package administrador;

import administrador.contrasenia.Validacion;

public class Administrador {
  String user;
  Validacion password;

  public Administrador (String user, Validacion password){
    this.user = user;
    this.password = password;
  }

}
