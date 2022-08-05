package entrega2;

import domain.miembros.Miembro;
import domain.miembros.TipoDeDocumento;
import domain.servicios.geodds.ServicioGeoDds;
import domain.trayecto.Tramo;
import domain.trayecto.TrayectoCompartido;
import domain.trayecto.transporte.*;
import domain.ubicaciones.Ubicacion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrayectoTests {

  ServicioGeoDds apiClient;
  Ubicacion ubicacionDefault;

  @BeforeEach
  void init() throws IOException {
    apiClient = mock(ServicioGeoDds.class);
    when(apiClient.verificarNombreLocalidad(anyString())).thenReturn(2);  //id Localidad = 2
    when(apiClient.nombreMunicipio(2)).thenReturn("Valcheta");
    when(apiClient.verificarNombreMunicipio("Valcheta")).thenReturn(4);   //id Municipio = 4
    when(apiClient.nombreProvincia(4)).thenReturn("Rio Negro");
    when(apiClient.verificarNombreProvincia("Rio Negro")).thenReturn(7);  //id Provincia = 7

    ubicacionDefault = new Ubicacion("Corrientes", 1200, "PUERTO LEONI", apiClient);
  }

  // Trayecto Compartido -> Solo Vehiculos Particulares (VP) o Servicios Contratados (SC)

  @Test
  public void noPuedoCrearUnTrayectoCompartidoQueNoSeaDeTipoVPoSC() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro, miembro2).collect(Collectors.toList());

    Bicicleta bici = new Bicicleta(ubicacionDefault, ubicacionDefault, apiClient);
    Tramo tramo = new Tramo(bici);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    assertThrows(RuntimeException.class, () -> new TrayectoCompartido(miembros, tramos));
  }

  @Test
  public void puedoCrearUnTrayectoCompartidoEntreDosPibes() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro, miembro2).collect(Collectors.toList());

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, ubicacionDefault, ubicacionDefault, apiClient, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    assertDoesNotThrow(() -> new TrayectoCompartido(miembros, tramos));

  }
  @Test
  public void elOwnerDeUnTrayectoCompartidoEntreDosPibesEsElCorrecto() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro, miembro2).collect(Collectors.toList());

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, ubicacionDefault, ubicacionDefault, apiClient, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido(miembros, tramos);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    assertTrue(trayectoCompartido.ownerIs(miembro));
    assertFalse(trayectoCompartido.ownerIs(miembro2));

  }

  @Test
  public void UnTrayectoCompartidoEntreDosPibesFiguraEnAmbosPibes() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro, miembro2).collect(Collectors.toList());

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, ubicacionDefault, ubicacionDefault, apiClient, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido(miembros, tramos);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    assertTrue(miembro.containsTrayecto(trayectoCompartido));
    assertTrue(miembro2.containsTrayecto(trayectoCompartido));

  }

  @Test
  public void sePuedeRegistrarUnTrayectoCompartidoSinQueElOwnerEsteEntreLosMiembros() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro2).collect(Collectors.toList());
    //Ahora en la lista SOLO esta el miembro con el q compartio, este seria 'miembro2'

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, ubicacionDefault, ubicacionDefault, apiClient, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido(miembros, tramos);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    assertTrue(trayectoCompartido.ownerIs(miembro));
    assertFalse(trayectoCompartido.ownerIs(miembro2));

  }

  // De aca para abajo se prueban funcionalidades q puede q no se usen

  @Test
  public void aUnTrayectoCompartidoSeLePuedenPedirLosMiembros() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro, miembro2).collect(Collectors.toList());

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, ubicacionDefault, ubicacionDefault, apiClient, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido(miembros, tramos);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    assertEquals(miembros, trayectoCompartido.miembros());

  }

  @Test
  public void aUnTrayectoCompartidoSeLePuedenPedirLosMiembrosAunSiElOwnerNoEstaEntreLosMiembros() {
    Miembro miembro = new Miembro("Crayon", "Lambert", TipoDeDocumento.DNI, 23666920);
    Miembro miembro2 = new Miembro("El", "Pibe", TipoDeDocumento.DNI, 50501502);
    List<Miembro> miembros = Stream.of(miembro2).collect(Collectors.toList());
    //Ahora en la lista SOLO esta el miembro con el q compartio, este seria 'miembro2'

    TipoServicioContratado taxi = new TipoServicioContratado("taxi");
    ServicioContratado servicioContratado = new ServicioContratado(
        taxi, ubicacionDefault, ubicacionDefault, apiClient, 500.0
    );
    Tramo tramo = new Tramo(servicioContratado);
    List<Tramo> tramos = Stream.of(tramo).collect(Collectors.toList());

    TrayectoCompartido trayectoCompartido = new TrayectoCompartido(miembros, tramos);

    miembro.registrarTrayecto(trayectoCompartido); // owner -> miembro

    List<Miembro> miembrosFinales = new ArrayList<>();
    miembrosFinales.add(miembro);
    miembrosFinales.add(miembro2);
    assertEquals(miembrosFinales, trayectoCompartido.miembros());

  }

}
