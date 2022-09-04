package entrega2;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.datos.actividades.DatosActividades;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.datos.actividades.tipos.NoCoincidenUnidadesFEYTC;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumoFactory;
import domain.ubicaciones.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CargaDeMedicionesyFETests {

  private Organizacion organizacionDefault;
  private Ubicacion ubicacionDefault;

  @BeforeEach
  public void init() {
    organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, "Manaos", ubicacionDefault, ClasificacionOrg.MINISTERIO);
  }

  @Test
  public void sePuedeCargarUnArchivoCSVCorrectamente(){
    organizacionDefault.cargarMediciones("src/test/java/archivo-prueba.csv");
    List<DatosActividades> datosActividadesExpected = new ArrayList<>();
    List<DatosActividades> datosActividadesLeidos = organizacionDefault.getDatosActividades();

    datosActividadesExpected.add(new DatosActividades("Gas Natural", "1234", "Mensual","05/2021"));
    datosActividadesExpected.add(new DatosActividades("Electricidad", "567", "Anual","2021"));
    datosActividadesExpected.add(new DatosActividades("Nafta", "89", "Mensual","05/2021"));

    Assertions.assertEquals(datosActividadesExpected.get(0).getValor(), datosActividadesLeidos.get(0).getValor());
    Assertions.assertEquals(datosActividadesExpected.get(1).getValor(), datosActividadesLeidos.get(1).getValor());
    Assertions.assertEquals(datosActividadesExpected.get(2).getValor(), datosActividadesLeidos.get(2).getValor());
  }

  @Test
  public void noSePuedeCargarUnFEConUnidadDiferenteAlTC(){
    TipoDeConsumo gasNatural = TipoDeConsumoFactory.instance().buildTipoDeConsumo("Gas Natural");
    FactorEmision fe = new FactorEmision(2, UnidadConsumo.LT);
    assertThrows(NoCoincidenUnidadesFEYTC.class, () -> gasNatural.cargarFactorEmision(fe));
  }

}
