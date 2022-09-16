package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ObjetoTestPersist {

  @Id
  int id;

  String nombre;

  public ObjetoTestPersist() {}

  public ObjetoTestPersist(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

}
