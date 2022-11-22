package repositorios;

import domain.trayecto.transporte.publico.Linea;
import repositorios.daos.DAO;
import repositorios.daos.DAOHibernate;

public class RepositorioLineas extends Repositorio<Linea> {
  private static RepositorioLineas instance = null;

  private RepositorioLineas(DAO<Linea> dao) {
    super(dao);
  }

  public static RepositorioLineas getInstance() {
    if (instance == null) {
      instance = new RepositorioLineas(new DAOHibernate<>(Linea.class));
    }
    return instance;
  }

}
