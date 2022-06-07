package domain.servicios.geodds;

import static java.net.URLEncoder.encode;

import domain.servicios.geodds.entidades.Distancia;
import domain.servicios.geodds.entidades.Localidad;
import domain.servicios.geodds.entidades.Municipio;
import domain.servicios.geodds.entidades.Pais;
import domain.servicios.geodds.entidades.Provincia;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoDds {
  // Clase Singleton
  private static ServicioGeoDds instancia = null;
  private static final String urlAPI = "https://ddstpa.com.ar/api/";
  private Retrofit retrofit;

  private ServicioGeoDds() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static ServicioGeoDds getInstancia() {
    if (instancia == null) {
      instancia = new ServicioGeoDds();
    }

    return instancia;
  }

  public List<Pais> listadoDePaises(int offset) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Pais>> requestPaises = geoDdsService.paises(offset);
    Response<List<Pais>> responsePaises = requestPaises.execute();

    return responsePaises.body();
  }

  public List<Provincia> listadoDeProvincias(int offset) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Provincia>> requestProvincias = geoDdsService.provincias(offset);
    Response<List<Provincia>> responseProvincias = requestProvincias.execute();

    return responseProvincias.body();
  }

  public List<Provincia> listadoDeProvincias(int offset, int paisId) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Provincia>> requestProvincias = geoDdsService.provincias(offset, paisId);
    Response<List<Provincia>> responseProvincias = requestProvincias.execute();

    return responseProvincias.body();
  }

  public List<Municipio> listadoDeMunicipios(int offset) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Municipio>> requestMunicipios = geoDdsService.municipios(offset);
    Response<List<Municipio>> responseMunicipios = requestMunicipios.execute();

    return responseMunicipios.body();
  }

  public List<Municipio> listadoDeMunicipios(int offset, int provinciaId) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Municipio>> requestMunicipios = geoDdsService.municipios(offset, provinciaId);
    Response<List<Municipio>> responseMunicipios = requestMunicipios.execute();

    return responseMunicipios.body();
  }

  public Distancia distanciaEntreUbicaciones(int localidadOrigenId,
                                             String calleOrigen,
                                             int alturaOrigen,
                                             int localidadDestinoId,
                                             String calleDestino,
                                             int alturaDestino) throws IOException {

    calleOrigen = encode(calleOrigen, "UTF-8");
    calleDestino = encode(calleDestino, "UTF-8");

    RetrofitGeoDds retrofitGeoDds = this.retrofit.create(RetrofitGeoDds.class);

    Call<Distancia> requestDistancia = retrofitGeoDds.distancia(localidadOrigenId,
        calleOrigen,
        alturaOrigen,
        localidadDestinoId,
        calleDestino,
        alturaDestino);

    Response<Distancia> responseDistancia = requestDistancia.execute();

    return responseDistancia.body();
  }

  /* =============== Métodos para mapear =============== */

  /**
   * Mapea todos los paises que tiene disponibles la api
   *
   * @return Map (Nombre, ID) del país
   */
  public Map<String, Integer> mapPaises(int offset) throws  IOException {
    List<Pais> listadoDePaises = this.listadoDePaises(offset);

    return listadoDePaises
        .stream()
        .collect(Collectors.toMap(Pais::getNombre, Pais::getId));
  }

  public Map<String, Provincia> mapProvincias(int offset) throws IOException {
    List<Provincia> listadoDeProvincias = this.listadoDeProvincias(offset);

    return listadoDeProvincias
        .stream()
        .collect(Collectors.toMap(Provincia::getNombre, Provincia::getItself));
  }

  /* =================== Métodos para verificar existencia =================== */


  public void validarId(Integer id, String mensajeError) {
    if (id == null) {
      throw new RuntimeException(mensajeError);
    }
  }


  /**
   * Verifica que un país exista y retorna el ID del mismo.
   *
   * @param nombrePais Nombre del país que se está queriendo validar
   * @return ID del país en caso de que exista.
   */
  public int verificarNombrePais(String nombrePais) throws RuntimeException, IOException {

    Map<String, Integer> paises = this.mapPaises(1); // Siempre se usa con offset 1 en este caso
    Integer id = paises.get(nombrePais.toUpperCase());

    this.validarId(id, "No se pudo encontrar el país");

    return id;
  }

  /**
   * Verifica que una provincia exista y retorna el ID de la misma.
   *
   * @param nombreProvincia nombre de la provincia que se está queriendo validar.
   * @return provincia
   */
  public Provincia verificarNombreProvincia(String nombreProvincia)
      throws RuntimeException, IOException {

    Map<String, Provincia> provincias = this.mapProvincias(1);
    Integer id = provincias.get(nombreProvincia.toUpperCase()).getId();

    this.validarId(id, "No se pudo encontrar la provincia");

    return provincias.get(nombreProvincia.toUpperCase());
  }
}
