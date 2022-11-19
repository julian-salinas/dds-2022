package domain.ubicaciones.sectores;

import domain.database.PersistenceEntity;
import domain.organizaciones.hc.HC;
import domain.organizaciones.Organizacion;
import domain.organizaciones.hc.UnidadHC;
import lombok.Getter;
import lombok.Setter;
import repositorios.RepositorioOrganizaciones;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "agente_sectorial")
@Getter @Setter
public class AgenteSectorial extends PersistenceEntity {
  String nombreAgente;
  int idSectorTerritorial;
  String nombreSectorTerritorial;
  @Enumerated(EnumType.STRING)
  TipoSectorTerritorial tipoSectorTerritorial;
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) @JoinColumn(name = "idSectorTerritorial")
  private List<HC> historialHCTotal = new ArrayList<>();

  public AgenteSectorial() {

  }

  public AgenteSectorial(TipoSectorTerritorial tipo, int id, String nombre) {
    this.tipoSectorTerritorial = tipo;
    this.idSectorTerritorial = id;
    this.nombreSectorTerritorial = nombre;
    //this.sectorTerritorial = encontrarSectorTerritorial();
  }

  public List<Organizacion> encontrarOrgs() {
    if(tipoSectorTerritorial.equals(TipoSectorTerritorial.MUNICIPIO)) {
      return RepositorioOrganizaciones.getInstance().inMunicipio(idSectorTerritorial);
    }
    else {  // tipoSecTerritorial.equals(TipoSectorTerritorial.PROVINCIA)
      return RepositorioOrganizaciones.getInstance().inProvincia(idSectorTerritorial);
    }
  }

  public HC hcSectorMensual(String mes) {
    List<Organizacion> orgInSectorTerr = encontrarOrgs();
    double hcEnKgCO2 = orgInSectorTerr.stream().mapToDouble(org -> org.hcMensual(mes).enKgCO2()).sum();
    return new HC(hcEnKgCO2, UnidadHC.kgCO2);
  }

  public HC hcSectorAnual(String anio) {
    List<Organizacion> orgInSectorTerr = encontrarOrgs();
    double hcEnKgCO2 = orgInSectorTerr.stream().mapToDouble(org -> org.hcAnual(anio).enKgCO2()).sum();
    return new HC(hcEnKgCO2, UnidadHC.kgCO2);
  }

  public HC hcTotal(){
    List<Organizacion> orgInSectorTerr = encontrarOrgs();
    double valorHC = orgInSectorTerr.stream().mapToDouble(org -> org.hcTotal().enKgCO2()).sum();
    HC hc = new HC(valorHC, UnidadHC.kgCO2);

    historialHCTotal.add(hc);
    return hc;
  }

}
