package domain.ubicaciones.sectores;

import domain.organizaciones.*;
import domain.organizaciones.datos.actividades.Actividad;
import domain.organizaciones.datos.actividades.Alcance;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.organizaciones.hc.HC;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import repositorios.RepositorioConsumos;
import repositorios.RepositorioOrganizaciones;
import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.Ubicacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AgenteSectorialTests {
  ServicioGeoDds apiClient;
  Organizacion org1;
  Organizacion org2;
  Organizacion org3;
  Ubicacion ubicacion1;
  Ubicacion ubicacion2;
  Ubicacion ubicacion3;

  @BeforeEach
  public void init() throws IOException {

    // Creo 3 Orgs en distintos municipios pero misma Provincia

    apiClient = mock(ServicioGeoDds.class);

    when(apiClient.verificarNombrePais("Argentina")).thenReturn(9);
    when(apiClient.verificarNombreProvincia("Buenos Aires",9)).thenReturn(102);
    when(apiClient.verificarNombreMunicipio("Valcheta",102)).thenReturn(321);
    when(apiClient.verificarNombreMunicipio("Carpincho",102)).thenReturn(430);
    when(apiClient.verificarNombreMunicipio("Bozoro",102)).thenReturn(519);
    when(apiClient.verificarNombreLocalidad(eq("localidad"),anyInt())).thenReturn(1);
    when(apiClient.verificarNombreLocalidad(eq("localidad2"),anyInt())).thenReturn(2);
    when(apiClient.verificarNombreLocalidad(eq("localidad3"),anyInt())).thenReturn(3);

    ubicacion1 = new Ubicacion("Corrientes", 1200, "Argentina", "Buenos Aires",
        "Valcheta", "localidad", apiClient);
    ubicacion2 = new Ubicacion("Corrientes", 1200, "Argentina", "Buenos Aires",
        "Carpincho", "localidad2", apiClient);
    ubicacion3 = new Ubicacion("Corrientes", 1200, "Argentina", "Buenos Aires",
        "Bozoro", "localidad3", apiClient);


    org1 = crearOrg("McDonalds", ubicacion1);
    org2 = crearOrg("Filamentos Danjo", ubicacion2);
    org3 = crearOrg("Pesquera Cantos", ubicacion3);

    cargarTiposDeConsumo();
  }

  // TODOS LOS Fe valen 30.5 !!!

  @Test
  public void sePuedenPedirLasOrgDentroDeUnMunicipioCorrectamente() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    List<Organizacion> orgs = RepositorioOrganizaciones.getInstance().inMunicipio(org1.sectorMunicipio().getId());

    assertEquals(1, orgs.size());
    assertEquals(org1, orgs.get(0));

    RepositorioOrganizaciones.getInstance().clean();
  }

  @Test
  public void sePuedenPedirLasOrgDentroDeUnaProvinciaCorrectamente() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    List<Organizacion> todasLasOrgs = new ArrayList<>();
    todasLasOrgs.add(org1);
    todasLasOrgs.add(org2);
    todasLasOrgs.add(org3);

    List<Organizacion> orgs = RepositorioOrganizaciones.getInstance().inProvincia(org1.sectorProvincia().getId());

    assertEquals(3, orgs.size());
    assertEquals(todasLasOrgs, orgs);

    RepositorioOrganizaciones.getInstance().clean();
  }

  // <--

  // Aca van los tests de Agente Sectorial

  @Test
  public void seCalculaBienElhcMensualDeUnMunicipio() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    org1.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org2.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org3.cargarMediciones("src/test/resources/files/archivo-prueba.csv");

    // El agente esta en el Municipio de org1, pero no de org2, y org3
    Municipio municipio = org1.sectorMunicipio();
    AgenteSectorial agenteSectorial = new AgenteSectorial(TipoSectorTerritorial.MUNICIPIO,
        municipio.getId(), municipio.getNombre());
    HC hcSectorMensual = agenteSectorial.hcSectorMensual();
    double valorHc = hcSectorMensual.enKgCO2();

    // el HC mensual de una org deberia dar 2.010,325
    // el de solo la org1 (que es la unica en ese municipio) deberia dar 2.010,325

    // 1234 * 30.5 + 567 * 30.5 / 12 + 89 * 30.5
    // 41.792,625

    assertEquals(41792.625, valorHc);

    RepositorioOrganizaciones.getInstance().clean();
    RepositorioConsumos.getInstance().clean();
  }

  /*

  @Test
  public void seCalculaBienElhcAnualDeUnMunicipio() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    org1.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org2.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org3.cargarMediciones("src/test/resources/files/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);

    // El agente esta en el Municipio de org1, pero no de org2, y org3
    Municipio municipio = org1.sectorMunicipio();
    AgenteSectorial agenteSectorial = new AgenteSectorial(TipoSectorTerritorial.MUNICIPIO,
        municipio.getId(), "Alfonso");
    HC hcSectorAnual = agenteSectorial.hcSectorAnual();
    double valorHc = hcSectorAnual.enKgCO2();

    // el HC anual de una org deberia dar 24.123,9
    // el de solo la org1 (que es la unica en ese municipio) deberia dar 24.123,9

    assertEquals(24123.9, valorHc);

    RepositorioOrganizaciones.getInstance().clean();
  }

  @Test
  public void seCalculaBienElhcMensualDeUnaProvincia() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    org1.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org2.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org3.cargarMediciones("src/test/resources/files/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);

    Provincia provincia = org1.sectorProvincia();
    AgenteSectorial agenteSectorial = new AgenteSectorial(TipoSectorTerritorial.PROVINCIA,
        provincia.getId(), "Alfonso");
    HC hcSectorMensual = agenteSectorial.hcSectorMensual();
    double valorHc = hcSectorMensual.enKgCO2();

    // el HC mensual de una org deberia dar 2.010,325
    // el de las 3 (que estan en la misma provinvia) deberia dar 6.030,975

    assertEquals(6030.975, valorHc);

    RepositorioOrganizaciones.getInstance().clean();
  }

  @Test
  public void seCalculaBienElhcAnualDeUnaProvincia() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    org1.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org2.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org3.cargarMediciones("src/test/resources/files/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);

    Provincia provincia = org1.sectorProvincia();
    AgenteSectorial agenteSectorial = new AgenteSectorial(TipoSectorTerritorial.PROVINCIA,
        provincia.getId(), "Alfonso");
    HC hcSectorAnual = agenteSectorial.hcSectorAnual();
    double valorHc = hcSectorAnual.enKgCO2();

    // el HC anual de una org deberia dar 24.123,9
    // el de las 3 (que estan en la misma provinvia) deberia dar 72.371,7

    assertEquals(72371, ((int) valorHc));

    RepositorioOrganizaciones.getInstance().clean();
  }

  // TODO: fixear test
  @Test
  public void siHayUnaOrgQueNoEstaEnSuProvinciaNoLaCuenta() throws IOException {
    when(apiClient.nombreProvincia(anyInt())).thenReturn("San Juan");
    when(apiClient.verificarNombreProvincia("San Juan")).thenReturn(20);     //id Provincia = 20

    Ubicacion ubicacion4 = new Ubicacion("Corrientes", 1200, "localidad", apiClient);
    Organizacion org4 = crearOrg("Jojo Donuts", ubicacion4);

    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);
    RepositorioOrganizaciones.getInstance().add(org4);

    org1.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org2.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org3.cargarMediciones("src/test/resources/files/archivo-prueba.csv");
    org4.cargarMediciones("src/test/resources/files/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);
    setFactoresDeEmision(org4);

    // El agente esta en la Provincia de org1, org2, y org3, pero no org4
    Provincia provincia = org1.sectorProvincia();
    AgenteSectorial agenteSectorial = new AgenteSectorial(TipoSectorTerritorial.PROVINCIA,
        provincia.getId(), "Alfonso");
    HC hcSectorMensual = agenteSectorial.hcSectorMensual();
    double valorHc = hcSectorMensual.enKgCO2();

    // el HC mensual de una org deberia dar 2.010,325
    // el de las 3 (que estan en la misma provinvia) deberia dar 6.030,975 (porq org4 no deberia afectar)

    assertEquals(6030.975, valorHc);

    RepositorioOrganizaciones.getInstance().clean();
  }
  */

  private Organizacion crearOrg(String nombre, Ubicacion ubicacion) {
    return new Organizacion(nombre, "S.A.", TipoOrganizacion.EMPRESA, ubicacion, ClasificacionOrg.MINISTERIO);
  }

  private void setFactoresDeEmision(Organizacion org) {
    FactorEmision feGasNatural = new FactorEmision(1.5, UnidadConsumo.M3);
    FactorEmision feElectricidad = new FactorEmision(1.3, UnidadConsumo.KWH);
    FactorEmision feNafta = new FactorEmision(1.1, UnidadConsumo.LT);

    org.getDatosActividades().get(0).cargarFactorEmision(feGasNatural);
    org.getDatosActividades().get(1).cargarFactorEmision(feElectricidad);
    org.getDatosActividades().get(2).cargarFactorEmision(feNafta);
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
