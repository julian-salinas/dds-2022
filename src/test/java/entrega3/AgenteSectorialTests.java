package entrega3;

import domain.organizaciones.*;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.hc.HC;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.repositorios.RepositorioOrganizaciones;
import domain.servicios.geodds.ServicioGeoDds;
import domain.ubicaciones.sectores.AgenteSectorial;
import domain.ubicaciones.sectores.Municipio;
import domain.ubicaciones.sectores.Provincia;
import domain.ubicaciones.Ubicacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
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
    when(apiClient.verificarNombreLocalidad("localidad")).thenReturn(1);      //id Localidad = 1
    when(apiClient.nombreMunicipio(1)).thenReturn("Valcheta");
    when(apiClient.verificarNombreLocalidad("localidad2")).thenReturn(2);     //id Localidad = 2
    when(apiClient.nombreMunicipio(2)).thenReturn("Carpincho");
    when(apiClient.verificarNombreLocalidad("localidad3")).thenReturn(3);     //id Localidad = 3
    when(apiClient.nombreMunicipio(3)).thenReturn("Bozoro");

    when(apiClient.verificarNombreMunicipio("Valcheta")).thenReturn(4);       //id Municipio = 4
    when(apiClient.verificarNombreMunicipio("Carpincho")).thenReturn(5);      //id Municipio = 5
    when(apiClient.verificarNombreMunicipio("Bozoro")).thenReturn(6);         //id Municipio = 6

    when(apiClient.nombreProvincia(anyInt())).thenReturn("Rio Negro");
    when(apiClient.verificarNombreProvincia("Rio Negro")).thenReturn(7);     //id Provincia = 7

    ubicacion1 = new Ubicacion("Corrientes", 1200, "localidad", apiClient);
    ubicacion2 = new Ubicacion("Corrientes", 1200, "localidad2", apiClient);
    ubicacion3 = new Ubicacion("Corrientes", 1200, "localidad3", apiClient);

    org1 = crearOrg("McDonalds", ubicacion1);
    org2 = crearOrg("Filamentos Danjo", ubicacion2);
    org3 = crearOrg("Pesquera Cantos", ubicacion3);

  }

  // Estos dos tests no van a hacer falta despues
  // -->

  @Test
  public void sePuedenPedirLasOrgDentroDeUnMunicipioCorrectamente() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    assertEquals(1, (long) org1.sectorMunicipio().orgsDentroDeSector().size());
    assertEquals(org1, org1.sectorMunicipio().orgsDentroDeSector().get(0));

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
    assertEquals(3, (long) org1.sectorProvincia().orgsDentroDeSector().size());
    assertEquals(todasLasOrgs, org1.sectorProvincia().orgsDentroDeSector());

    RepositorioOrganizaciones.getInstance().clean();
  }
  // <--

  // Aca van los tests de Agente Sectorial

  @Test
  public void seCalculaBienElhcMensualDeUnMunicipio() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    org1.cargarMediciones("src/test/java/archivo-prueba.csv");
    org2.cargarMediciones("src/test/java/archivo-prueba.csv");
    org3.cargarMediciones("src/test/java/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);

    // El agente esta en el Municipio de org1, pero no de org2, y org3
    Municipio municipio = org1.sectorMunicipio();
    AgenteSectorial agenteSectorial = new AgenteSectorial(municipio);
    HC hcSectorMensual = agenteSectorial.hcSectorMensual();
    double valorHc = hcSectorMensual.enKgCO2();

    // el HC mensual de una org deberia dar 2.010,325
    // el de solo la org1 (que es la unica en ese municipio) deberia dar 2.010,325

    assertEquals(2010.325, valorHc);

    RepositorioOrganizaciones.getInstance().clean();
  }

  @Test
  public void seCalculaBienElhcAnualDeUnMunicipio() {
    RepositorioOrganizaciones.getInstance().add(org1);
    RepositorioOrganizaciones.getInstance().add(org2);
    RepositorioOrganizaciones.getInstance().add(org3);

    org1.cargarMediciones("src/test/java/archivo-prueba.csv");
    org2.cargarMediciones("src/test/java/archivo-prueba.csv");
    org3.cargarMediciones("src/test/java/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);

    // El agente esta en el Municipio de org1, pero no de org2, y org3
    Municipio municipio = org1.sectorMunicipio();
    AgenteSectorial agenteSectorial = new AgenteSectorial(municipio);
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

    org1.cargarMediciones("src/test/java/archivo-prueba.csv");
    org2.cargarMediciones("src/test/java/archivo-prueba.csv");
    org3.cargarMediciones("src/test/java/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);

    Provincia provincia = org1.sectorProvincia();
    AgenteSectorial agenteSectorial = new AgenteSectorial(provincia);
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

    org1.cargarMediciones("src/test/java/archivo-prueba.csv");
    org2.cargarMediciones("src/test/java/archivo-prueba.csv");
    org3.cargarMediciones("src/test/java/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);

    Provincia provincia = org1.sectorProvincia();
    AgenteSectorial agenteSectorial = new AgenteSectorial(provincia);
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

    org1.cargarMediciones("src/test/java/archivo-prueba.csv");
    org2.cargarMediciones("src/test/java/archivo-prueba.csv");
    org3.cargarMediciones("src/test/java/archivo-prueba.csv");
    org4.cargarMediciones("src/test/java/archivo-prueba.csv");

    setFactoresDeEmision(org1);
    setFactoresDeEmision(org2);
    setFactoresDeEmision(org3);
    setFactoresDeEmision(org4);

    // El agente esta en la Provincia de org1, org2, y org3, pero no org4
    Provincia provincia = org1.sectorProvincia();
    AgenteSectorial agenteSectorial = new AgenteSectorial(provincia);
    HC hcSectorMensual = agenteSectorial.hcSectorMensual();
    double valorHc = hcSectorMensual.enKgCO2();

    // el HC mensual de una org deberia dar 2.010,325
    // el de las 3 (que estan en la misma provinvia) deberia dar 6.030,975 (porq org4 no deberia afectar)

    assertEquals(6030.975, valorHc);

    RepositorioOrganizaciones.getInstance().clean();
  }

  private Organizacion crearOrg(String nombre, Ubicacion ubicacion) {
    return new Organizacion("S.A.", TipoOrganizacion.EMPRESA, nombre, ubicacion, ClasificacionOrg.MINISTERIO);
  }

  private void setFactoresDeEmision(Organizacion org) {
    FactorEmision feGasNatural = new FactorEmision(1.5, UnidadConsumo.M3);
    FactorEmision feElectricidad = new FactorEmision(1.3, UnidadConsumo.KWH);
    FactorEmision feNafta = new FactorEmision(1.1, UnidadConsumo.LT);

    org.getDatosActividades().get(0).getTipoDeConsumo().cargarFactorEmision(feGasNatural);
    org.getDatosActividades().get(1).getTipoDeConsumo().cargarFactorEmision(feElectricidad);
    org.getDatosActividades().get(2).getTipoDeConsumo().cargarFactorEmision(feNafta);
  }

}
