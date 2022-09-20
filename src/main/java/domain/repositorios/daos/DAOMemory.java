package domain.repositorios.daos;

import domain.database.EntidadPersistente;

import java.util.List;

public class DAOMemory<T> implements DAO<T> {
    private List<EntidadPersistente> entidades;

    public DAOMemory(List<EntidadPersistente> entidades){
        this.entidades = entidades;
    }

    @Override
    public List<T> all() {
        return (List<T>) this.entidades;
    }

    @Override
    public T get(int id) {
        return (T) this.entidades
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .get();
    }

    @Override
    public void add(Object unObjeto) {
        this.entidades.add((EntidadPersistente) unObjeto);
    }

    @Override
    public void update(Object unObjeto) {

    }

    @Override
    public void delete(Object unObjeto) {
        this.entidades.remove(unObjeto);
    }
}
