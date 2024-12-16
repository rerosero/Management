package ec.edu.uce.manapayment.jpa.paypal;

import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import ec.edu.uce.manapayment.jpa.client.Client;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class PayPal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payId;

    @Column(nullable = false)
    private String emailPaypal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date datePays;

    // Relación con el cliente
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Client client;

    // Relación con el tipo de pago
    @OneToOne
    @JoinColumn(name = "typepays_id") // Define la columna que será la llave foránea
    private Typepays typePays;

    // Relación uno a muchos con Paypalpay
    @OneToMany(mappedBy = "payPal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paypalpay> paypalPays = new ArrayList<>();

    // Getters y Setters

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public String getEmailPaypal() {
        return emailPaypal;
    }

    public void setEmailPaypal(String emailPaypal) {
        this.emailPaypal = emailPaypal;
    }

    public Date getDatePays() {
        return datePays;
    }

    public void setDatePays(Date datePays) {
        this.datePays = datePays;
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

    public List<Paypalpay> getPaypalPays() {
        return paypalPays;
    }

    public void setPaypalPays(List<Paypalpay> paypalPays) {
        this.paypalPays = paypalPays;
    }
}