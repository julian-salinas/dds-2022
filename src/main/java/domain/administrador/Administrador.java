package domain.administrador;

import domain.contrasenias.Validador;

public class Administrador {
  String user;
  Validador validador;

  public Administrador(String user, Validador validador) {
    this.user = user;
    this.validador = validador;
  }

}
