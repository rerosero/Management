package ec.edu.uce.manapayment.crud.typepay;

import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import ec.edu.uce.manapayment.processor.PaymentProcessor;
import ec.edu.uce.manapayment.processor.PaymentProcessorFactory;
import ec.edu.uce.manapayment.validator.PaymentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TypePaysService {
    @Inject
    private EntityManager em;
    @Inject
    private PaymentValidator paymentValidator;
    @Inject
    private PaymentProcessorFactory paymentProcessorFactory;

    public TypePaysService() {
    }
    public void createTypePays(Typepays typePays) {
        try {
            if(!paymentValidator.isTypePayValid(typePays)){
                throw new IllegalArgumentException("El tipo de pago no puede ser nulo.");
            }
            em.getTransaction().begin();
            em.persist(typePays);
            em.getTransaction().commit();
        }catch(Exception e) {
            System.err.println("Error al guardar el tipo de pago: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void saveTypePays(Typepays typePays) {
        try {
            em.getTransaction().begin();
            em.persist(typePays);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo guardar el tipo de pago: "+e.getMessage());
        }
    }

    public List<Typepays> findAllTypePays() {
        try {
            // Realiza la consulta para obtener todos los registros de la entidad Typepays
            return em.createQuery("SELECT t FROM Typepays t", Typepays.class).getResultList();
        } catch (Exception e) {
            System.err.println("No se pudieron encontrar los tipos de pago: " + e.getMessage());
            e.printStackTrace();
            // Devuelve una lista vacía en caso de error
            return new ArrayList<>();
        }
    }

    public Typepays findByIdTypePays(int id) {
        try {
            return em.find(Typepays.class, id);
        } catch (Exception e) {
            System.err.println("No se pudo buscar el tipo de pago con ID " + id + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public void updateTypepays (Typepays typePays) {
        try {
            if(!paymentValidator.isTypePayValid(typePays)){
                throw new IllegalArgumentException("El tipo de pago no puede ser nulo.");
            }
            em.getTransaction().begin();
            this.em.merge(typePays);
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar el usuario: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteTypepays (int id) {
        try {
            em.getTransaction().begin();
            Typepays typePays = em.find(Typepays.class, id);
            if(typePays != null) {
                em.remove(typePays);
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
