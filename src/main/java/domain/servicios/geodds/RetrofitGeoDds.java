package domain.servicios.geodds;

import domain.servicios.geodds.entidades.Distancia;
import domain.servicios.geodds.entidades.Localidad;
import domain.servicios.geodds.entidades.Municipio;
import domain.servicios.geodds.entidades.Pais;
import domain.servicios.geodds.entidades.Provincia;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitGeoDds {

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json",
  })
  @GET("paises")
  Call<List<Pais>> paises(@Header("Authorization") String token, @Query("offset") int offset);

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json"
  })
  @GET("provincias")
  Call<List<Provincia>> provincias(@Header("Authorization") String token, @Query("offset") int offset);

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json"
  })
  @GET("provincias")
  Call<List<Provincia>> provincias(@Header("Authorization") String token, @Query("offset") int offset, @Query("paisId") int paisId);

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json"
  })
  @GET("localidades")
  Call<List<Localidad>> localidades(@Header("Authorization") String token, @Query("offset") int offset);

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json",
  })
  @GET("localidades")
  Call<List<Localidad>> localidades(@Header("Authorization") String token,
                                    @Query("offset") int offset,
                                    @Query("municipioId") int municipioId);

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json",
  })
  @GET("municipios")
  Call<List<Municipio>> municipios(@Header("Authorization") String token,
                                   @Query("offset") int offset);

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json",
  })
  @GET("municipios")
  Call<List<Municipio>> municipios(@Header("Authorization") String token,
                                   @Query("offset") int offset,
                                   @Query("provinciaId") int provinciaId);

  @Headers({
    "Accept: application/json",
    "Content-Type: application/json",
  })
  @GET("distancia")
  Call<Distancia> distancia(@Header("Authorization") String token,
                            @Query("LocalidadOrigenId") int localidadOrigenId,
                            @Query("calleOrigen") String calleOrigen,
                            @Query("alturaOrigen") int alturaOrigen,
                            @Query("LocalidadDestinoId") int localidadDestinoId,
                            @Query("calleDestino") String calleDestino,
                            @Query("alturaDestino") int alturaDestino);
}
