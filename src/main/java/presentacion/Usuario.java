package presentacion;

import domain.database.PersistenceEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
public class Usuario extends PersistenceEntity {
  String username;
  String password;
  @Enumerated(EnumType.STRING)
  TipoUsuario tipo;

  public Usuario(String username, String password, TipoUsuario tipo){
    this.username = username;
    this.password = password;
    this.tipo = tipo;
  }

  public Usuario(){

  }
}
