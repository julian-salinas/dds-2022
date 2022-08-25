package entrega3;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import domain.miembros.Miembro;
import domain.miembros.TipoDeDocumento;
import domain.organizaciones.*;
import domain.organizaciones.consumos.Unidad;
import domain.organizaciones.consumos.tipos.FactorEmision;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.Bicicleta;
import domain.trayecto.transporte.Pie;
import domain.trayecto.transporte.ServicioContratado;
import domain.trayecto.transporte.TipoServicioContratado;
import domain.ubicaciones.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CalculoHcTests {

  ServicioGeoDds apiClient;

  Ubicacion unaUbicacion;
  ClasificacionOrganizacion clasificacion;
  Organizacion organizacion;
  Sector sectorDefault;

  Miembro miembro1;
  Miembro miembro2;

  FactorEmision feGasNatural;
  FactorEmision feElectricidad;
  FactorEmision feNafta;
  FactorEmision feDistancia;

  TrayectoCompartido trayectoCompartido;

  @BeforeEach
  public void init() throws IOException {
    apiClient = mock(ServicioGeoDds.class);

    clasificacion = new ClasificacionOrganizacion(" Empresa del sector secundario");
    organizacion = new Organizacion("RazonX", TipoOrganizacion.EMPRESA, "Cola-Coca", unaUbicacion, clasificacion);
    sectorDefault = new Sector();
    organizacion.agregarSector(sectorDefault);
    miembro1 = new Miembro("Pepito", "Martinez", TipoDeDocumento.DNI, 45869305);
    miembro2 = new Miembro("Juanito", "Hernandez", TipoDeDocumento.DNI, 47869206);
    sectorDefault.agregarMiembro(miembro1);
    sectorDefault.agregarMiembro(miembro2);

    feGasNatural = new FactorEmision(1.5, Unidad.M3);
    feElectricidad = new FactorEmision(1.3, Unidad.KWH);
    feNafta = new FactorEmision(1.1, Unidad.LT);
    feDistancia = new FactorEmision(1.3, Unidad.KM);

    organizacion.cargarMediciones("src/test/java/archivo-prueba.csv");
    List<DatosActividades> datosActividades = organizacion.getDatosActividades();
    datosActividades.get(0).getTipoDeConsumo().cargarFactorEmision(feGasNatural);
    datosActividades.get(1).getTipoDeConsumo().cargarFactorEmision(feElectricidad);
    datosActividades.get(2).getTipoDeConsumo().cargarFactorEmision(feNafta);

    Pie tramoPie = new Pie(unaUbicacion, unaUbicacion);
    Tramo primerTramom1 = new Tramo(tramoPie);
    Trayecto trayecto1 = new Trayecto();
    trayecto1.agregarTramo(primerTramom1);
    miembro1.agregarTrayecto(trayecto1);

    Bicicleta tramoBici = new Bicicleta(unaUbicacion, unaUbicacion);
    Tramo primerTramom2 = new Tramo(tramoBici);
    Trayecto trayecto2 = new Trayecto();
    trayecto2.agregarTramo(primerTramom2);
    miembro2.agregarTrayecto(trayecto2);

    List<Miembro> miembros = Stream.of(miembro2).collect(Collectors.toList());
    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, unaUbicacion, unaUbicacion, 0.15
    );
    Tramo tramo = new Tramo(servicioContratado);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());
    trayectoCompartido = new TrayectoCompartido(miembros, tramos);

    miembro1.registrarTrayecto(trayectoCompartido);

    when(apiClient.distanciaEntreUbicaciones(unaUbicacion, unaUbicacion)).thenReturn(4000.0);
  }

  @Test
  public void SeCalculaElHCMensualDeUnaOrganizacion() {
    organizacion.cargarDATransladoMiembros();
    List<DatosActividades> datosActividades = organizacion.getDatosActividades();
    datosActividades.get(3).getTipoDeConsumo().cargarFactorEmision(feDistancia);

    HC hcMensualOrg = organizacion.hcMensual();

    // Calculo realizado:
    // Archivo CSV: Gas natural: 1234, mensual; Electricidad: 567, anual; Nafta: 89, mensual
    // valor gas natural * valor fe gas natural + valor electricidad / 12 * valor fe electricidad + ...
    // 1234 * 1.5 + (567 / 12) * 1.3 + 89 * 1.1 = 2010.325
    // Se le suma el transporte de miembros, miembro1 va a pie, miembro2 en bici, y ambos comparten un trayecto en taxi de 4km, el taxi usa 0.15Lts de combustible por KM
    // 2010.325 + combustible utilizado para el transporte de miembros * 30 (para hacerlo un valor mensual) * valor fe distancia
    // 2010.325 + (4 * 0.15 * 30) * 1.3
    // = 2033.725, aprox 2033

    assertEquals(2033, (int) hcMensualOrg.enKgCO2());
  }

  @Test
  public void SeCalculaElHCAnualDeUnaOrganizacion() {
    organizacion.cargarDATransladoMiembros();
    List<DatosActividades> datosActividades = organizacion.getDatosActividades();
    datosActividades.get(3).getTipoDeConsumo().cargarFactorEmision(feDistancia);

    HC hcAnualOrg = organizacion.hcAnual();

    // Calculo realizado:
    // Archivo CSV: Gas natural: 1234, mensual; Electricidad: 567, anual; Nafta: 89, mensual
    // valor gas natural * valor fe gas natural + valor electricidad / 12 * valor fe electricidad + ...
    // 1234 * 12 * 1.5 + 567 * 1.3 + 89 * 12 * 1.1 = 24123.9
    // Se le suma el transporte de miembros, miembro1 va a pie, miembro2 en bici, y ambos comparten un trayecto en taxi de 4km, el taxi usa 0.15Lts de combustible por KM
    // 24123.9 + combustible utilizado para el transporte de miembros * valor fe distancia
    // 24123.9 + (4 * 0.15 * 30 * 12) * 1.3
    // = 24404.7, aprox 24404

    assertEquals(24404, (int) hcAnualOrg.enKgCO2());
  }
}


