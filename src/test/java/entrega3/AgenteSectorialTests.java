package entrega3;

import domain.organizaciones.ClasificacionOrganizacion;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.repositorios.RepoOrganizaciones;
import domain.servicios.geodds.ServicioGeoDds;
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

    try {
      ubicacion1 = new Ubicacion("Corrientes", 1200, "localidad", apiClient);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      ubicacion2 = new Ubicacion("Corrientes", 1200, "localidad2", apiClient);
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      ubicacion3 = new Ubicacion("Corrientes", 1200, "localidad3", apiClient);
    } catch (IOException e) {
      e.printStackTrace();
    }

    org1 = crearOrg("McDonalds", ubicacion1);
    org2 = crearOrg("Filamentos Danjo", ubicacion2);
    org3 = crearOrg("Pesquera Cantos", ubicacion3);

    RepoOrganizaciones.instance().agregarOrganizacion(org1, org2, org3);

  }

  // Estos dos tests no van a hacer falta despues
  // -->

  @Test
  public void sePuedenPedirLasOrgDentroDeUnMunicipioCorrectamente() {
    assertEquals(org1, org1.sectorMunicipio().orgsDentroDeSector().get(0));
    assertEquals(1, (long) org1.sectorMunicipio().orgsDentroDeSector().size());
  }

  @Test
  public void sePuedenPedirLasOrgDentroDeUnaProvinciaCorrectamente() {
    List<Organizacion> todasLasOrgs = new ArrayList<>();
    todasLasOrgs.add(org1);
    todasLasOrgs.add(org2);
    todasLasOrgs.add(org3);
    assertEquals(todasLasOrgs, org1.sectorProvincia().orgsDentroDeSector());
    assertEquals(3, (long) org1.sectorProvincia().orgsDentroDeSector().size());
  }
  // <--

  // Aca van los tests de Agente Sectorial (del metodo 'hC')

  private Organizacion crearOrg(String nombre, Ubicacion ubicacion) {
    ClasificacionOrganizacion ministerio = new ClasificacionOrganizacion("ministerio");
    return new Organizacion("S.A.", TipoOrganizacion.EMPRESA, nombre, ubicacion, ministerio);
  }

}
