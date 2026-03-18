import java.util.Collection;

public abstract class InterpreterTestBase {

    protected boolean run(String script) {
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interp = new Interpreter(stack, false);
        return interp.execute(new Parser().parse(script));
    }

    protected Collection<byte[]> runAndGetStack(String script) {
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interp = new Interpreter(stack, false);
        interp.execute(new Parser().parse(script));
        return stack;
    }
}
