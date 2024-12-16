package ec.edu.uce.manapayment.jpa.transfer;

import ec.edu.uce.manapayment.jpa.paypal.Paypalpay;
import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import ec.edu.uce.manapayment.jpa.client.Client;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransfer;

    @Column(nullable = false, length = 10, unique = true)
    private String account; // Número de cuenta de transferencia

    @Column(nullable = false, length = 100)
    private String holder; // Nombre del titular de la cuenta

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateTransfer; // Fecha de la transferencia

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "idUser", nullable = false)
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_pays_id", nullable = false)
    private Typepays typePays;

    // Relación uno a muchos con PayTransfer
    @OneToOne(mappedBy = "transfer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Paytransfer payTransfer;


    public Transfer() {}

    // Getters y Setters

    public int getIdTransfer() {
        return idTransfer;
    }

    public void setIdTransfer(int idTransfer) {
        this.idTransfer = idTransfer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public Date getDateTransfer() {
        return dateTransfer;
    }

    public void setDateTransfer(Date dateTransfer) {
        this.dateTransfer = dateTransfer;
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
        typePays.setTransfer(this); // Sincronización bidireccional
    }

    public Paytransfer getPayTransfer() {
        return payTransfer;
    }

    public void setPayTransfer(Paytransfer payTransfer) {
        this.payTransfer = payTransfer;
    }
}