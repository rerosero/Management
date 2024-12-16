//package ec.edu.uce.manapayment.jpa.typepays;
//
//public enum PaymentStatus {
//    PENDING,    // El pago no se ha realizado aún
//    COMPLETED,  // El pago fue exitoso
//    CANCELED    // El pago fue cancelado
//}
package ec.edu.uce.manapayment.jpa.typepays;

public enum PaymentStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}