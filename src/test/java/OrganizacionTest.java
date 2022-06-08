import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.organizaciones.ClasificacionOrganizacion;
import domain.miembros.Miembro;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.consumos.Unidad;
import domain.organizaciones.consumos.tipos.FactorEmision;
import domain.organizaciones.consumos.tipos.GasNatural;
import domain.organizaciones.consumos.tipos.NoCoincidenUnidadesFEYTC;
import domain.organizaciones.consumos.tipos.TipoDeConsumo;
import domain.passwords.exceptions.LongitudException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizacionTest {

  private Organizacion organizacionDefault;
  private Sector sectorDefault;
  private Miembro miembroDefault;
  private ClasificacionOrganizacion ministerio;

  @BeforeEach
  void init() {
    ministerio = new ClasificacionOrganizacion("ministerio");
    organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, "Rosario", ministerio);
    sectorDefault = new Sector();
    miembroDefault = new Miembro("Juan", "Martin", "Crack?", "43-208-556");
  }

  @Test
  public void noSePuedeAceptarAunMiembroEnUnaOrgSiEsteNoPidioVincularse(){
    // Existe la org y el miembro, sin embargo el miembro NUNCA pidio vincularse a la org.
    assertThrows(ExcepcionNoExisteElMiembroAacptarEnLaOrg.class,
        () -> organizacionDefault.aceptarVinculacionDeTrabajador(miembroDefault, sectorDefault));
  }

  @Test
  public void sePuedeAgregarUnSectorAUnaOrganizacion(){
    organizacionDefault.agregarSector(sectorDefault);
    assertTrue(organizacionDefault.containsSector(sectorDefault));
    assertEquals(sectorDefault.getOrgAlaQuePertenezco(), organizacionDefault);
  }

  @Test
  public void sePuedeCargarUnArchivoCSVCorrectamente(){
    organizacionDefault.cargarMediciones("C:\\Users\\Luciano\\Documents\\archivo-prueba.csv"); //Hay que arreglar este path
    List<List<String>> datosActividadesExpected = new ArrayList<>();
    datosActividadesExpected.add(Arrays.asList("Gas Natural", "Electricidad", "Nafta")); // Primera columna
    datosActividadesExpected.add(Arrays.asList("1234", "567", "89")); // Segunda columna
    datosActividadesExpected.add(Arrays.asList("Mensual", "Anual", "Mensual")); // Tercera columna
    datosActividadesExpected.add(Arrays.asList("04/2020", "1905", "05/2021")); // Cuarta columna
    Assertions.assertEquals(datosActividadesExpected, organizacionDefault.getDatosActividades());
  }

  @Test
  public void noSePuedeCargarUnFEConUnidadDiferenteAlTC(){
    TipoDeConsumo gasNatural = new GasNatural();
    FactorEmision fe = new FactorEmision(2, Unidad.LT);
    assertThrows(NoCoincidenUnidadesFEYTC.class, () -> gasNatural.cargarFactorEmision(fe));
  }
}
