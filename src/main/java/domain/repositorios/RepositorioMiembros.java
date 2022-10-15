package domain.repositorios;

import domain.organizaciones.miembros.Miembro;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;

public class RepositorioMiembros extends Repositorio<Miembro> {

  private static RepositorioMiembros instance = null;

  private RepositorioMiembros(DAO<Miembro> dao) {
    super(dao);
  }

  public static RepositorioMiembros getInstance() {
    if (instance == null) {
      instance = new RepositorioMiembros(new DAOHibernate<>(Miembro.class));
    }
    return instance;
  }
}
