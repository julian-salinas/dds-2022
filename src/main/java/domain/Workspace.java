package domain;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Workspace {

  public static void main(String[] args) {

    EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();
    //ObjetoTestPersist obj = new ObjetoTestPersist("Jhon");
    //entityManager.persist(obj);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    entityManager.persist(miembro);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("S.A.", TipoOrganizacion.EMPRESA, "Panchos Loria", null, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);
    entityManager.persist(sector);
    entityManager.persist(organizacion);
    tx.commit();

    //ObjetoTestPersist objRespuesta = entityManager.find(ObjetoTestPersist.class, obj.id);

    //System.out.println("Id: " + objRespuesta.id + " , Nombre: " + objRespuesta.nombre);
    //List<ObjetoTestPersist> difusiones = entityManager.createQuery("from ObjetoTestPersist").getResultList();

    entityManager.close();
  }
}