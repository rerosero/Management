package ec.edu.uce.manapayment.crud.card;

import ec.edu.uce.manapayment.jpa.card.Card;

import ec.edu.uce.manapayment.jpa.card.PayCard;
import ec.edu.uce.manapayment.security.EncryptionService;
import ec.edu.uce.manapayment.validator.PaymentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class CardService {
    @Inject
    private EntityManager em;
    @Inject
    PaymentValidator paymentValidator;
    @Inject
    EncryptionService encryptionService;
    public CardService() {}
    public void createCard(Card card) {
        try {
            // Validar el número de tarjeta
            if (!paymentValidator.isCardNumberValid(card.getCardNumber())) {
                throw new IllegalArgumentException("El número de tarjeta debe tener 16 dígitos.");
            }

            em.getTransaction().begin(); // Iniciar la transacción

            // Ocultar el número de tarjeta
            String maskedNumber = encryptionService.maskCardNumber(card.getCardNumber());
            card.setCardNumber(maskedNumber);

            // Persiste la tarjeta
            em.persist(card);
            em.getTransaction().commit(); // Confirmar la transacción

            System.out.println("Tarjeta creada exitosamente con ID: " + card.getIdCard());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No se pudo crear la tarjeta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Card findByIdCard(int id) {
        try {
            return em.find(Card.class, id);
        }catch(Exception e) {
            System.err.println("No se pudo encontrar la tarjeta: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    public List<Card> findAllCards(){
        try {
            return em.createQuery("SELECT c FROM Card c", Card.class).getResultList(); //lista de todas las tarjetas
        }catch(Exception e) {
            System.err.println("No se pudo encontrar la tarjeta: "+e.getMessage());
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }

    }

    public Card findByNumberCard(String numberCard) {
        try {
            return em.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :numberCard", Card.class)
                    .setParameter("numberCard", numberCard)
                    .getSingleResult();
        }catch(Exception e) {
            System.err.println("No se pudo encontrar la tarjeta: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Card findByNumberCardAndTypeCard(String numberCard, String typeCard) {
        try {
            return em.createQuery("SELECT c FROM Card c WHERE c.cardNumber = :numberCard AND c.cardType = :typeCard", Card.class)
                    .setParameter("numberCard", numberCard)
                    .setParameter("typeCard", typeCard)
                    .getSingleResult();
        }catch(Exception e) {
            System.err.println("No se pudo encontrar la tarjeta: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void updateCard(Card card) {
        try {
            em.getTransaction().begin();
            this.em.merge(card);
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo actualizar la tarjeta: "+e.getMessage());
            e.printStackTrace();
        }
    }
    public void deleteCard(int id) {
        try {
            em.getTransaction().begin();
            Card card = em.find(Card.class, id);
            if(card != null) {
                em.remove(card);
            }else {
                System.err.println("No se pudo eliminar la tarjeta: "+id);
            }
            em.getTransaction().commit();
        }catch(Exception e) {
            em.getTransaction().rollback();
            System.err.println("No se pudo eliminar el usuario: "+e.getMessage());
            e.printStackTrace();
        }

    }
}
