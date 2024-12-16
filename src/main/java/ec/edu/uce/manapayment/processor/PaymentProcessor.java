package ec.edu.uce.manapayment.processor;

import ec.edu.uce.manapayment.jpa.typepays.Typepays;

public interface PaymentProcessor {
    // para procesar el pago de acuerdo con un metodo especifico
    void setTypepays (Typepays typepays);
    // validacion si el procesador es compatible con el metodo dado
    boolean isCompatibleWith(Typepays typepays);
}
