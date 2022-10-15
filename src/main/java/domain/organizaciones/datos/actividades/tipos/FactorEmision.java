package domain.organizaciones.datos.actividades.tipos;

import domain.organizaciones.datos.actividades.UnidadConsumo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class FactorEmision {
  @Column(name = "fe")
  @Getter @Setter private double valor;
  @Column(name = "unidad_fe")
  @Enumerated(EnumType.STRING)
  @Getter private UnidadConsumo unidad;

  public FactorEmision() {}

  public FactorEmision(double valor, UnidadConsumo unidad){
    this.valor = valor;
    this.unidad = unidad;
  }
}
