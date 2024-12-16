package ec.edu.uce.manapayment.initapp;

import ec.edu.uce.manapayment.crud.card.CardService;
import ec.edu.uce.manapayment.crud.card.PayCardService;
import ec.edu.uce.manapayment.crud.client.ClientService;
import ec.edu.uce.manapayment.crud.paypal.PayPalService;
import ec.edu.uce.manapayment.crud.paypal.PaypalpayService;
import ec.edu.uce.manapayment.crud.transfer.PayTransferService;
import ec.edu.uce.manapayment.crud.transfer.TransferService;
import ec.edu.uce.manapayment.crud.typepay.TypePaysService;
import ec.edu.uce.manapayment.datarandom.RandomDataGenerator;
import ec.edu.uce.manapayment.jpa.card.Card;
import ec.edu.uce.manapayment.jpa.card.PayCard;
import ec.edu.uce.manapayment.jpa.client.Client;
import ec.edu.uce.manapayment.jpa.paypal.PayPal;
import ec.edu.uce.manapayment.jpa.paypal.Paypalpay;
import ec.edu.uce.manapayment.jpa.transfer.Paytransfer;
import ec.edu.uce.manapayment.jpa.transfer.Transfer;
import ec.edu.uce.manapayment.jpa.typepays.PaymentMethod;
import ec.edu.uce.manapayment.jpa.typepays.PaymentStatus;
import ec.edu.uce.manapayment.jpa.typepays.Typepays;
import ec.edu.uce.manapayment.processor.PaymentProcessor;
import ec.edu.uce.manapayment.processor.PaymentProcessorFactory;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.Date;

