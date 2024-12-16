package ec.edu.uce.manapayment.crud.client;

import ec.edu.uce.manapayment.jpa.client.Client;
import ec.edu.uce.manapayment.security.EncryptionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClientService {
    @Inject
    private EntityManager em;
    @Inject
    private EncryptionService encryptionService;

    public ClientService() {}

    public void createClient(Client client) {
        try {
            // Cifra la contraseña antes de guardarla
            String hashedPassword = encryptionService.hashPassword(client.getPassword());
            client.setPassword(hashedPassword);

            em.getTransaction().begin();
            em.persist(client);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo registrar el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Client FindByIdClient(int id) {
        try {
            return em.find(Client.class, id);
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el cliente: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public Client findByEmailClient(String email) {
        try {
            return em.createQuery("SELECT c FROM Client c WHERE c.email = :email", Client.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch(Exception e) {
            System.err.println("No se pudo buscar el usuario: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public List<Client> findAllClient(){
        try {
            return em.createQuery("SELECT c FROM Client c", Client.class).getResultList(); //lista de todas las tarjetas
        }catch(Exception e) {
            System.err.println("No se pudo encontrar los clientes: "+e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    public void updateClient(Client client) {
        try {
            em.getTransaction().begin();
            this.em.merge(client);
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar el usuario: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteClient(int id) {
        try {
            em.getTransaction().begin();
            Client client = em.find(Client.class, id);
            if(client != null) {
                em.remove(client);
            }else {
                System.err.println("No se pudo eliminar el usuario: "+id);
            }
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo eliminar el usuario: "+e.getMessage());
            e.printStackTrace();
        }

    }

}
