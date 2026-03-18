import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class InterpreterControlFlowTest {

    private boolean run(String script) {
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interp = new Interpreter(stack, false);
        return interp.execute(new Parser().parse(script));
    }

    @Test
    void opIf() {
        assertTrue(run("DATA:1 OP_IF OP_5 OP_ENDIF"));
        assertTrue(run("DATA:0 OP_IF OP_RETURN OP_ENDIF OP_1"));
    }

    @Test
    void opIfElse() {
        assertTrue(run("DATA:1 OP_IF OP_5 OP_ELSE OP_RETURN OP_ENDIF"));
        assertTrue(run("DATA:0 OP_IF OP_RETURN OP_ELSE OP_1 OP_ENDIF"));
    }

    @Test
    void opVerify() {
        assertTrue(run("DATA:1 OP_VERIFY OP_5"));
        assertFalse(run("DATA:0 OP_VERIFY OP_5"));
    }

    @Test
    void opReturn() {
        assertFalse(run("OP_RETURN"));
    }
}