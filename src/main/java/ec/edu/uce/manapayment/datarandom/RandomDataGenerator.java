package ec.edu.uce.manapayment.datarandom;

import jakarta.enterprise.context.ApplicationScoped; // Para usar como Bean CDI

@ApplicationScoped
public class RandomDataGenerator {

    private static final String[] NOMBRES = {
            "Carito Ubago", "Pedro Meneses", "Luis Coello", "Rosa Zavala", "Maria Fernandez", "Ricardo Lema",
            "Ana Borquez", "Fernando Cevallos", "Lucia", "Sofia", "Carlos Gómez", "Ana Pérez", "Luis Martínez",
            "María Rodríguez", "Pedro Sánchez", "Isabel Torres", "José García", "Carmen López", "Fernando Díaz",
            "Laura Fernández", "Javier Ruiz", "Sofía García", "Miguel Hernández", "Elena Ramírez", "Antonio Morales",
            "Patricia González", "David Martínez", "Raquel Jiménez", "Diego Álvarez", "Teresa Díaz"
    };

    private static final String[] DESCRIPCIONES = {
            "Pago de prueba", "Saldo inicial", "Compra en línea", "Transferencia mensual", "Pago recurrente",
            "Abono bancario", "Pago de servicios", "Compra de productos", "Pago Etafashion", "Pago de matricula",
            "Pago de RM", "Pago de Prestamos", "Pago de agua", "Pago de Luz", "Pago de internet", "Pago de telefono",
            "Pago de cable", "Pago Supermercado"
    };

    private static final int LONGITUD_CUENTA = 10; // Constante para longitud de cuenta
    private static final int LONGITUD_TARJETA = 16; // Constante para longitud de tarjeta
    private static final int LONGITUD_PASSWORD = 8;  //Constante para la contraseña
    // Para generar datos randomicos
    private static final java.util.Random RANDOM = new java.util.Random();

    //genera numeros de cuentas
    public String generarNumeroCuenta() {
        return generarNumber(LONGITUD_CUENTA);
    }

    //genera numeros de tarjeta
    public String generarNumeroTarjeta() {
        return generarNumber(LONGITUD_TARJETA);
    }
    //generar numeros aleatorios de tarjeta
    public String generarPassword() {
        return generarNumber(LONGITUD_PASSWORD);
    }
    //generador de numeros de cuenta
    private String generarNumber(int longitud) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(RANDOM.nextInt(10)); // Generar un dígito aleatorio
        }
        return sb.toString();
    }

    //genera nombres aleatorios
    public String generarNombre() {
        return NOMBRES[RANDOM.nextInt(NOMBRES.length)];
    }

    // genera descripciones aleatoria
    public String generarDescripcion() {
        return DESCRIPCIONES[RANDOM.nextInt(DESCRIPCIONES.length)];
    }

    // genera un monto aleatorio
    public double generarMonto() {
        double monto = RANDOM.nextDouble() * 1000;
        return Math.round(monto * 100.0) / 100.0;
    }




}