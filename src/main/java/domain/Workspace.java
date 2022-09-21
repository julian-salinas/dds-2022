package domain;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.transporte.*;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Workspace {

  public static void main(String[] args) {

    EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();
    // Organizacion, Sector, Miembro
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("S.A.", TipoOrganizacion.EMPRESA, "Panchos Loria", null, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);
    entityManager.persist(organizacion);
    // Trayecto, TrayectoCompartido, Tramo
    Tramo tramo1 = new Tramo(null);
    Tramo tramo2 = new Tramo(null);
    Tramo tramo3 = new Tramo(null);
    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramo1, tramo2, tramo3);
    miembro.registrarTrayecto(trayecto);
    entityManager.merge(miembro);
    // MedioDeTransporte, MedioNoPublico, y todos los medios de transporte
    TransportePublico transportePublico = new TransportePublico(TipoTransportePublico.SUBTE,
        null, null, null);
    Tramo tramoPublico = new Tramo(transportePublico);
    trayecto = entityManager.find(Trayecto.class, 1);
    trayecto.agregarTramo(tramoPublico);
    entityManager.merge(trayecto);

    VehiculoParticular vehiculoParticular = new VehiculoParticular(TipoDeVehiculo.AUTO, TipoDeCombustible.GASOIL,
        null, null, 500.0);
    Tramo tramoVParticular = new Tramo(vehiculoParticular);
    trayecto = entityManager.find(Trayecto.class, 1);
    trayecto.agregarTramo(tramoVParticular);
    entityManager.merge(trayecto);

    Parada parada_ini = new Parada("Hola", new Distancia(400.0, UnidadDistancia.MTS));
    Parada parada_fin = new Parada("Chau", new Distancia(250.0, UnidadDistancia.MTS));

    TransportePublico transportePublico2 = new TransportePublico(TipoTransportePublico.SUBTE,
        null, parada_ini, parada_fin);
    Tramo tramoPublico2 = new Tramo(transportePublico2);
    trayecto = entityManager.find(Trayecto.class, 1);
    trayecto.agregarTramo(tramoPublico2);
    entityManager.merge(trayecto);

    tx.commit();

    //ObjetoTestPersist objRespuesta = entityManager.find(ObjetoTestPersist.class, obj.id);
    //System.out.println("Id: " + objRespuesta.id + " , Nombre: " + objRespuesta.nombre);
    //List<ObjetoTestPersist> difusiones = entityManager.createQuery("from ObjetoTestPersist").getResultList();

    entityManager.close();
  }
}