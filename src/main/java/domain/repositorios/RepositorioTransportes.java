package domain.repositorios;

import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;
import domain.trayecto.transporte.MedioDeTransporte;

public class RepositorioTransportes extends Repositorio<MedioDeTransporte> {

  private static RepositorioTransportes instance = null;

  private RepositorioTransportes(DAO<MedioDeTransporte> dao) {
    super(dao);
  }

  public static RepositorioTransportes getInstance() {
    if (instance == null) {
      instance = new RepositorioTransportes(new DAOHibernate<>(MedioDeTransporte.class));
    }
    return instance;
  }

}
