package domain.organizaciones.hc;

import domain.PersistenceEntity;

import javax.persistence.*;

@Entity
@Table
public class HC extends PersistenceEntity {

  @Column
  private double valor;

  @Enumerated(EnumType.STRING)
  private UnidadHC unidadHC;

  public HC(double valor, UnidadHC unidadHC) {
    this.valor = valor;
    this.unidadHC = unidadHC;
  }

  public HC(){}

  public double enGCO2() {
    if(unidadHC.equals(UnidadHC.gCO2)) {
      return valor;
    } else if(unidadHC.equals(UnidadHC.kgCO2)) {
      return valor * 1000;
    } else {// if(unidadHC.equals(UnidadHC.tnCO2)) {
      return valor * 1000000; // 1 millon
    }
  }

  public double enKgCO2() {
    if(unidadHC.equals(UnidadHC.kgCO2)) {
      return valor;
    } else if(unidadHC.equals(UnidadHC.gCO2)) {
      return valor / 1000;
    } else {// if(unidadHC.equals(UnidadHC.tnCO2)) {
      return valor * 1000;
    }
  }

  public double enTnCO2() {
    if(unidadHC.equals(UnidadHC.tnCO2)) {
      return valor;
    } else if(unidadHC.equals(UnidadHC.kgCO2)) {
      return valor / 1000;
    } else {// if(unidadHC.equals(UnidadHC.tnCO2)) {
      return valor / 1000000; // 1 millon
    }
  }
}
