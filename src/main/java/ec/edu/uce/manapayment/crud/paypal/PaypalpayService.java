package ec.edu.uce.manapayment.crud.paypal;

import ec.edu.uce.manapayment.jpa.paypal.Paypalpay;
import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import ec.edu.uce.manapayment.validator.PaymentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PaypalpayService {
    @Inject
    EntityManager em;
    @Inject
    PaymentValidator validator;
    public PaypalpayService() {
    }
    public void createPaypalpay(Paypalpay paypalpay) {
        try {
            if(!validator.isAmountValid(paypalpay.getAmount())){
                throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
            }
            if(!validator.isDescriptionValid(paypalpay.getDescription())){
                throw new IllegalArgumentException("La descripcion del pago no puede estar vacia");
            }
            paypalpay.setStatus(PaymentStatus.PENDING);
            em.getTransaction().begin();
            em.persist(paypalpay);
            em.getTransaction().commit();
        }catch(Exception e) {
            System.err.println("No se pudo guardar el pago Paypalpay: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public Paypalpay findByIdPaypalpay(int id) {
        try {
            return em.find(Paypalpay.class, id);
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el pago con Paypalpay: "+e.getMessage());
            return null;
        }
    }
    public List<Paypalpay> findAllPaypalpay() {
        try {
            return em.createQuery("SELECT p FROM Paypalpay p", Paypalpay.class).getResultList();
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el pago con Paypalpay: "+e.getMessage());
            return new ArrayList<>();
        }
    }
    public void updatePaypalpay(Paypalpay paypalpay) {
        try {
            if(!validator.isAmountValid(paypalpay.getAmount())){
                throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
            }
            if(!validator.isDescriptionValid(paypalpay.getDescription())){
                throw new IllegalArgumentException("La descripcion del pago no puede estar vacia");
            }
            em.getTransaction().begin();
            this.em.merge(paypalpay);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar el pago con Paypalpay: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void deletePaypalpay(int id) {
        try {

            em.getTransaction().begin();
            Paypalpay paypalpay = em.find(Paypalpay.class, id);
            if(paypalpay != null) {
                em.remove(paypalpay);
            }else {
                System.err.println("No se pudo eliminar el pago con Paypalpay: "+id);
            }
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo eliminar el pago con Paypalpay: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
