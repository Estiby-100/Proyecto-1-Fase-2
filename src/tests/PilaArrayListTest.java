import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PilaArrayListTest {
    @Test
    void pushYpop() {
        PilaArrayList<Integer> pila = new PilaArrayList<>();
        pila.push(5);
        assertEquals(5, pila.pop());
    }
    @Test
    void popEnPilaVacia() {
        PilaArrayList<Integer> pila = new PilaArrayList<>();
        assertNull(pila.pop());
    }
    @Test
    void peekNoRemueveElemento() {
        PilaArrayList<Integer> pila = new PilaArrayList<>();
        pila.push(3);
        assertEquals(3, pila.peek());
        assertFalse(pila.isEmpty());
    }
}