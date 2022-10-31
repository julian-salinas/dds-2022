package domain.organizaciones.datos.actividades.tipos;

import domain.database.PersistenceEntity;
import domain.organizaciones.datos.actividades.Actividad;
import domain.organizaciones.datos.actividades.Alcance;
import domain.organizaciones.datos.actividades.UnidadConsumo;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "tipo_de_consumo")
@Entity
public class TipoDeConsumo extends PersistenceEntity {
  @Column(name = "nombre_tipo")
  private String tipo;
  @Column(name = "unidad_consumo")
  @Enumerated(EnumType.STRING)
  private UnidadConsumo unidad;
  @Enumerated(EnumType.STRING)
  private Actividad actividad;
  @Enumerated(EnumType.STRING)
  private Alcance alcance;
  @Embedded
  @Getter private FactorEmision fe;

  public TipoDeConsumo() {}

  public TipoDeConsumo(String tipo, UnidadConsumo unidad, Actividad actividad, Alcance alcance) {
    this.tipo = tipo;
    this.unidad = unidad;
    this.actividad = actividad;
    this.alcance = alcance;
  }

  public void cargarFactorEmision(FactorEmision fe){
    if(unidad.equals(fe.getUnidad())) {
      this.fe = fe;
    } else {
      throw new NoCoincidenUnidadesFEYTC();
    }
  }

}
