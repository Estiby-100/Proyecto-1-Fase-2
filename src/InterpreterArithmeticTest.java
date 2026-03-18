import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

public class InterpreterArithmeticTest extends InterpreterTestBase {

    @Test
    void opAdd() {
        assertTrue(run("DATA:3 DATA:4 OP_ADD"));
    }

    @Test
    void opAdd_resultCheck() {
        Collection<byte[]> stack = runAndGetStack("DATA:3 DATA:4 OP_ADD");
        assertArrayEquals(new byte[]{7}, stack.peek());
    }

    @Test
    void opSub() {
        assertTrue(run("DATA:7 DATA:3 OP_SUB"));
    }

    @Test
    void opLessThan() {
        assertTrue(run("DATA:3 DATA:5 OP_LESSTHAN"));
        assertFalse(run("DATA:5 DATA:3 OP_LESSTHAN"));
    }

    @Test
    void opGreaterThan() {
        assertTrue(run("DATA:5 DATA:3 OP_GREATERTHAN"));
        assertFalse(run("DATA:3 DATA:5 OP_GREATERTHAN"));
    }
}