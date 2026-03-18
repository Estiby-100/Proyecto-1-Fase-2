import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class InterpreterCryptoTest {

    private boolean run(String script) {
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interp = new Interpreter(stack, false);
        return interp.execute(new Parser().parse(script));
    }

    @Test
    void opCheckSig() {
        assertTrue(run("DATA:5 DATA:5 OP_CHECKSIG"));
        assertFalse(run("DATA:5 DATA:6 OP_CHECKSIG"));
    }

    @Test
    void opCheckSigVerify() {
        assertTrue(run("DATA:5 DATA:5 OP_CHECKSIGVERIFY OP_1"));
        assertFalse(run("DATA:5 DATA:6 OP_CHECKSIGVERIFY OP_1"));
    }

    @Test
    void opCheckMultiSig() {
        assertTrue(run("DATA:5 DATA:1 DATA:5 DATA:1 OP_CHECKMULTISIG"));
        assertFalse(run("DATA:6 DATA:1 DATA:5 DATA:1 OP_CHECKMULTISIG"));
    }

    @Test
    void opCheckMultiSigVerify() {
        assertTrue(run("DATA:5 DATA:1 DATA:5 DATA:1 OP_CHECKMULTISIGVERIFY OP_1"));
        assertFalse(run("DATA:6 DATA:1 DATA:5 DATA:1 OP_CHECKMULTISIGVERIFY OP_1"));
    }
}