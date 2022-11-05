package entrega2;

import domain.organizaciones.ClasificacionOrg;
import domain.organizaciones.datos.actividades.Actividad;
import domain.organizaciones.datos.actividades.Alcance;
import domain.organizaciones.datos.actividades.DatosActividades;
import domain.organizaciones.Organizacion;
import domain.organizaciones.TipoOrganizacion;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.datos.actividades.tipos.NoCoincidenUnidadesFEYTC;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import repositorios.RepositorioConsumos;
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
    organizacionDefault = new Organizacion("Manaos", "?", TipoOrganizacion.EMPRESA, ubicacionDefault, ClasificacionOrg.MINISTERIO);

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
    RepositorioConsumos.getInstance().clean();
  }

  @Test
  public void noSePuedeCargarUnFEConUnidadDiferenteAlTC(){
    //TipoDeConsumo gasNatural = TipoDeConsumoFactory.instance().buildTipoDeConsumo("Gas Natural");
    TipoDeConsumo gasNatural = RepositorioConsumos.getInstance().findByName("Gas Natural");
    FactorEmision fe = new FactorEmision(2, UnidadConsumo.LT);
    assertThrows(NoCoincidenUnidadesFEYTC.class, () -> gasNatural.cargarFactorEmision(fe));
    RepositorioConsumos.getInstance().clean();
  }

}
