package domain.ubicaciones.services.geodds;

import domain.ubicaciones.services.geodds.entities.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoDdsService {

  @GET("paises")
  Call<ListadoDePaises> paises(@Query("offset") int offset);

  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("offset") int offset);

  @GET("provincias")
  Call<ListadoDeProvincias> provincias(@Query("offset") int offset, @Query("paisId") int paisId);

  @GET("localidades")
  Call<ListadoDeLocalidades> localidades(@Query("offset") int offset);

  @GET("localidades")
  Call<ListadoDeLocalidades> localidades(@Query("offset") int offset, @Query("municipioId") int municipioId);

  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("offset") int offset);

  @GET("municipios")
  Call<ListadoDeMunicipios> municipios(@Query("offset") int offset, @Query("provinciaId") int provinciaId);

  @GET("distancia")
  Call<Distancia> distancia(@Query("LocalidadOrigenId") int localidadOrigenId,
                            @Query("calleOrigen") String calleOrigen,
                            @Query("alturaOrigen") int alturaOrigen,
                            @Query("LocalidadDestinoId") int localidadDestinoId,
                            @Query("calleDestino") String calleDestino,
                            @Query("alturaDestino") int alturaDestino);
}
