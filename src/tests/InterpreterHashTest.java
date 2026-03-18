import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.security.MessageDigest;
import java.util.Collection;
import java.util.Arrays;

public class InterpreterHashTest extends InterpreterTestBase {

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
        byte[] first = sha.digest(new byte[]{5});
        byte[] second = sha.digest(first);

        assertArrayEquals(second, stack.peek());
    }

    @Test
    void opHash160_length() {
        Collection<byte[]> stack = runAndGetStack("DATA:5 OP_HASH160");
        assertEquals(20, stack.peek().length);
    }
}