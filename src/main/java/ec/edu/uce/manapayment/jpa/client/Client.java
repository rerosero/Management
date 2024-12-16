package ec.edu.uce.manapayment.jpa.client;

import ec.edu.uce.manapayment.jpa.card.PayCard;
import ec.edu.uce.manapayment.jpa.paypal.PayPal;
import ec.edu.uce.manapayment.jpa.transfer.Transfer;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private int idcliente;

    @Column(nullable = false, unique = true)
    private String names;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // En un sistema real, debe estar cifrada

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PayCard> payCards = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PayPal> paypalPays = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transfer> transferPays = new ArrayList<>();

    // Constructor por defecto
    public Client() {}

    // Constructor por parámetros
    public Client(String names, String email, String password) {
        this.names = names;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Transfer> getTransferPays() {
        return transferPays;
    }

    public void setTransferPays(List<Transfer> transferPays) {
        this.transferPays = transferPays;
    }

    public void addTransfer(Transfer transfer) {
        this.transferPays.add(transfer);
        transfer.setClient(this);
    }

    public void removeTransfer(Transfer transfer) {
        this.transferPays.remove(transfer);
        transfer.setClient(null);
    }
}