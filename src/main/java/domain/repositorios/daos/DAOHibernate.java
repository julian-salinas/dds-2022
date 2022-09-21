package domain.repositorios.daos;

import domain.database.EntityManagerHelper;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DAOHibernate<T> implements DAO<T> {
    private Class<T> type;

    public DAOHibernate(Class<T> type){
        this.type = type;
    }

    @Override
    public List<T> all() {
        return EntityManagerHelper.getEntityManager().createQuery("SELECT * FROM " + type.getName()).getResultList();
    }

    @Override
    public T get(int id) {
        return EntityManagerHelper.getEntityManager().find(type, id);
    }

    @Override
    public void add(Object object) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().persist(object);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void update(Object object) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().merge(object);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void delete(Object object) {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        EntityManagerHelper.getEntityManager().remove(object);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }

    @Override
    public void clean() {
        EntityManagerHelper.getEntityManager().getTransaction().begin();
        CriteriaBuilder cb = EntityManagerHelper.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(type);
        query.from(type);
        EntityManagerHelper.getEntityManager().createQuery(query).getResultList().forEach(EntityManagerHelper.getEntityManager()::remove);
        EntityManagerHelper.getEntityManager().getTransaction().commit();
    }
}