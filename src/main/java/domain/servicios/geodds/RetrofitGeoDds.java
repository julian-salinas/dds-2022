package domain.servicios.geodds;

import domain.servicios.geodds.entidades.Distancia;
import domain.servicios.geodds.entidades.Localidad;
import domain.servicios.geodds.entidades.Municipio;
import domain.servicios.geodds.entidades.Pais;
import domain.servicios.geodds.entidades.Provincia;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RetrofitGeoDds {

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("paises")
  Call<List<Pais>> paises(@Query("offset") int offset);

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("provincias")
  Call<List<Provincia>> provincias(@Query("offset") int offset);

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("provincias")
  Call<List<Provincia>> provincias(@Query("offset") int offset, @Query("paisId") int paisId);

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("localidades")
  Call<List<Localidad>> localidades(@Query("offset") int offset);

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("localidades")
  Call<List<Localidad>> localidades(@Query("offset") int offset,
                                    @Query("municipioId") int municipioId);

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("municipios")
  Call<List<Municipio>> municipios(@Query("offset") int offset);

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("municipios")
  Call<List<Municipio>> municipios(@Query("offset") int offset,
                                   @Query("provinciaId") int provinciaId);

  @Headers({
      "Accept: application/json",
      "Content-Type: application/json",
      "Authorization: Bearer INXPj19d4ui6pZ6aAcXs5FyFmtLtwa72qk+13NESOWA="
  })
  @GET("distancia")
  Call<Distancia> distancia(@Query("LocalidadOrigenId") int localidadOrigenId,
                            @Query("calleOrigen") String calleOrigen,
                            @Query("alturaOrigen") int alturaOrigen,
                            @Query("LocalidadDestinoId") int localidadDestinoId,
                            @Query("calleDestino") String calleDestino,
                            @Query("alturaDestino") int alturaDestino);
}
