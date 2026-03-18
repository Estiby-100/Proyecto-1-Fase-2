import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

public class InterpreterPushTest extends InterpreterTestBase {

    @Test
    void op5_pushes5OntoStack() {
        Collection<byte[]> stack = runAndGetStack("OP_5");
        assertArrayEquals(new byte[]{5}, stack.peek());
    }

    @Test
    void op5_executeReturnsTrueWhenTopIsNonZero() {
        assertTrue(run("OP_5"));
    }
}