package domain.repositorios;

import domain.database.EntityManagerHelper;
import domain.repositorios.daos.DAO;
import domain.repositorios.daos.DAOHibernate;
import domain.trayecto.transporte.MedioDeTransporte;
import domain.trayecto.transporte.nopublico.Bicicleta;
import domain.trayecto.transporte.nopublico.Pie;
import domain.trayecto.transporte.nopublico.ServicioContratado;
import domain.trayecto.transporte.publico.TransportePublico;

import java.util.List;

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

  public Pie getPie() {
    String query = "from pie where id = 1";
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    List<Pie> pies = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();
    return pies.get(0);
  }

  public Bicicleta getBicicleta() {
    String query = "from bicicleta where id = 1";
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    List<Bicicleta> bicis = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();
    return bicis.get(0);
  }

  public List<ServicioContratado> allServicioContratado() {
    // Para pedir todos los de un tipo
    String query = "from servicio_contratado";
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    List<ServicioContratado> todos = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();
    return todos;
  }

  public List<TransportePublico> allTransportePublico() {
    // Para pedir todos los de un tipo
    String query = "from transporte_publico";
    EntityManagerHelper.getEntityManager().getTransaction().begin();
    List<TransportePublico> todos = EntityManagerHelper.getEntityManager().createQuery(query).getResultList();
    EntityManagerHelper.getEntityManager().getTransaction().commit();
    return todos;
  }

}