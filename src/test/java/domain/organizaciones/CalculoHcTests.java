package domain.organizaciones;

import domain.organizaciones.datos.actividades.Actividad;
import domain.organizaciones.datos.actividades.Alcance;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.organizaciones.miembros.Miembro;
import domain.organizaciones.miembros.TipoDeDocumento;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.hc.HC;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.sectores.Sector;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.nopublico.Bicicleta;
import domain.trayecto.transporte.nopublico.Pie;
import domain.trayecto.transporte.nopublico.ServicioContratado;
import domain.trayecto.transporte.nopublico.TipoServicioContratado;
import domain.ubicaciones.Ubicacion;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositorios.RepositorioConsumos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*

public class CalculoHcTests {

  ServicioGeoDds apiClient;

  String pathCsv = "src/test/resources/files/archivo-prueba.csv";

  Ubicacion unaUbicacion;
  Organizacion organizacion;
  Sector sectorDefault;

  Miembro miembro1;
  Miembro miembro2;

  TrayectoCompartido trayectoCompartido;

  @BeforeEach
  public void init() throws IOException {

    apiClient = mock(ServicioGeoDds.class);

    unaUbicacion = new Ubicacion("Calle", 1200, apiClient);

    organizacion = new Organizacion("Cola-Coca", "RazonX", TipoOrganizacion.EMPRESA, unaUbicacion, ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);
    sectorDefault = new Sector();
    organizacion.agregarSector(sectorDefault);
    miembro1 = new Miembro("Pepito", "Martinez", TipoDeDocumento.DNI, 45869305);
    miembro2 = new Miembro("Juanito", "Hernandez", TipoDeDocumento.DNI, 47869206);
    sectorDefault.agregarMiembro(miembro1);
    sectorDefault.agregarMiembro(miembro2);

    cargarTiposDeConsumo();
    organizacion.cargarMediciones(pathCsv);

    Pie tramoPie = new Pie();
    Tramo primerTramom1 = new Tramo(tramoPie, unaUbicacion, unaUbicacion);
    Trayecto trayecto1 = new Trayecto();
    trayecto1.agregarTramo(primerTramom1);
    miembro1.agregarTrayecto(trayecto1);

    Bicicleta tramoBici = new Bicicleta();
    Tramo primerTramom2 = new Tramo(tramoBici, unaUbicacion, unaUbicacion);
    Trayecto trayecto2 = new Trayecto();
    trayecto2.agregarTramo(primerTramom2);
    miembro2.agregarTrayecto(trayecto2);

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(taxi, 0.15);
    Tramo tramo = new Tramo(servicioContratado, unaUbicacion, unaUbicacion);
    trayectoCompartido = new TrayectoCompartido();
    // miembros, tramos
    trayectoCompartido.agregarTramo(tramo);
    trayectoCompartido.agregarAcompanante(miembro2);

    miembro1.registrarTrayecto(trayectoCompartido);

    when(apiClient.distanciaEntreUbicaciones(any(Ubicacion.class), any(Ubicacion.class))).thenReturn(4000.0);
    organizacion.cargarDATransladoMiembros();
  }

  @Test
  public void sinDatosDeActividadesDa0() {

    Organizacion org = new Organizacion("Panas", "S.A.", TipoOrganizacion.EMPRESA, null,
        ClasificacionOrg.EMPRESA_SECTOR_SECUNDARIO);

    HC hcMensualOrg = org.hcMensual();

    assertEquals(0, (int) hcMensualOrg.enKgCO2());
    RepositorioConsumos.getInstance().clean();
  }

  @Test
  public void SeCalculaElHCMensualDeUnaOrganizacion() {

    HC hcMensualOrg = organizacion.hcMensual();

    // Calculo realizado:
    // Archivo CSV: Gas natural: 1234, mensual; Electricidad: 567, anual; Nafta: 89, mensual
    // valor gas natural * valor fe gas natural + valor electricidad / 12 * valor fe electricidad + ...
    // 1234 * 30.5 + 567 * 30.5 / 12 + 89 * 30.5 = 41792.625
    // Se le suma el transporte de miembros, miembro1 va a pie, miembro2 en bici, y ambos comparten un trayecto en taxi de 4km, el taxi usa 0.15Lts de combustible por KM
    // 41792.625 + combustible utilizado para el transporte de miembros * 30 (para hacerlo un valor mensual) * valor fe distancia
    // 41792.625 + 4 * 0.15 * 30 * 30.5
    // = 41792.625 + 549

    assertEquals(41792 + 549, (int) hcMensualOrg.enKgCO2());
    RepositorioConsumos.getInstance().clean();
  }

