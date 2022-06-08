package domain.servicios.geodds;

import static java.net.URLEncoder.encode;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import domain.servicios.geodds.entidades.*;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
  private String apiKey;

  private ServicioGeoDds() {
    this.retrofit = new Retrofit.Builder()
        .baseUrl(urlAPI)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    // set apiKey value from local.properties
    try{
      InputStream input = new FileInputStream("src/main/java/domain/servicios/geodds/local.properties");
      Properties properties = new Properties();
      properties.load(input);
      this.apiKey = "Bearer " + properties.getProperty("apiKey");
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

  }

  public static ServicioGeoDds getInstancia() {
    if (instancia == null) {
      instancia = new ServicioGeoDds();
    }

    return instancia;
  }

  /** =============== Métodos para hacer Request a API GeoDds =============== **/

  public List<Pais> listadoDePaises(int offset) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Pais>> requestPaises = geoDdsService.paises(this.apiKey, offset);
    Response<List<Pais>> responsePaises = requestPaises.execute();

    return responsePaises.body();
  }

  public List<Provincia> listadoDeProvincias(int offset) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Provincia>> requestProvincias = geoDdsService.provincias(this.apiKey, offset);
    Response<List<Provincia>> responseProvincias = requestProvincias.execute();

    return responseProvincias.body();
  }

  public List<Provincia> listadoDeProvincias(int offset, int paisId) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Provincia>> requestProvincias = geoDdsService.provincias(this.apiKey, offset, paisId);
    Response<List<Provincia>> responseProvincias = requestProvincias.execute();

    return responseProvincias.body();
  }

  public List<Municipio> listadoDeMunicipios(int offset) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Municipio>> requestMunicipios = geoDdsService.municipios(this.apiKey, offset);
    Response<List<Municipio>> responseMunicipios = requestMunicipios.execute();

    return responseMunicipios.body();
  }

  public List<Municipio> listadoDeMunicipios(int offset, int provinciaId) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Municipio>> requestMunicipios = geoDdsService.municipios(this.apiKey, offset, provinciaId);
    Response<List<Municipio>> responseMunicipios = requestMunicipios.execute();

    return responseMunicipios.body();
  }

  public List<Localidad> listadoDeLocalidades(int offset) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Localidad>> requestLocalidades = geoDdsService.localidades(this.apiKey, offset);
    Response<List<Localidad>> responseLocalidades = requestLocalidades.execute();

    return responseLocalidades.body();
  }

  public List<Localidad> listadoDeLocalidades(int offset, int idMunicipio) throws IOException {
    RetrofitGeoDds geoDdsService = this.retrofit.create(RetrofitGeoDds.class);

    Call<List<Localidad>> requestLocalidades = geoDdsService.localidades(this.apiKey, offset, idMunicipio);
    Response<List<Localidad>> responseLocalidades = requestLocalidades.execute();

    return responseLocalidades.body();
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

    Call<Distancia> requestDistancia = retrofitGeoDds.distancia(this.apiKey,
        localidadOrigenId,
        calleOrigen,
        alturaOrigen,
        localidadDestinoId,
        calleDestino,
        alturaDestino);

    Response<Distancia> responseDistancia = requestDistancia.execute();

    return responseDistancia.body();
  }

  /** =============== Métodos para mapear =============== **/

  /**
   * Mapea todos los paises que tiene disponibles la api
   *
   * @return Map (Nombre, ID) con los países
   */
  public Map<String, Integer> mapPaises(int offset) throws  IOException {
    List<Pais> listadoDePaises = this.listadoDePaises(offset);

    return listadoDePaises
        .stream()
        .collect(Collectors.toMap(Pais::getNombre, Pais::getId));
  }

  /**
   * Mapea todos los paises que tiene disponibles la api
   *
   * @return Map (Nombre, ID) con las provincias
   */
  public Map<String, Integer> mapProvincias(int offset) throws IOException {
    List<Provincia> listadoDeProvincias = this.listadoDeProvincias(offset);

    return listadoDeProvincias
        .stream()
        .collect(Collectors.toMap(Provincia::getNombre, Provincia::getId));
  }

  /**
   * Mapea todos los municipios que tiene disponibles la api
   *
   * @return Map (Nombre, ID) de los municipios
   */
  public Map<String, Integer> mapMunicipios(int offset) throws  IOException {
    List<Municipio> listadoDeMunicipios = this.listadoDeMunicipios(offset);

    return listadoDeMunicipios
        .stream()
        .collect(Collectors.toMap(Municipio::getNombre, Municipio::getId));
  }


  /**
   * Mapea todas las localidades que tiene disponibles la api
   *
   * @return Map (Nombre, ID) de las localidades
   */
  public Map<String, Integer> mapLocalidades(int offset) throws IOException {
    List<Localidad> listadoDeLocalidades = this.listadoDeLocalidades(offset);

    return listadoDeLocalidades
        .stream()
        .collect(Collectors.toMap(Localidad::getNombre, Localidad::getId));
  }

  /** =================== Métodos para verificar existencia =================== **/

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
   * @return ID de la provincia
   */
  public int verificarNombreProvincia(String nombreProvincia)  throws RuntimeException, IOException {

    Map<String, Integer> provincias = this.mapProvincias(1);
    Integer id = provincias.get(nombreProvincia.toUpperCase());

    this.validarId(id, "No se pudo encontrar la provincia");

    return id;
  }

  /**
   * Verifica que un municipio exista y retorna el ID de la misma.
   *
   * @param nombreMunicipio nombre del municipio que se está queriendo validar.
   * @return ID del municipio
   */
  public int verificarNombreMunicipio(String nombreMunicipio) throws  RuntimeException, IOException {
    Map<String, Integer> municipios = this.mapMunicipios(1);
    Integer id = municipios.get(nombreMunicipio.toUpperCase());

    this.validarId(id, "No se encontró el municipio");

    return id;
  }

  /**
   * Verifica que una provincia exista y retorna el ID de la misma.
   *
   * @param nombreLocalidad nombre de la localidad que se está queriendo validar.
   * @return ID de la localidad
   */
  public int verificarNombreLocalidad(String nombreLocalidad) throws RuntimeException, IOException {
    Map<String, Integer> localidades = this.mapLocalidades(1);
    Integer id = localidades.get(nombreLocalidad.toUpperCase());

    this.validarId(id, "No se pudo encontrar la localidad");

    return id;
  }
}
