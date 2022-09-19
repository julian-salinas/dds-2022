package domain.repositorios.daos;

import java.util.List;

public interface DAO<T>{
    /**
     * Data Access Object 😏🍔
     */
    List<T> buscarTodos();

    T buscar(int id);

    void agregar(Object objeto);

    void eliminar(Object objeto);

    void modificar(Object objeto);
}
