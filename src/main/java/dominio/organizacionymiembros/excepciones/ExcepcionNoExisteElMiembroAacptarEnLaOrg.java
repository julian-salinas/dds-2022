package dominio.organizacionymiembros.excepciones;

public class ExcepcionNoExisteElMiembroAacptarEnLaOrg extends RuntimeException {
  public ExcepcionNoExisteElMiembroAacptarEnLaOrg() {
    super("Se trato de aceptar a un miembro que no pidio vincularse,"
        + " no esta vinculado a la org o no existe");
  }
}
