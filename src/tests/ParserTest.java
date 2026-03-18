import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class ParserTest {
    @Test
    void parseData() {
        Parser parser = new Parser();
        List<Token> tokens = parser.parse("DATA:5");
        assertEquals(1, tokens.size());
        assertEquals(TokenType.DATA, tokens.get(0).getType());
    }
    @Test
    void opcodeInvalido() {
        Parser parser = new Parser();
        assertThrows(IllegalArgumentException.class,
                () -> parser.parse("OP_INVALID"));
    }
}