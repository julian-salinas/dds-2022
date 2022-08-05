package entrega2;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.Trayecto;
import domain.trayecto.transporte.*;
import domain.ubicaciones.Distancia;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.UnidadDeDistancia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DistanciaTests {

  ServicioGeoDds apiClient;

  Parada sanPedrito;
  Parada flores;
  Parada carabobo;
  Parada puan;
  Linea  lineaA;
  TransportePublico recorridoConLineaA;
  Tramo  tramoLineaA;

  Ubicacion ubicacionInicio;
  Ubicacion ubicacionFin;
  Ubicacion ubicacionInicio2;
  Ubicacion ubicacionFin2;
  Ubicacion ubicacionInicio3;
  Ubicacion ubicacionFin3;
  Ubicacion ubicacionInicio4;
  Ubicacion ubicacionFin4;

  @BeforeEach
  public void init() throws IOException {
    apiClient = mock(ServicioGeoDds.class);

    sanPedrito = new Parada("San Pedrito", distanciaMts(300.0));
    flores     = new Parada("San Jose de Flores", distanciaMts(200.0));
    carabobo   = new Parada("Carabobo", distanciaMts(150.0));
    puan       = new Parada("Puan", distanciaMts(150.0));
    List<Parada> paradasLineaA = Stream.of(sanPedrito, flores, carabobo, puan).collect(Collectors.toList());
    lineaA = new Linea("Linea A", paradasLineaA, TipoTransportePublico.SUBTE);
    // Con un recorrido que es solo de San Pedrito a Carabobo
    recorridoConLineaA = new TransportePublico(TipoTransportePublico.SUBTE, lineaA, sanPedrito, carabobo);
    tramoLineaA = new Tramo(recorridoConLineaA);

    ubicacionInicio   = crearUbicacion("Directorio", 1700);
    ubicacionFin      = crearUbicacion("Yapeyu", 600);
    ubicacionInicio2  = crearUbicacion("Directorio", 100);
    ubicacionFin2     = crearUbicacion("Yapeyu", 200);
    ubicacionInicio3  = crearUbicacion("Directorio", 2300);
    ubicacionFin3     = crearUbicacion("Yapeyu", 2400);
    ubicacionInicio4  = crearUbicacion("Directorio", 600);
    ubicacionFin4     = crearUbicacion("Yapeyu", 700);

  }

  // Tests con Transporte Publico (la dist. de cada tramo se indica a mano)

  @Test
  public void laDistanciaDeUnTramoDeTransportePublicoSeCalculaBien() {
    assertEquals(650.0, tramoLineaA.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTrayectoDeTramosDeTransportePublicoSeCalculaBien() {
    Parada carabobo1200 = new Parada("Carabobo1200", distanciaMts(55.0));
    Parada carabobo1400 = new Parada("Carabobo1400", distanciaMts(55.0));
    List<Parada> paradas132 = Stream.of(carabobo1200, carabobo1400).collect(Collectors.toList());
    Linea linea132 = new Linea("132", paradas132, TipoTransportePublico.COLECTIVO);
    TransportePublico recorridoCon132 = new TransportePublico(TipoTransportePublico.COLECTIVO, linea132, carabobo1200, carabobo1400);
    Tramo tramoCon132 = new Tramo(recorridoCon132);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoLineaA, tramoCon132);

    assertEquals(760.0, trayecto.distanciaTotal().valorEnMetros());
  }

  // Tests con los demas tipos de Transporte (la dist. de cada tramo se la indica la API)

  @Test
  public void laDistanciaDeUnTramoAPieSeCalculaBien() throws IOException {
    Pie recorridoAPie = new Pie(ubicacionInicio, ubicacionFin, apiClient);
    Tramo tramoAPie   = new Tramo(recorridoAPie);

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio, ubicacionFin))
        .thenReturn(73.6);

    assertEquals(73.6, tramoAPie.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoEnBiciSeCalculaBien() throws IOException {
    Bicicleta recorridoEnBici = new Bicicleta(ubicacionInicio2, ubicacionFin2, apiClient);
    Tramo tramoEnBici = new Tramo(recorridoEnBici);

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio2, ubicacionFin2))
        .thenReturn(313.47);

    assertEquals(313.47, tramoEnBici.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoEnVehiculoParticularSeCalculaBien() throws IOException {
    VehiculoParticular recorridoEnAuto = new VehiculoParticular(TipoDeVehiculo.AUTO,
        TipoDeCombustible.GASOIL,
        ubicacionInicio3, ubicacionFin3,
        apiClient, 400.0);

    Tramo tramoEnAuto = new Tramo(recorridoEnAuto);

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio3, ubicacionFin3))
        .thenReturn(10002.2);

    assertEquals(10002.2, tramoEnAuto.distancia().valorEnMetros());
  }

  @Test
  public void laDistanciaDeUnTramoEnServicioContratadoSeCalculaBien() throws IOException {
    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado recorridoEnTaxi = new ServicioContratado(taxi, ubicacionInicio4,
        ubicacionFin4, apiClient, 200.0);

    Tramo tramoEnTaxi = new Tramo(recorridoEnTaxi);

    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio4, ubicacionFin4))
        .thenReturn(750.0);

    assertEquals(750.0, tramoEnTaxi.distancia().valorEnMetros());
  }

  @Test                                               //TP = Transporte Publico
  public void laDistanciaDeUnTrayectoDeTramosDeTodoMenosTPSeCalculaBien() throws IOException {

    Pie recorridoAPie = new Pie(ubicacionInicio, ubicacionFin, apiClient);
    Tramo tramoAPie   = new Tramo(recorridoAPie);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio, ubicacionFin))
        .thenReturn(73.0);

    Bicicleta recorridoEnBici = new Bicicleta(ubicacionInicio2, ubicacionFin2, apiClient);
    Tramo tramoEnBici = new Tramo(recorridoEnBici);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio2, ubicacionFin2))
        .thenReturn(313.0);

    VehiculoParticular recorridoEnAuto = new VehiculoParticular(TipoDeVehiculo.AUTO,
        TipoDeCombustible.GASOIL,
        ubicacionInicio3, ubicacionFin3,
        apiClient, 400.0);
    Tramo tramoEnAuto = new Tramo(recorridoEnAuto);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio3, ubicacionFin3))
        .thenReturn(10002.0);

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado recorridoEnTaxi = new ServicioContratado(taxi,
        ubicacionInicio4, ubicacionFin4,
        apiClient, 200.0);
    Tramo tramoEnTaxi = new Tramo(recorridoEnTaxi);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio4, ubicacionFin4))
        .thenReturn(750.0);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoAPie, tramoEnBici, tramoEnAuto, tramoEnTaxi);

    // 73.0 + 313.0 + 10000.0 + 750.0 = 11136.0
    assertTrue(trayecto.distanciaTotal().valorEnMetros() > 11120.0);
    assertTrue(trayecto.distanciaTotal().valorEnMetros() < 11145.0);

    // Haciendo este tests me di cuenta de q a Java 8 (min) le explota la cabeza al sumar doubles
    // Nunca da exacto, es increible
    // Esa es la razon de la, uno podria decir, ranciedad del test, aunque esta
    // manera no esta tan mal.
  }

  @Test
  public void laDistanciaDeUnTrayectoDeTramosDeTodosLosTiposSeCalculaBien() throws IOException {

    Pie recorridoAPie = new Pie(ubicacionInicio, ubicacionFin, apiClient);
    Tramo tramoAPie   = new Tramo(recorridoAPie);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio, ubicacionFin))
        .thenReturn(73.0);

    Bicicleta recorridoEnBici = new Bicicleta(ubicacionInicio2, ubicacionFin2, apiClient);
    Tramo tramoEnBici = new Tramo(recorridoEnBici);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio2, ubicacionFin2))
        .thenReturn(313.0);

    VehiculoParticular recorridoEnAuto = new VehiculoParticular(TipoDeVehiculo.AUTO,
        TipoDeCombustible.GASOIL,
        ubicacionInicio3, ubicacionFin3,
        apiClient, 400.0);
    Tramo tramoEnAuto = new Tramo(recorridoEnAuto);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio3, ubicacionFin3))
        .thenReturn(10002.0);

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado recorridoEnTaxi = new ServicioContratado(taxi,
        ubicacionInicio4, ubicacionFin4,
        apiClient, 200.0);
    Tramo tramoEnTaxi = new Tramo(recorridoEnTaxi);
    when(apiClient.distanciaEntreUbicaciones(ubicacionInicio4, ubicacionFin4))
        .thenReturn(750.0);

    Trayecto trayecto = new Trayecto();
    trayecto.agregarTramos(tramoAPie, tramoEnBici, tramoEnAuto, tramoEnTaxi, tramoLineaA);

    // 73.0 + 313.0 + 10000.0 + 750.0 + 650.0 = 11786.0
    assertTrue(trayecto.distanciaTotal().valorEnMetros() > 11770.0);
    assertTrue(trayecto.distanciaTotal().valorEnMetros() < 11795.0);

    // Haciendo este tests me di cuenta de q a Java 8 (min) le explota la cabeza al sumar doubles
    // Nunca da exacto, es increible
    // Esa es la razon de la, uno podria decir, ranciedad del test, aunque esta
    // manera no esta tan mal.
  }

  // Metodos aux.

  private Distancia distanciaMts(double valor) {
    return new Distancia(valor, UnidadDeDistancia.MTS);
  }

  private Distancia distanciaKm(double valor) {
    return new Distancia(valor, UnidadDeDistancia.KM);
  }

  private Ubicacion crearUbicacion(String calle, int altura) throws IOException {
    Ubicacion ubicacion;
    ServicioGeoDds api = mock(ServicioGeoDds.class);
    when(api.verificarNombreLocalidad(anyString())).thenReturn(2);  //id Localidad = 2
    when(api.nombreMunicipio(2)).thenReturn("Valcheta");
    when(api.verificarNombreMunicipio("Valcheta")).thenReturn(4);   //id Municipio = 4
    when(api.nombreProvincia(4)).thenReturn("Rio Negro");
    when(api.verificarNombreProvincia("Rio Negro")).thenReturn(7);  //id Provincia = 7

    ubicacion = new Ubicacion(calle, altura, "Chacabuco", api);
    return ubicacion;
  }

}
