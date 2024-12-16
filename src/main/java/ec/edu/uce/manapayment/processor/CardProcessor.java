package ec.edu.uce.manapayment.processor;

import ec.edu.uce.manapayment.jpa.typepays.PaymentMethod;
import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import jakarta.enterprise.context.Dependent;

@Dependent
public class CardProcessor implements PaymentProcessor {
    @Override
    public void setTypepays(Typepays typepays) {
        if (typepays == null) {
            throw new IllegalArgumentException("Se requiere información del pago para procesar.");
        }
        System.out.println("Procesando pago con tarjeta...");
    }

    @Override
    public boolean isCompatibleWith(Typepays typepays) {
        return typepays != null && typepays.getPaymentMethod() == PaymentMethod.CARD;
    }
}
