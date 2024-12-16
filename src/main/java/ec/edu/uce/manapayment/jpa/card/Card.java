package ec.edu.uce.manapayment.jpa.card;

import ec.edu.uce.manapayment.jpa.client.Client;
import jakarta.persistence.*;


@Entity
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCard;

    @Column(name="card_number",nullable = false, length = 16)
    private String cardNumber;

    @Column(name="card_type",nullable = false, length=10)
    private String cardType;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private Client client;

    // Constructor por defecto
    public Card() {}

    // Getters y Setters
    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCardPay) {
        this.idCard = idCardPay;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
