package ec.edu.uce.manapayment.processor;

import ec.edu.uce.manapayment.jpa.typepays.PaymentMethod;
import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import jakarta.enterprise.context.Dependent;

@Dependent
public class PayPalProcessor implements PaymentProcessor {
    @Override
    public void setTypepays(Typepays typepays) {
        if (typepays==null){
            throw new IllegalArgumentException("Se requiere informacion del pago para procesarlo");
        }
        System.out.println("Procesando pago con PayPal...");

    }

    @Override
    public boolean isCompatibleWith(Typepays typepays) {
        return typepays!=null && typepays.getPaymentMethod()== PaymentMethod.PAYPAL;
    }
}
