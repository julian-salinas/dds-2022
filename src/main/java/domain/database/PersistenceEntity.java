package domain.database;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class PersistenceEntity {
  @Id @GeneratedValue
  @Getter int id;
}
