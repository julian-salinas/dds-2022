package domain.trayecto.transporte;

import domain.ubicaciones.Distancia;

public interface MedioDeTransporte {
  Distancia distancia();
  Boolean admiteTrayectoCompartido();
  double getCombustibleConsumidoPorKM();
}
