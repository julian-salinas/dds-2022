package domain.organizaciones.datos.actividades;

import domain.database.PersistenceEntity;
import domain.organizaciones.datos.actividades.tipos.FactorEmision;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumo;
import domain.organizaciones.datos.actividades.tipos.TipoDeConsumoFactory;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Table(name = "datos_actividades")
@Entity
public class DatosActividades extends PersistenceEntity {
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "consumo_id")
  TipoDeConsumo tipoDeConsumo;
  @Column(name = "valor_actividad")
  double valor;
  String periodicidad;
  @Column(name = "periodo_imputacion")
  String periodoImputacion;

  public DatosActividades() {}

  public DatosActividades(String tipo, String valor, String periodicidad, String periodoImputacion) {
      this.tipoDeConsumo = TipoDeConsumoFactory.instance().buildTipoDeConsumo(tipo);
      this.valor = Double.parseDouble(valor);
      this.periodicidad = periodicidad;
      this.periodoImputacion = periodoImputacion;
    }

    public double impactoHC() {
      if (this.periodicidad.equals("Anual")){
        return (valor * tipoDeConsumo.getFe().getValor())/12.0;
      } else {
        return valor * tipoDeConsumo.getFe().getValor();
      }
    }

    public void cargarFactorEmision(FactorEmision fe) {
      tipoDeConsumo.cargarFactorEmision(fe);
    }

}


