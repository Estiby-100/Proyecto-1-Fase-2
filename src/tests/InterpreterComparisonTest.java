import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;

public class InterpreterComparisonTest extends InterpreterTestBase {

    @Test
    void opEqual_equalValues() {
        assertTrue(run("DATA:5 DATA:5 OP_EQUAL"));
    }

    @Test
    void opEqual_unequalValues() {
        assertFalse(run("DATA:5 DATA:6 OP_EQUAL"));
    }

    @Test
    void opEqualVerify_equal() {
        assertTrue(run("DATA:5 DATA:5 OP_EQUALVERIFY OP_1"));
    }

    @Test
    void opNot() {
        assertTrue(run("DATA:0 OP_NOT"));
        assertFalse(run("DATA:1 OP_NOT"));
    }

    @Test
    void opBoolAnd() {
        assertTrue(run("DATA:1 DATA:1 OP_BOOLAND"));
        assertFalse(run("DATA:1 DATA:0 OP_BOOLAND"));
    }

    @Test
    void opBoolOr() {
        assertTrue(run("DATA:1 DATA:0 OP_BOOLOR"));
        assertFalse(run("DATA:0 DATA:0 OP_BOOLOR"));
    }
}