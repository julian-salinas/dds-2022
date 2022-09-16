package domain;

import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Workspace {

  public static void main(String[] args) {

    EntityManager entityManager = PerThreadEntityManagers.getEntityManager();
    EntityTransaction tx = entityManager.getTransaction();
    tx.begin();
    ObjetoTestPersist obj = new ObjetoTestPersist(3, "Juan");
    entityManager.persist(obj);
    tx.commit();

    //List<ObjetoTestPersist> difusiones = entityManager.createQuery("from ObjetoTestPersist").getResultList();

    entityManager.close();
  }
}