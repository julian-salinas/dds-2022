package domain.ubicaciones.services.geodds;

import domain.ubicaciones.services.geodds.entities.Distancia;
import domain.ubicaciones.services.geodds.entities.ListadoDeMunicipios;
import domain.ubicaciones.services.geodds.entities.ListadoDePaises;
import domain.ubicaciones.services.geodds.entities.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

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

  public ListadoDePaises listadoDePaises(int offset) throws IOException {
    GeoDdsService geoDdsService = this.retrofit.create(GeoDdsService.class);

    Call<ListadoDePaises> requestPaises = geoDdsService.paises(offset);
    Response<ListadoDePaises> responsePaises = requestPaises.execute();

    return responsePaises.body();
  }

  public ListadoDeProvincias listadoDeProvincias(int offset) throws IOException {
    GeoDdsService geoDdsService = this.retrofit.create(GeoDdsService.class);

    Call<ListadoDeProvincias> requestProvincias = geoDdsService.provincias(offset);
    Response<ListadoDeProvincias> responseProvincias = requestProvincias.execute();

    return responseProvincias.body();
  }

  public ListadoDeProvincias listadoDeProvincias(int offset, int paisId) throws IOException {
    GeoDdsService geoDdsService = this.retrofit.create(GeoDdsService.class);

    Call<ListadoDeProvincias> requestProvincias = geoDdsService.provincias(offset, paisId);
    Response<ListadoDeProvincias> responseProvincias = requestProvincias.execute();

    return responseProvincias.body();
  }

  public ListadoDeMunicipios listadoDeMunicipios(int offset) throws IOException {
    GeoDdsService geoDdsService = this.retrofit.create(GeoDdsService.class);

    Call<ListadoDeMunicipios> requestMunicipios = geoDdsService.municipios(offset);
    Response<ListadoDeMunicipios> responseMunicipios = requestMunicipios.execute();

    return responseMunicipios.body();
  }

  public ListadoDeMunicipios listadoDeMunicipios(int offset, int provinciaId) throws IOException {
    GeoDdsService geoDdsService = this.retrofit.create(GeoDdsService.class);

    Call<ListadoDeMunicipios> requestMunicipios = geoDdsService.municipios(offset, provinciaId);
    Response<ListadoDeMunicipios> responseMunicipios = requestMunicipios.execute();

    return responseMunicipios.body();
  }

  public Distancia distanciaEntreUbicaciones(int localidadOrigenId,
                                             String calleOrigen,
                                             int alturaOrigen,
                                             int localidadDestinoId,
                                             String calleDestino,
                                             int alturaDestino) throws IOException {
    GeoDdsService geoDdsService = this.retrofit.create(GeoDdsService.class);

    Call<Distancia> requestDistancia = geoDdsService.distancia(localidadOrigenId, calleOrigen, alturaOrigen,
                                                                localidadDestinoId, calleDestino, alturaDestino);

    Response<Distancia> responseDistancia = requestDistancia.execute();

    return responseDistancia.body();
  }
}
