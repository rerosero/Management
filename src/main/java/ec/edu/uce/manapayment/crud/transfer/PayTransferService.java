package ec.edu.uce.manapayment.crud.transfer;

import ec.edu.uce.manapayment.jpa.transfer.Paytransfer;
import ec.edu.uce.manapayment.jpa.transfer.Transfer;
import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import ec.edu.uce.manapayment.validator.PaymentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;


@ApplicationScoped
public class PayTransferService {
    @Inject
    EntityManager em;
    @Inject
    PaymentValidator paymentValidator;
    public PayTransferService() {
    }
    public void createPayTransfer(Paytransfer paytransfer) {
        try {
            if (!paymentValidator.isAmountValid(paytransfer.getAmount())) {
                throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
            }
            if (!paymentValidator.isDescriptionValid(paytransfer.getDescription())) {
                throw new IllegalArgumentException("La descripcion del pago no puede estar vacia");
            }

            // Comienza la transacción
            em.getTransaction().begin();

            // Configura el estado y persiste el pago
            paytransfer.setStatus(PaymentStatus.PENDING);

            // Si la transaccion se realizo
            em.persist(paytransfer);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Si ocurre un error, revierte la transacción
            }
            System.err.println("Error al guardar el pago: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public Transfer findByIdPayTransfer(int id) {
        try {
            return em.find(Transfer.class, id);
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el pago: "+e.getMessage());
            return null;
        }
    }

    public void updatePayTransfer(Paytransfer paytransfer) {
        try {
            if(!paymentValidator.isAmountValid(paytransfer.getAmount())){
                throw new IllegalArgumentException("El monto del pago debe ser mayor a 0.");
            }
            if(!paymentValidator.isDescriptionValid(paytransfer.getDescription())){
                throw new IllegalArgumentException("La descripcion del pago no puede estar vacia");
            }
            em.getTransaction().begin();
            this.em.merge(paytransfer);
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar el pago: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void deletePayTransfer(int id) {
        try {
            em.getTransaction().begin();
            Paytransfer paytransfer = em.find(Paytransfer.class, id);
            if(paytransfer != null) {
                em.remove(paytransfer);
            }else {
                System.err.println("No se pudo eliminar el pago: "+id);
            }
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo eliminar el pago: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
