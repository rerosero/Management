package ec.edu.uce.manapayment.jpa.typepays;

public enum PaymentMethod {
    CARD("Card"),
    PAYPAL("Paypal"),
    TRANSFER("Transfer");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Buscar por descripción
    public static PaymentMethod fromDescription(String description) {
        for (PaymentMethod method : values()) {
            if (method.getDescription().equalsIgnoreCase(description)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Método de pago no válido para la descripción: " + description);
    }

    @Override
    public String toString() {
        return this.description;
    }
}