import java.util.ArrayList;
import java.util.List;

public class Parser {

    // Convierte un texto completo en lista de Tokens
    public List<Token> parse(String scriptText) {
        List<Token> tokens = new ArrayList<>();

        // Caso borde: texto vacío
        if (scriptText == null || scriptText.trim().isEmpty()) {
            return tokens;
        }

        // 1) separar por espacios
        String[] parts = scriptText.trim().split("\\s+");

        // 2) convertir cada palabra en Token
        for (String part : parts) {
            tokens.add(parseWord(part));
        }

        return tokens;
    }

    // Convierte una palabra individual a Token
    private Token parseWord(String word) {

        // Si empieza con DATA: es dato
        if (word.startsWith("DATA:")) {
            String value = word.substring("DATA:".length());
            return Token.data(decimalToBytes(value));
        }

        // Si no es DATA:, asumimos que debe ser un opcode
        try {
            OpCode op = OpCode.valueOf(word);
            return Token.opcode(op);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown token: " + word);
        }
    }

    private byte[] decimalToBytes(String decimal) {
        int value = Integer.parseInt(decimal);
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Decimal fuera de rango (0-255)");
        }
        return new byte[]{ (byte) value };
    }
}
