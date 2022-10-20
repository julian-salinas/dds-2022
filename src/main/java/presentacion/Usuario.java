package presentacion;

import domain.database.PersistenceEntity;
import domain.organizaciones.Organizacion;
import domain.organizaciones.miembros.Miembro;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Usuario extends PersistenceEntity {
  String username;
  String password;
  @Enumerated(EnumType.STRING)
  TipoUsuario tipo;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  Organizacion org;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  Miembro miembro;


  public Usuario(String username, String password, TipoUsuario tipo){
    this.username = username;
    this.password = password;
    this.tipo = tipo;
  }

  public Usuario(){

  }
}
