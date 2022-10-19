package presentacion;

import domain.repositorios.RepositorioUsuarios;

public class Bootstrap {
  public static void init() {
    // Cosas que queramos que pasen al iniciar el server.
    Usuario mati = new Usuario("Matias", "1234", TipoUsuario.MIEMBRO);
    Usuario gonza = new Usuario("Gonzalo", "abcd", TipoUsuario.MIEMBRO);

    if(RepositorioUsuarios.getInstance().findByUsername("Matias") == null) {
      RepositorioUsuarios.getInstance().add(mati);
      RepositorioUsuarios.getInstance().add(gonza);
    }

  }
}
