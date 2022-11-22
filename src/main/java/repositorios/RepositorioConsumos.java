package repositorios;

import domain.database.EntityManagerHelper;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import repositorios.daos.DAO;
import repositorios.daos.DAOHibernate;

import java.util.List;

public class RepositorioConsumos extends Repositorio<TipoDeConsumo> {

  private static RepositorioConsumos instance = null;

  private RepositorioConsumos(DAO<TipoDeConsumo> dao) {
    super(dao);
  }

  public static RepositorioConsumos getInstance() {
    if (instance == null) {
      instance = new RepositorioConsumos(new DAOHibernate<>(TipoDeConsumo.class));
    }
    return instance;
  }

  public TipoDeConsumo findByName(String name) {
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    String query =
        "FROM TipoDeConsumo " +
            "WHERE nombre_tipo LIKE " + "'" + name + "'";
    List<TipoDeConsumo> consumos = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();

    if(consumos.isEmpty())
      return null;
    return consumos.get(0);
  }
  public TipoDeConsumo findByID(int id) {
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    String query =
        "FROM TipoDeConsumo " +
            "WHERE id LIKE " + "'" + id + "'";
    List<TipoDeConsumo> consumos = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();

    if(consumos.isEmpty())
      return null;
    return consumos.get(0);
  }

}
