import domain.excepciones.ExcepcionNoExisteElMiembroAacptarEnLaOrg;
import domain.miembros.TipoDeDocumento;
import domain.organizaciones.*;
import domain.miembros.Miembro;
import domain.organizaciones.Organizacion;
import domain.organizaciones.Sector;
import domain.organizaciones.TipoOrganizacion;

import domain.organizaciones.consumos.Unidad;
import domain.organizaciones.consumos.tipos.FactorEmision;
import domain.organizaciones.consumos.tipos.GasNatural;
import domain.organizaciones.consumos.tipos.NoCoincidenUnidadesFEYTC;
import domain.organizaciones.consumos.tipos.TipoDeConsumo;
import domain.ubicaciones.Ubicacion;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrganizacionTest {

  private Organizacion organizacionDefault;
  private Sector sectorDefault;
  private Miembro miembroDefault;
  private ClasificacionOrganizacion ministerio;
  private Ubicacion ubicacionDefault;

  @BeforeEach
  void init() {
    try {
      ubicacionDefault = new Ubicacion("Corrientes", 1200, "PUERTO LEONI ");
    } catch (IOException e) {
      //xd
    }
    ministerio = new ClasificacionOrganizacion("ministerio");
    organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, ubicacionDefault, ministerio);
    sectorDefault = new Sector();
    miembroDefault = new Miembro("Juan", "Martin", TipoDeDocumento.DNI, 43208556);
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
    organizacionDefault.cargarMediciones("src/test/java/archivo-prueba.csv");
    List<DatosActividades> datosActividadesExpected = new ArrayList<>();
    List<DatosActividades> datosActividadesLeidos = organizacionDefault.getDatosActividades();
    datosActividadesExpected.add(new DatosActividades("Gas Natural", "1234", "Mensual","04/2020"));
    datosActividadesExpected.add(new DatosActividades("Electricidad", "567", "Anual","1905"));
    datosActividadesExpected.add(new DatosActividades("Nafta", "89", "Mensual","05/2021"));
    Assertions.assertEquals(datosActividadesExpected.get(0).getValor(), datosActividadesLeidos.get(0).getValor());
  }

  @Test
  public void noSePuedeCargarUnFEConUnidadDiferenteAlTC(){
    TipoDeConsumo gasNatural = new GasNatural();
    FactorEmision fe = new FactorEmision(2, Unidad.LT);
    assertThrows(NoCoincidenUnidadesFEYTC.class, () -> gasNatural.cargarFactorEmision(fe));
  }
}
