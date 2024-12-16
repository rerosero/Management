package ec.edu.uce.manapayment.jpa.card;

import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import ec.edu.uce.manapayment.jpa.client.Client;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
public class PayCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPayCard;

    private double amount;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // me indica si el pago se realizo o no

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


    @ManyToOne
    @JoinColumn(name = "type_pays_id", nullable = false)
    private Typepays typePays;

    //Construcctor por defecto
    public PayCard() {}

    //Getters and setters
    public int getIdPayCard() {
        return idPayCard;
    }

    public void setIdPayCard(int idPayCard) {
        this.idPayCard = idPayCard;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Typepays getTypePays() {
        return typePays;
    }

    public void setTypePays(Typepays typePays) {
        this.typePays = typePays;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}