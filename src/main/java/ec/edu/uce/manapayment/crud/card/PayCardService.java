package ec.edu.uce.manapayment.crud.card;

import ec.edu.uce.manapayment.jpa.card.PayCard;
import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import ec.edu.uce.manapayment.validator.PaymentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PayCardService {
    @Inject
    PaymentValidator paymentValidator;
    @Inject
    EntityManager em;
    public PayCardService() {
    }
    public void createPayCard(PayCard payCard) {
        try {
            // Validar los datos del pago
            if (!paymentValidator.isAmountValid(payCard.getAmount())) {
                throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
            }

            if (!paymentValidator.isDescriptionValid(payCard.getDescription())) {
                throw new IllegalArgumentException("La descripción del pago no puede estar vacía.");
            }

            em.getTransaction().begin(); // Iniciar la transacción

            // Establece el estado inicial del pago
            payCard.setStatus(PaymentStatus.PENDING);

            // Persiste el pago
            em.persist(payCard);
            em.getTransaction().commit(); // Confirmar la transacción

            System.out.println("Pago creado exitosamente con ID: " + payCard.getIdPayCard());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No se pudo crear el pago: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public PayCard findByIdPayCard(int id) {
        try {
            return em.find(PayCard.class, id);
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el pago por tarjeta: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public List<PayCard> findAllPayCards() {
        try {
            return em.createQuery("SELECT pc FROM PayCard pc", PayCard.class).getResultList();
        } catch (Exception e) {
            System.err.println("No hay pagos por tarjeta o no existen: "+e.getMessage());
            e.printStackTrace(); // Registra la excepción
            return new ArrayList<>(); // Retorna lista vacía en caso de error
        }
    }

    public void updatePayCard(PayCard payCard) {
        try {
            // Verifica que el monto sea válido
            if (!paymentValidator.isAmountValid(payCard.getAmount())) {
                throw new IllegalArgumentException("El monto del pago debe ser mayor a 0");
            }
            // Verifica que el pago no esté completado
            if (paymentValidator.isPaymentAlreadyMade(payCard)) {
                throw new IllegalArgumentException("No puedes actualizar un pago ya realizado.");
            }

            em.getTransaction().begin();
            this.em.merge(payCard);
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar su pago por tarjeta: "+e.getMessage());
            e.printStackTrace();
        }
    }


    public void deletePayCard(int id) {
        PayCard payCard = em.find(PayCard.class, id);
        if (payCard != null) {
            // Verifica que el pago no esté marcado como realizado
            if (paymentValidator.isPaymentAlreadyMade(payCard)) {
                throw new IllegalArgumentException("No puedes eliminar pagos ya realizados.");
            }
            em.getTransaction().begin();
            em.remove(payCard);
            em.getTransaction().commit();
        } else {
            System.err.println("No existe el registro de pago con el ID " + id);
        }

    }


}
