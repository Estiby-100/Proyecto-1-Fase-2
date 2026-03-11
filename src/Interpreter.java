import java.util.Arrays;
import java.util.List;

public class Interpreter {

    private Collection<byte[]> stack;

    public Interpreter(Collection<byte[]> stack) {
        this.stack = stack;
    }

    public boolean execute(List<Token> tokens) {

        for (Token token : tokens) {

            if (token.getType() == TokenType.DATA) {
                stack.push(token.getData());
            }

            else if (token.getType() == TokenType.OPCODE) {
                boolean ok = executeOpCode(token.getOpCode());
                if (!ok) {
                    return false; // falla inmediata
                }
            }
        }

        // criterio de éxito
        byte[] result = stack.peek();
        return result != null && result.length != 0 && !(result.length == 1 && result[0] == 0);
    }

    private boolean executeOpCode(OpCode op) {

        switch (op) {

            case OP_0:  stack.push(new byte[]{0});  return true;
            case OP_1:  stack.push(new byte[]{1});  return true;
            case OP_2:  stack.push(new byte[]{2});  return true;
            case OP_3:  stack.push(new byte[]{3});  return true;
            case OP_4:  stack.push(new byte[]{4});  return true;
            case OP_5:  stack.push(new byte[]{5});  return true;
            case OP_6:  stack.push(new byte[]{6});  return true;
            case OP_7:  stack.push(new byte[]{7});  return true;
            case OP_8:  stack.push(new byte[]{8});  return true;
            case OP_9:  stack.push(new byte[]{9});  return true;
            case OP_10: stack.push(new byte[]{10}); return true;
            case OP_11: stack.push(new byte[]{11}); return true;
            case OP_12: stack.push(new byte[]{12}); return true;
            case OP_13: stack.push(new byte[]{13}); return true;
            case OP_14: stack.push(new byte[]{14}); return true;
            case OP_15: stack.push(new byte[]{15}); return true;
            case OP_16: stack.push(new byte[]{16}); return true;

            case OP_DROP:
                byte[] dropped = stack.pop();
                return dropped != null;

            case OP_DUP:
                byte[] top = stack.peek();
                if (top == null) return false;
                stack.push(top.clone());
                return true;

            case OP_EQUAL:
                byte[] a = stack.pop();
                byte[] b = stack.pop();
                if (a == null || b == null) return false;
                stack.push(Arrays.equals(a,b) ? new byte[]{1} : new byte[]{0});
                return true;

            case OP_EQUALVERIFY:
                byte[] x = stack.pop();
                byte[] y = stack.pop();
                if (x == null || y == null) return false;
                if (!Arrays.equals(x,y)) {     //Por temas de interaccion al pushear resultado de EQUAL
                    return false;
                }
                return true;
            
                case OP_HASH160:
                // Not implemented yet
                throw new UnsupportedOperationException("OP_HASH160 not implemented yet");
            
                case OP_CHECKSIG:
                // Not implemented yet
                throw new UnsupportedOperationException("OP_CHECKSIG not implemented yet");
            
                default:
                return false;
        }
    }
}

