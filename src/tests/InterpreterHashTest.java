import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.security.MessageDigest;


public class InterpreterHashTest {

    private Collection<byte[]> runAndGetStack(String script) {
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interp = new Interpreter(stack, false);
        interp.execute(new Parser().parse(script));
        return stack;
    }

    @Test
    void opSha256_correctHash() throws Exception {
        Collection<byte[]> stack = runAndGetStack("DATA:5 OP_SHA256");
        byte[] expected = MessageDigest.getInstance("SHA-256").digest(new byte[]{5});
        assertArrayEquals(expected, stack.peek());
    }

    @Test
    void opHash256_doubleSha() throws Exception {
        Collection<byte[]> stack = runAndGetStack("DATA:5 OP_HASH256");
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] expected = sha.digest(sha.digest(new byte[]{5}));
        assertArrayEquals(expected, stack.peek());
    }

    @Test
    void opHash160_length() {
        Collection<byte[]> stack = runAndGetStack("DATA:5 OP_HASH160");
        assertEquals(20, stack.peek().length);
    }
}