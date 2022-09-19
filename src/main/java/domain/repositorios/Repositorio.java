package domain.repositorios;

import domain.repositorios.daos.DAO;
import java.util.List;

public class Repositorio<T> {
    protected DAO<T> dao;

    public Repositorio(DAO<T> dao) {
        this.dao = dao;
    }

    public void setDao(DAO<T> dao) {
        this.dao = dao;
    }

    public List<T> buscarTodos(){
        return this.dao.buscarTodos();
    }

    public T buscar(int id){
        return this.dao.buscar(id);
    }

    public void agregar(Object object){
        this.dao.agregar(object);
    }

    public void modificar(Object object){
        this.dao.modificar(object);
    }

    public void eliminar(Object object){
        this.dao.eliminar(object);
    }

}
