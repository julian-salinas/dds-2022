package entrega2;

import domain.organizaciones.ClasificacionOrganizacion;
import domain.organizaciones.DatosActividades;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.consumos.Unidad;
import domain.organizaciones.consumos.tipos.FactorEmision;
import domain.organizaciones.consumos.tipos.NoCoincidenUnidadesFEYTC;
import domain.organizaciones.consumos.tipos.TipoDeConsumo;
import domain.organizaciones.consumos.tipos.TipoDeConsumoFactory;
import domain.ubicaciones.Ubicacion;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CargaDeMedicionesyFETests {

  private Organizacion organizacionDefault;
  private Ubicacion ubicacionDefault;

  @BeforeEach
  public void init() {
    ClasificacionOrganizacion ministerio = new ClasificacionOrganizacion("ministerio");
    organizacionDefault = new Organizacion("?", TipoOrganizacion.EMPRESA, "Manaos", ubicacionDefault, ministerio);
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
    FactorEmision fe = new FactorEmision(2, Unidad.LT);
    assertThrows(NoCoincidenUnidadesFEYTC.class, () -> gasNatural.cargarFactorEmision(fe));
  }

}