  @Test
  public void SeCalculaElHCAnualDeUnaOrganizacion() {

    HC hcAnualOrg = organizacion.hcAnual();

    // Calculo realizado:
    // Archivo CSV: Gas natural: 1234, mensual; Electricidad: 567, anual; Nafta: 89, mensual
    // valor gas natural * valor fe gas natural + valor electricidad / 12 * valor fe electricidad + ...
    // 1234 * 30.5 * 12 + 567 * 30.5 + 89 * 30.5 * 12 = 501511.5
    // Se le suma el transporte de miembros, miembro1 va a pie, miembro2 en bici, y ambos comparten un trayecto en taxi de 4km, el taxi usa 0.15Lts de combustible por KM
    // 501511.5 + combustible utilizado para el transporte de miembros * valor fe distancia * 30 (el valor es diario por default, valor*12*30 = anual)
    // 501511.5 + 4 * 0.15 * 30.5 * 12 * 30
    // = 501511.5 + 6588

    assertEquals(501511 + 6588, (int) hcAnualOrg.enKgCO2());
    RepositorioConsumos.getInstance().clean();
  }

  private void cargarTiposDeConsumo() {
    // Cargo Tipos De Consumo
    FactorEmision feM3 = new FactorEmision(30.5, UnidadConsumo.M3);
    FactorEmision feLT = new FactorEmision(30.5, UnidadConsumo.LT);
    FactorEmision feKG = new FactorEmision(30.5, UnidadConsumo.KG);
    FactorEmision feKWH = new FactorEmision(30.5, UnidadConsumo.KWH);
    FactorEmision feKM = new FactorEmision(30.5, UnidadConsumo.KM);
    FactorEmision feNinguna = new FactorEmision(30.5, UnidadConsumo.NINGUNA);

    TipoDeConsumo tipo1 = new TipoDeConsumo("Gas Natural",
        UnidadConsumo.M3,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo1.cargarFactorEmision(feM3);
    TipoDeConsumo tipo2 = new TipoDeConsumo("Diesel/Gasoil",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo2.cargarFactorEmision(feLT);
    TipoDeConsumo tipo3 = new TipoDeConsumo("Nafta",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo3.cargarFactorEmision(feLT);
    TipoDeConsumo tipo4 = new TipoDeConsumo("Carbon",
        UnidadConsumo.KG,
        Actividad.COMBUSTION_FIJA,
        Alcance.DIRECTAS);
    tipo4.cargarFactorEmision(feKG);
    TipoDeConsumo tipo5 = new TipoDeConsumo("Combustible Gasoil",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_MOVIL,
        Alcance.DIRECTAS);
    tipo5.cargarFactorEmision(feLT);
    TipoDeConsumo tipo6 = new TipoDeConsumo("Combustible Nafta",
        UnidadConsumo.LT,
        Actividad.COMBUSTION_MOVIL,
        Alcance.DIRECTAS);
    tipo6.cargarFactorEmision(feLT);
    TipoDeConsumo tipo7 = new TipoDeConsumo("Electricidad",
        UnidadConsumo.KWH,
        Actividad.ELECTRICIDAD,
        Alcance.INDIRECTAS_ELECTRICIDAD);
    tipo7.cargarFactorEmision(feKWH);
    TipoDeConsumo tipo8 = new TipoDeConsumo("Camion de carga",
        UnidadConsumo.NINGUNA,
        Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
        Alcance.INDIRECTAS_EXTERNAS);
    tipo8.cargarFactorEmision(feNinguna);
    TipoDeConsumo tipo9 = new TipoDeConsumo("Utilitario liviano",
        UnidadConsumo.NINGUNA,
        Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
        Alcance.INDIRECTAS_EXTERNAS);
    tipo9.cargarFactorEmision(feNinguna);
    TipoDeConsumo tipo10 = new TipoDeConsumo("Distancia media",
        UnidadConsumo.KM,
        Actividad.LOGISTICA_PRODUCTOS_RESIDUOS,
        Alcance.INDIRECTAS_EXTERNAS);
    tipo10.cargarFactorEmision(feKM);

    List<TipoDeConsumo> consumos = RepositorioConsumos.getInstance().all();
    if(consumos.isEmpty()) {
      RepositorioConsumos.getInstance().add(tipo1);
      RepositorioConsumos.getInstance().add(tipo2);
      RepositorioConsumos.getInstance().add(tipo3);
      RepositorioConsumos.getInstance().add(tipo4);
      RepositorioConsumos.getInstance().add(tipo5);
      RepositorioConsumos.getInstance().add(tipo6);
      RepositorioConsumos.getInstance().add(tipo7);
      RepositorioConsumos.getInstance().add(tipo8);
      RepositorioConsumos.getInstance().add(tipo9);
      RepositorioConsumos.getInstance().add(tipo10);
    }
  }

}

*/

