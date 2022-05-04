package administrador.contrasenia.excepciones;

public class ExcepcionContraseniaComun extends RuntimeException {
  public ExcepcionContraseniaComun() {
    super("Tu contraseña se encuentra en el top 1000 peores contraseñas");
  }
}
