package domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistenceEntity {
  @Id @GeneratedValue
  int id;
}
