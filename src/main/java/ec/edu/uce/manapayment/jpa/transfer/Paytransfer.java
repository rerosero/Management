package ec.edu.uce.manapayment.jpa.transfer;

import ec.edu.uce.manapayment.jpa.client.Client;
import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Paytransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPaytransfer;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToOne
    @JoinColumn(name = "transfer_id", nullable = false, unique = true)
    private Transfer transfer;

    public Paytransfer() {}

    // Getters y Setters

    public int getIdPaytransfer() {
        return idPaytransfer;
    }

    public void setIdPaytransfer(int idPaytransfer) {
        this.idPaytransfer = idPaytransfer;
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

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }
}