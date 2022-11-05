package repositorios;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.ubicaciones.Ubicacion;
import org.junit.jupiter.api.Test;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReposTests {

 /*
    Notas:
        - Los ID's empiezan en 1.
        - Si tengo 5 elementos en la DB, y los borro, al insertar uno nuevo, este tiene ID = 6.
 */

  @Test
  public void sePuedeGuardarYSacarAlgo() {

    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "Localidad", null);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("Panchos Loria", "S.A.", TipoOrganizacion.EMPRESA, ubicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);

    RepositorioOrganizaciones.getInstance().add(organizacion);
    int idOrg = organizacion.getId();
    Organizacion orgDB = RepositorioOrganizaciones.getInstance().get(idOrg);

    assertEquals(organizacion, orgDB);

    RepositorioOrganizaciones.getInstance().clean();

  }

  @Test
  public void sePuedeActualizarAlgo() {

    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "Localidad", null);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("Panchos Loria", "S.A.", TipoOrganizacion.EMPRESA, ubicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);

    RepositorioOrganizaciones.getInstance().add(organizacion);
    int idOrg = organizacion.getId();
    Organizacion org = RepositorioOrganizaciones.getInstance().get(idOrg);

    Sector sectorNuevo = new Sector();
    org.agregarSector(sectorNuevo);

    RepositorioOrganizaciones.getInstance().update(org);

    // Esto es porque no hay repo de sectores y necesitaba todos
    EntityManager em = PerThreadEntityManagers.getEntityManager();
    em.getTransaction().begin();
    List<Sector> listSectores = em.createQuery("from Sector").getResultList();
    em.getTransaction().commit();

    Organizacion orgDB = RepositorioOrganizaciones.getInstance().get(idOrg);

    assertTrue(orgDB.containsSector(listSectores.get(0)));
    assertTrue(orgDB.containsSector(listSectores.get(1)));

    RepositorioOrganizaciones.getInstance().clean();

  }

  @Test
  public void sePuedenPedirTodosDeAlgo() {

    Organizacion org1 = new Organizacion();
    Organizacion org2 = new Organizacion();
    Organizacion org3 = new Organizacion();
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    List<Organizacion> listaOrgs = RepositorioOrganizaciones.getInstance().all();

    assertEquals(3, listaOrgs.size());

    RepositorioOrganizaciones.getInstance().clean();

  }

  @Test
  public void sePuedeBorrarAlgo() {
    Organizacion org1 = new Organizacion();
    Organizacion org2 = new Organizacion();
    Organizacion org3 = new Organizacion();
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    RepositorioOrganizaciones.getInstance().delete(org2);

    List<Organizacion> listaOrgs = RepositorioOrganizaciones.getInstance().all();
    int idOrg2 = org2.getId();

    assertEquals(2, listaOrgs.size());
    assertNull(RepositorioOrganizaciones.getInstance().get(idOrg2));

    RepositorioOrganizaciones.getInstance().clean();
  }

  @Test
  public void sePuedeBorrarTodoDeAlgo() {

    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "Localidad", null);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("Panchos Loria", "S.A.", TipoOrganizacion.EMPRESA, ubicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);
    RepositorioOrganizaciones.getInstance().add(organizacion);

    RepositorioOrganizaciones.getInstance().clean();
    assertTrue(RepositorioOrganizaciones.getInstance().all().isEmpty());

  }

}
