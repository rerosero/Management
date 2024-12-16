package ec.edu.uce.manapayment.crud.paypal;

import ec.edu.uce.manapayment.jpa.paypal.PayPal;
import ec.edu.uce.manapayment.validator.PaymentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PayPalService {
    @Inject
    private EntityManager em;
    @Inject
    private PaymentValidator paymentValidator;
    public PayPalService() {}
    public void createPayPal(PayPal payPal) {
        try {
            em.getTransaction().begin();
            em.persist(payPal);
            em.getTransaction().commit();
        }catch(Exception e) {
            System.err.println("No se pudo guardar el pago PayPal: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public PayPal findByIdPayPal(int id) {
        try {
            return em.find(PayPal.class, id);
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el pago con PayPal: "+e.getMessage());
            return null;
        }
    }
    public PayPal findByEmailPayPal(String email) {
        try {
            return em.createQuery("SELECT p FROM PayPal p WHERE p.emailPaypal = :email", PayPal.class)
                    .setParameter("email", email)
                    .getSingleResult();
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el pago con PayPal: "+e.getMessage());
            return null;
        }
    }
    public List<PayPal> findAllPayPal() {
        try {
            return em.createQuery("SELECT p FROM PayPal p", PayPal.class).getResultList();
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el pago con PayPal: "+e.getMessage());
            return new ArrayList<>();
        }
    }
    public void updatePayPal(PayPal payPal) {
        try {
            em.getTransaction().begin();
            this.em.merge(payPal);
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar el pago con PayPal: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void deletePayPal(int id) {
        try {
            em.getTransaction().begin();
            PayPal payPal = em.find(PayPal.class, id);
            if (payPal != null) {
                em.remove(payPal);
            } else {
                System.err.println("No se pudo eliminar el pago con PayPal: " + id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo eliminar el pago con PayPal: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
