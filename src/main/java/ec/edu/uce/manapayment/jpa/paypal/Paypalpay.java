package ec.edu.uce.manapayment.jpa.paypal;

import ec.edu.uce.manapayment.jpa.client.Client;
import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import jakarta.persistence.*;

@Entity
public class Paypalpay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPaypaypal;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    // Relación con el cliente (N:1)
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Relación con PayPal (N:1)
    @ManyToOne
    @JoinColumn(name = "paypal_id", nullable = false)
    private PayPal payPal;



    // Constructor por defecto
    public Paypalpay() {}

    // Getters y Setters

    public int getIdPaypaypal() {
        return idPaypaypal;
    }

    public void setIdPaypaypal(int idPaypaypal) {
        this.idPaypaypal = idPaypaypal;
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


    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public PayPal getPayPal() {
        return payPal;
    }
    public void setPayPal(PayPal payPal) {
        this.payPal = payPal;
    }

}