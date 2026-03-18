import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class InterpreterArithmeticTest {

    private boolean run(String script) {
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interp = new Interpreter(stack, false);
        return interp.execute(new Parser().parse(script));
    }

    private Collection<byte[]> runAndGetStack(String script) {
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interp = new Interpreter(stack, false);
        interp.execute(new Parser().parse(script));
        return stack;
    }

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