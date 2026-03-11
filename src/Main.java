import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Collection<byte[]> stack = new PilaArrayList<>();
        Interpreter interpreter = new Interpreter(stack);
        FileReader dataObtainer = new FileReader();
        String script = dataObtainer.ReadFile("target/Script_commands.txt");
        List<Token> tokens = parser.parse(script);
        boolean result = interpreter.execute(tokens);
        System.out.println("Resultado: " + result);
    }
}