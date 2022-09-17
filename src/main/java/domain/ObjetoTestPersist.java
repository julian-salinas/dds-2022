package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ObjetoTestPersist {

  @Id @GeneratedValue
  int id;

  String nombre;

  public ObjetoTestPersist() {}

  public ObjetoTestPersist(String nombre) {
    this.nombre = nombre;
  }

}
