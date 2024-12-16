package ec.edu.uce.manapayment.jpa.typepays;

import ec.edu.uce.manapayment.jpa.client.Client;
import ec.edu.uce.manapayment.jpa.paypal.PayPal;
import ec.edu.uce.manapayment.jpa.transfer.Transfer;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Typepays {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTypePays;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePays;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne(mappedBy = "typePays", cascade = CascadeType.ALL)
    private Transfer transfer;

    @OneToOne
    private Client client;

    public Typepays() {}

    public Typepays(Date datePays, PaymentMethod paymentMethod) {
        this.datePays = datePays;
        this.paymentMethod = paymentMethod;
    }

    // Getters y Setters

    public int getIdTypePays() {
        return idTypePays;
    }

    public void setIdTypePays(int idTypePays) {
        this.idTypePays = idTypePays;
    }

    public Date getDatePays() {
        return datePays;
    }

    public void setDatePays(Date datePays) {
        this.datePays = datePays;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}