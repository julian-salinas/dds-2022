package domain.repositorios;

import domain.database.EntityManagerHelper;
import domain.organizaciones.miembros.Miembro;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;
import presentacion.Usuario;

import java.util.List;

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

  public List<Miembro> miembrosMismaOrg(Miembro miembro){
    int id = miembro.getId();

    EntityManagerHelper.getEntityManager().getTransaction().begin();
    String query =
        "select * from dds.miembro m2" +
        "where sector_id in (" +
        "select id from dds.sector s2" +
        "where s2.org_id IN (select org.id from dds.miembro m" +
            "join dds.sector s on m.sector_id = s.id" +
            "join dds.organizacion org on s.org_id = org.id" +
            "where m.id = " + id +  "))" +
         "AND m2.id !=" + id;
    List<Miembro> miembroList = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();

    if(miembroList.isEmpty())
      return null;
    return miembroList;
  }
}
