package domain.trayecto.transporte;

import domain.ubicaciones.Distancia;

public interface MedioDeTransporte {
  Distancia getDistancia();
  TipoDeTransporte getTipoBase();
}
