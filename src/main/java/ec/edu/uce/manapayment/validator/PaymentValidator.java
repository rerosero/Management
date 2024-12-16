package ec.edu.uce.manapayment.validator;

import ec.edu.uce.manapayment.jpa.card.PayCard;
import ec.edu.uce.manapayment.jpa.paypal.Paypalpay;
import ec.edu.uce.manapayment.jpa.transfer.Paytransfer;
import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import jakarta.enterprise.context.RequestScoped;

// CDI Bean: Se crea una instancia para cada solicitud HTTP
@RequestScoped
public class PaymentValidator {

    // Verifica que la tarjeta tenga los 16 dígitos
    public boolean isCardNumberValid(String cardNumber) {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    // Verifica que el monto sea mayor a 0
    public boolean isAmountValid(double amount) {
        return amount > 0;
    }

    // Verifica si el pago ya fue realizado (PayCard)
    public boolean isPaymentAlreadyMade(PayCard payCard) {
        return payCard.getStatus() == PaymentStatus.COMPLETED;
    }

    public boolean isTypePayValid(Typepays typepays) {
        return typepays != null;
    }
    // Verifica si el pago ya fue realizado (Paypalpay)
    public boolean isPaymentAlreadyMadePay(Paypalpay paypalpay) {
        return paypalpay.getStatus() == PaymentStatus.COMPLETED;
    }

    // Verifica si el pago ya fue realizado (Paytransfer)
    public boolean isPaymentAlreadyMadeTrans(Paytransfer paytransfer) {
        return paytransfer.getStatus() == PaymentStatus.COMPLETED;
    }

    // Verifica que la descripción del pago no sea nula o vacía
    public boolean isDescriptionValid(String description) {
        return description != null && !description.trim().isEmpty();
    }
    public boolean isAccountNumberValid(String accountNumber) {
        return accountNumber != null && accountNumber.matches("\\d{10}");
    }
}