@Path("/management")
public class ManagementResource {
    @Inject
    private ClientService clientService; //inyeccion de client service
    @Inject
    private TypePaysService typePaysService;
    @Inject
    private CardService cardService;
    @Inject
    EntityManager em;
    @Inject
    private PaypalpayService paypalpayService;
    @Inject
    private PayPalService payPalService;
    @Inject
    private TransferService transferService;
    @Inject
    private PayTransferService payTransferService;
    @Inject
    private PaymentProcessorFactory paymentProcessorFactory;
    @Inject
    private RandomDataGenerator randomDataGenerator;
    @Inject
    private PayCardService payCardService;

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
    @GET
    @Produces("text/plain")
    @Path("/create-random-payments")
    public String createRandomPayments() {
        try {
            // Acumulador para construir el resultado final
            StringBuilder result = new StringBuilder();
            result.append("Se generaron los siguientes pagos aleatorios:\n");

            // Generar 20 datos aleatorios
            for (int i = 1; i <= 20; i++) {
                // ---------- 1. Crear Cliente con Datos Aleatorios ----------
                Client client = new Client();
                client.setNames(randomDataGenerator.generarNombre());  // Nombre aleatorio
                client.setEmail(client.getNames().toLowerCase().replaceAll(" ", "") + "@gmail.com"); // Email aleatorio
                client.setPassword(randomDataGenerator.generarPassword()); // Contraseña aleatoria
                clientService.createClient(client);

                // ---------- 2. Crear Tipo de Pago ----------
                Typepays typePays = new Typepays();
                typePays.setDatePays(new Date());
                typePays.setClient(client);

                // Elegir un metodo aleatorio
                PaymentMethod randomPaymentMethod = obtenerMetodoPagoAleatorio();
                typePays.setPaymentMethod(randomPaymentMethod);
                typePaysService.createTypePays(typePays);

                // ---------- 3. Procesar el Pago ----------
                PaymentProcessor processor = paymentProcessorFactory.getPaymentProcessor(typePays);
                processor.setTypepays(typePays); // Configura el tipo de pago
                processor.isCompatibleWith(typePays); // Verifica compatibilidad del procesador

                // ---------- 4. Crear Entidad del Pago Específica ----------
                String detallesPago;
                if (randomPaymentMethod == PaymentMethod.TRANSFER) {
                    // Crear transferencia
                    Transfer transfer = new Transfer();
                    transfer.setHolder(randomDataGenerator.generarNombre());
                    transfer.setDateTransfer(new Date());
                    transfer.setAccount(randomDataGenerator.generarNumeroCuenta());
                    transfer.setTypePays(typePays);
                    transfer.setClient(client);
                    transferService.createTransfer(transfer);

                    // Crear entidad de pago para Transferencia
                    Paytransfer paytransfer = new Paytransfer();
                    paytransfer.setAmount(randomDataGenerator.generarMonto());
                    paytransfer.setDescription(randomDataGenerator.generarDescripcion());
                    paytransfer.setStatus(PaymentStatus.PENDING);
                    paytransfer.setClient(client);
                    paytransfer.setTransfer(transfer);
                    payTransferService.createPayTransfer(paytransfer);

                    detallesPago = "Transferencia creada: " +
                            transfer.getHolder() + ", Número de Cuenta: " +
                            transfer.getAccount() + ", Monto: " +
                            paytransfer.getAmount() + ", Descripción: " +
                            paytransfer.getDescription();

                } else if (randomPaymentMethod == PaymentMethod.PAYPAL) {
                    // Crear cuenta PayPal
                    PayPal payPal = new PayPal();
                    payPal.setEmailPaypal(randomDataGenerator.generarNombre().toLowerCase().replace(" ", "") + "@paypal.com");
                    payPal.setClient(client);
                    payPal.setDatePays(new Date());
                    payPal.setTypePays(typePays);
                    payPalService.createPayPal(payPal);

                    // Crear pago para PayPal
                    Paypalpay paypalpay = new Paypalpay();
                    paypalpay.setAmount(randomDataGenerator.generarMonto());
                    paypalpay.setDescription(randomDataGenerator.generarDescripcion());
                    paypalpay.setClient(client);
                    paypalpay.setStatus(PaymentStatus.PENDING);
                    paypalpay.setPayPal(payPal);
                    paypalpayService.createPaypalpay(paypalpay);

                    detallesPago = "Pago PayPal creado: Email PayPal: " +
                            payPal.getEmailPaypal() + ", Monto: " +
                            paypalpay.getAmount() + ", Descripción: " +
                            paypalpay.getDescription();

                } else if (randomPaymentMethod == PaymentMethod.CARD) {
                    // Crear Tarjeta
                    Card card = new Card();
                    card.setCardNumber(randomDataGenerator.generarNumeroTarjeta());
                    card.setCardType("VISA");
                    card.setClient(client);

                    // Guardar la tarjeta primero
                    cardService.createCard(card);

                    // Crear y asociar el pago a la tarjeta
                    PayCard payCard = new PayCard();
                    payCard.setAmount(randomDataGenerator.generarMonto());
                    payCard.setDescription(randomDataGenerator.generarDescripcion());
                    payCard.setClient(client);
                    payCard.setStatus(PaymentStatus.PENDING);
                    payCard.setTypePays(typePays);


                    // Guardar el pago por separado
                    payCardService.createPayCard(payCard);

                    // Detalles del pago para confirmación
                    detallesPago = "Tarjeta creada: Número de Tarjeta: " +
                            card.getCardNumber() + ", Tipo: " +
                            card.getCardType() + ", Monto: " + payCard.getAmount() +
                            ", Descripción: " + payCard.getDescription();
                } else {
                    detallesPago = "No se pudo determinar el método de pago aleatorio.";
                }

                // Agregar los detalles del pago generado al resultado
                result.append("[").append(i).append("] ")
                        .append("Cliente: ").append(client.getNames())
                        .append(", Método de Pago: ").append(randomPaymentMethod)
                        .append(", Detalles: ").append(detallesPago)
                        .append("\n");
            }

            // ---------- 5. Retornar Respuesta con Todos los Pagos Generados ----------
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al generar pagos aleatorios: " + e.getMessage();
        }
    }

    private PaymentMethod obtenerMetodoPagoAleatorio() {
        int random = (int) (Math.random() * 3);
        switch (random) {
            case 0:
                return PaymentMethod.CARD;
            case 1:
                return PaymentMethod.PAYPAL;
            case 2:
                return PaymentMethod.TRANSFER;
            default:
        }
        return null;
    }

}
