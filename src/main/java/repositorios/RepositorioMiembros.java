package repositorios;

import domain.organizaciones.Organizacion;
import domain.organizaciones.miembros.Miembro;
import repositorios.daos.DAO;
import repositorios.daos.DAOHibernate;

import java.util.List;
import java.util.stream.Collectors;

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

    /*EntityManagerHelper.getEntityManager().getTransaction().begin();
    String query =
        "from Miembro m2 " +
        "where sector_id in ( " +
        "select id from Sector s2 " +
        "where s2.org_id IN (select org.id from Miembro m " +
            "join Sector s on m.sector_id = s.id " +
            "join Organizacion org on s.org_id = org.id " +
            "where m.id = " + id +  " ))" +
         "AND m2.id != " + id;
    //List<Miembro> miembroList = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();*/


    List<Organizacion> organizaciones = RepositorioOrganizaciones.getInstance().all().stream().filter(org-> org.containsMiembro(miembro)).collect(Collectors.toList());
    List<Miembro> miembroList = organizaciones.stream().flatMap(org -> org.getSectores().stream())
        .flatMap(sector -> sector.getMiembros().stream()).collect(Collectors.toList());

    if(miembroList.isEmpty())
      return null;
    return miembroList;
  }
}
