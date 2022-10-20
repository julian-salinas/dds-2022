package domain;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.datos.actividades.DatosActividades;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.sectores.Sector;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.transporte.nopublico.TipoDeCombustible;
import domain.trayecto.transporte.nopublico.TipoDeVehiculo;
import domain.trayecto.transporte.nopublico.VehiculoParticular;
import domain.trayecto.transporte.publico.Linea;
import domain.trayecto.transporte.publico.Parada;
import domain.trayecto.transporte.publico.TipoTransportePublico;
import domain.trayecto.transporte.publico.TransportePublico;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import domain.ubicaciones.distancia.UnidadDistancia;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Workspace {

  public static void main(String[] args) {

    EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();

    // SIN REPOS (FUNCA)

    // Organizacion, Sector, Miembro
    Ubicacion ubicacion = new Ubicacion("rivadavia", 2000, "Localidad", null);
    Miembro miembro = new Miembro("Juan", "Carlos", TipoDeDocumento.DNI, 43567890);
    Sector sector = new Sector();
    sector.agregarMiembro(miembro);
    Organizacion organizacion = new Organizacion("Panchos Loria", "S.A.", TipoOrganizacion.EMPRESA, ubicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    organizacion.agregarSector(sector);
    entityManager.persist(organizacion);

    // Trayecto, TrayectoCompartido, Tramo
    Tramo tramo1 = new Tramo();
    Tramo tramo2 = new Tramo();
    Tramo tramo3 = new Tramo();
    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramo1, tramo2, tramo3);
    miembro.registrarTrayecto(trayecto);
    entityManager.merge(miembro);

    Ubicacion ubicacionVacia = new Ubicacion();

    // MedioDeTransporte, MedioNoPublico, y todos los medios de transporte
    TransportePublico transportePublico = new TransportePublico(TipoTransportePublico.SUBTE,
        null);
    Tramo tramoPublico = new Tramo(transportePublico, ubicacionVacia, ubicacionVacia);
    trayecto = entityManager.find(Trayecto.class, 1);
    trayecto.agregarTramo(tramoPublico);
    entityManager.merge(trayecto);

    VehiculoParticular vehiculoParticular = new VehiculoParticular(TipoDeVehiculo.AUTO
        , TipoDeCombustible.GASOIL, 500.0);
    Tramo tramoVParticular = new Tramo(vehiculoParticular, ubicacionVacia, ubicacionVacia);
    trayecto = entityManager.find(Trayecto.class, 1);
    trayecto.agregarTramo(tramoVParticular);
    entityManager.merge(trayecto);

    // Parada y Linea


    Parada parada_ini = new Parada("Hola", ubicacionVacia, new Distancia(400.0, UnidadDistancia.MTS));
    Parada parada_fin = new Parada("Chau", ubicacionVacia, new Distancia(250.0, UnidadDistancia.MTS));

    Linea linea = new Linea("Linea A", Stream.of(parada_ini, parada_fin).collect(Collectors.toList()));

    entityManager.persist(linea);

    TransportePublico transportePublico2 = new TransportePublico(TipoTransportePublico.SUBTE,
        linea);
    Tramo tramoPublico2 = new Tramo(transportePublico2, parada_ini, parada_fin);
    trayecto = entityManager.find(Trayecto.class, 1);
    trayecto.agregarTramo(tramoPublico2);
    entityManager.merge(trayecto);

    // Datos de Actividades

    organizacion = entityManager.find(Organizacion.class, 1);
    FactorEmision fe = new FactorEmision(55.0, UnidadConsumo.M3);

    DatosActividades datosActividades = new DatosActividades("Gas","22.5","Anual","2022");
    datosActividades.cargarFactorEmision(fe);
    organizacion.setDatosActividades(Stream.of(datosActividades).collect(Collectors.toList()));
    entityManager.merge(organizacion);

    tx.commit();

    //ObjetoTestPersist objRespuesta = entityManager.find(ObjetoTestPersist.class, obj.id);
    //System.out.println("Id: " + objRespuesta.id + " , Nombre: " + objRespuesta.nombre);
    //List<ObjetoTestPersist> difusiones = entityManager.createQuery("from ObjetoTestPersist").getResultList();

    entityManager.close();
  }
}