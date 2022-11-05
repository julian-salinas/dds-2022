package repositorios;

import domain.database.EntityManagerHelper;
import repositorios.daos.DAO;
import repositorios.daos.DAOHibernate;
import presentacion.Usuario;

import java.util.List;

public class RepositorioUsuarios extends Repositorio<Usuario>{

  private static RepositorioUsuarios instance = null;

  private RepositorioUsuarios(DAO<Usuario> dao) {
    super(dao);
  }

  public static RepositorioUsuarios getInstance() {
    if (instance == null) {
      instance = new RepositorioUsuarios(new DAOHibernate<>(Usuario.class));
    }
    return instance;
  }

  public Usuario findByUsername(String nombre){

    EntityManagerHelper.getEntityManager().getTransaction().begin();
    String query =
        "FROM Usuario " +
        "WHERE username LIKE " + "'" + nombre + "'";
    List<Usuario> user = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();

    if(user.isEmpty())
      return null;
    return user.get(0);
  }

}