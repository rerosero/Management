package ec.edu.uce.manapayment.initapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ManagementUnity");

    @Produces
    @RequestScoped
    public EntityManager produceEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public void disposeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}