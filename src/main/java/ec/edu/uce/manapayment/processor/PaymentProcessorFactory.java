package ec.edu.uce.manapayment.processor;

import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class PaymentProcessorFactory {
    @Inject
    @Any // Se inyectan todas las implementaciones de PaymentProcessor disponibles
    private Instance<PaymentProcessor> paymentProcessors;

    //obtiene el procesado adecuado para cada metodo de pago
    public PaymentProcessor getPaymentProcessor(Typepays typepays) {
        for (PaymentProcessor paymentProcessor : paymentProcessors) {
            if (paymentProcessor.isCompatibleWith(typepays)) {
                return paymentProcessor;
            }
        }
       throw new IllegalArgumentException("Ningun procesador soporta el metodo de pago "
       + typepays.getPaymentMethod() + ".");
    }
}
