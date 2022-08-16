package domain.trayecto.transporte;

import domain.ubicaciones.Distancia;

public interface MedioDeTransporte {
  Distancia distancia();
  //TipoDeTransporte getTipoBase();
  Boolean admiteTrayectoCompartido();

  double getCombustibleConsumidoPorKM();
}
