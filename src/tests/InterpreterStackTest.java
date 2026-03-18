import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

public class InterpreterStackTest extends InterpreterTestBase {

    @Test
    void opDup_duplicatesTop() {
        Collection<byte[]> stack = runAndGetStack("DATA:5 OP_DUP");
        assertArrayEquals(new byte[]{5}, stack.peek());
        stack.pop();
        assertArrayEquals(new byte[]{5}, stack.peek());
    }

    @Test
    void opDup_emptyStack_returnsFalse() {
        assertFalse(run("OP_DUP"));
    }

    @Test
    void opDrop_removesTop() {
        Collection<byte[]> stack = runAndGetStack("DATA:5 DATA:3 OP_DROP");
        assertArrayEquals(new byte[]{5}, stack.peek());
    }

    @Test
    void opSwap_swapsTopTwoElements() {
        Collection<byte[]> stack = runAndGetStack("DATA:2 DATA:1 OP_SWAP");
        assertArrayEquals(new byte[]{2}, stack.peek());
        stack.pop();
        assertArrayEquals(new byte[]{1}, stack.peek());
    }

    @Test
    void opOver_copiesSecondElementToTop() {
        Collection<byte[]> stack = runAndGetStack("DATA:1 DATA:2 OP_OVER");
        assertArrayEquals(new byte[]{1}, stack.peek());
    }
}