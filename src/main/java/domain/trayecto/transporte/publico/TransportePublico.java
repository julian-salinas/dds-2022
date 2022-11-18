package domain.trayecto.transporte.publico;

import domain.trayecto.transporte.MedioDeTransporte;
import domain.ubicaciones.Ubicacion;
import domain.ubicaciones.distancia.Distancia;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "transporte_publico")
public class TransportePublico extends MedioDeTransporte {
  @Enumerated(EnumType.STRING)
  private TipoTransportePublico tipo;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Linea linea;
  @Getter @Setter private double combustibleConsumidoPorKM = 0.0;
  //@Getter private String descripcion

  public TransportePublico() {}

  public TransportePublico(TipoTransportePublico tipo, Linea linea) {
    this.tipo = tipo;
    this.linea = linea;
    this.descripcion = this.toString();
  }

  public void validacionParadas(Parada paradaInicio, Parada paradaFin) {
    linea.findParada(paradaInicio.getUbicacionParada());
    linea.findParada(paradaFin.getUbicacionParada());
  }

  @Override
  public Distancia distancia(Ubicacion ubicacionInicio, Ubicacion ubicacionFin) {
    Parada paradaInicio = linea.findParada(ubicacionInicio);
    Parada paradaFin = linea.findParada(ubicacionFin);
    return linea.distanciaEntreParadas(paradaInicio, paradaFin);
  }

  @Override
  public Boolean admiteTrayectoCompartido() {
    return false;
  }

  @Override
  public String toString() {
    // "Transporte Publico: " +
    return tipo.toString().toUpperCase() + " " +
        linea.getNombre().toUpperCase();
  }

}
