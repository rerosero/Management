package ec.edu.uce.manapayment.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EncryptionService {

    // Genera el hash de la contraseña utilizando bcrypt
    public String hashPassword(String plainPassword) {
        return BCrypt.withDefaults().hashToString(12, plainPassword.toCharArray());
    }

    // Valida la contraseña proporcionada contra el hash almacenado
    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), hashedPassword).verified;
    }
    //ocultar digitos en la tarjeta
    public String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            throw new IllegalArgumentException("El número de tarjeta no es válido para enmascarar");
        }

        // Elimina espacios o guiones, si los hay
        cardNumber = cardNumber.replaceAll("\\s+", "").replaceAll("-", "");

        // Extrae los primeros 8 dígitos
        String firstPart = cardNumber.substring(0, 8);
        // Extrae los últimos 4 dígitos
        String lastPart = cardNumber.substring(cardNumber.length() - 4);
        // Genera asteriscos para los dígitos intermedios
        String maskedPortion = "*".repeat(cardNumber.length() - 12);

        // Combina primero, intermedio y último segmento
        return firstPart + maskedPortion + lastPart;
    }
    //ocultar digitos en la tarjeta
    public String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            throw new IllegalArgumentException("El número de tarjeta no es válido para enmascarar");
        }
        // Elimina espacios o guiones, si los hay
        accountNumber = accountNumber.replaceAll("\\s+", "").replaceAll("-", "");

        // Genera asteriscos para los dígitos intermedios
        String maskedPortion = "*".repeat(accountNumber.length() - 4);

        // Combina los asteriscos con los últimos 4 dígitos del número de cuenta
        String visiblePortion = accountNumber.substring(accountNumber.length() - 4);

        // Retorna el resultado final: asteriscos seguidos de los últimos 4 dígitos
        return maskedPortion + visiblePortion;
    }
}
