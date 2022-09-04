package domain.trayecto.transporte;

import domain.ubicaciones.distancia.Distancia;

public interface MedioDeTransporte {
  Distancia distancia();
  Boolean admiteTrayectoCompartido();
  double getCombustibleConsumidoPorKM();
}
