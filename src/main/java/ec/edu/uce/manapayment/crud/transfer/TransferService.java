package ec.edu.uce.manapayment.crud.transfer;

import ec.edu.uce.manapayment.jpa.transfer.Transfer;
import ec.edu.uce.manapayment.security.EncryptionService;
import ec.edu.uce.manapayment.validator.PaymentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class TransferService {
    @Inject
    private EntityManager em;
    @Inject
    private PaymentValidator paymentValidator;
    @Inject
    private EncryptionService encryptionService;
    public TransferService() {}
    public void createTransfer(Transfer transfer) {
        try {
            if(!paymentValidator.isAccountNumberValid(transfer.getAccount())){
                throw new IllegalArgumentException("El numero de cuenta no puede estar vacio y debe tener 10 digitos");
            }

            String maskedaccount= encryptionService.maskAccountNumber(transfer.getAccount());
            transfer.setAccount(maskedaccount);
            em.getTransaction().begin();
            em.persist(transfer);
            em.getTransaction().commit();
        }catch(Exception e) {
            System.err.println("No se pudo guardar el transfer: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public Transfer findByIdTransfer(int id) {
        try {
            return em.find(Transfer.class, id);
        }catch(Exception e) {
            System.err.println("No se pudo encontrar el transfer: "+e.getMessage());
            return null;
        }
    }
    public void updateTransfer(Transfer transfer) {
        try {
            em.getTransaction().begin();
            this.em.merge(transfer);
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar el transfer: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteTransfer(int id) {
        try {
            em.getTransaction().begin();
            Transfer transfer = em.find(Transfer.class, id);
            if(transfer != null) {
                em.remove(transfer);
            }else {
                System.err.println("No se pudo eliminar el transfer: "+id);
            }
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo eliminar el transfer: "+e.getMessage());
            e.printStackTrace();
        }
    }
}
