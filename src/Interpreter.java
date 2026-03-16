import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interpreter {

    private boolean trace;
    private Collection<byte[]> stack;
    private Collection<Boolean> executionStack = new PilaArrayList<>();

    public Interpreter(Collection<byte[]> stack, boolean trace) {
        this.stack = stack;
        this.trace = trace;
    }

    private void printStack() {
        Collection<byte[]> temp = new PilaArrayList<>();
        System.out.print("STACK: [");
        while (!stack.isEmpty()) {
            byte[] val = stack.pop();
            temp.push(val);
            System.out.print(val[0] + " ");
        }
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
        System.out.println("]");
    }

    public boolean execute(List<Token> tokens) {
        for (Token token : tokens) {
            if (trace) {
                System.out.println("TOKEN: " + token);
            }
            boolean executing = true;
            Collection<Boolean> temp = new PilaArrayList<>();
            while (!executionStack.isEmpty()) {
                Boolean b = executionStack.pop();
                temp.push(b);
                if (!b) executing = false;
            }
            while (!temp.isEmpty()) {
                executionStack.push(temp.pop());
            }
            if (token.getType() == TokenType.DATA) {
                if (executing) {
                    stack.push(token.getData());
                    if (trace){
                        printStack();
                    }
                }
            }
            else if (token.getType() == TokenType.OPCODE) {
                OpCode op = token.getOpCode();
                if (!executing &&
                    op != OpCode.OP_IF &&
                    op != OpCode.OP_NOTIF &&
                    op != OpCode.OP_ELSE &&
                    op != OpCode.OP_ENDIF) {
                    continue;
                }
                boolean ok = executeOpCode(op);
                if (trace) {
                    printStack();
                }
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
            case OP_FALSE -> {
                stack.push(new byte[]{0});
                return true;
            }
            case OP_0 -> {stack.push(new byte[]{0});  return true;}
            case OP_1 -> {stack.push(new byte[]{1});  return true;}
            case OP_2 -> {stack.push(new byte[]{2});  return true;}
            case OP_3 -> {stack.push(new byte[]{3});  return true;}
            case OP_4 -> {stack.push(new byte[]{4});  return true;}
            case OP_5 -> {stack.push(new byte[]{5});  return true;}
            case OP_6 -> {stack.push(new byte[]{6});  return true;}
            case OP_7 -> {stack.push(new byte[]{7});  return true;}
            case OP_8 -> {stack.push(new byte[]{8});  return true;}
            case OP_9 -> {stack.push(new byte[]{9});  return true;}
            case OP_10 -> {stack.push(new byte[]{10}); return true;}
            case OP_11 -> {stack.push(new byte[]{11}); return true;}
            case OP_12 -> {stack.push(new byte[]{12}); return true;}
            case OP_13 -> {stack.push(new byte[]{13}); return true;}
            case OP_14 -> {stack.push(new byte[]{14}); return true;}
            case OP_15 -> {stack.push(new byte[]{15}); return true;}
            case OP_16 -> {stack.push(new byte[]{16}); return true;}

            case OP_DROP -> {
                byte[] dropped = stack.pop();
                return dropped != null;
            }

            case OP_DUP -> {
                byte[] top = stack.peek();
                if (top == null) return false;
                stack.push(top.clone());
                return true;
            }

            case OP_SWAP -> {
                byte[] a = stack.pop();
                byte[] b = stack.pop();
                if (a == null || b == null) return false;
                stack.push(a);
                stack.push(b);
                return true;
            }

            case OP_OVER -> {
                byte[] first = stack.pop();
                byte[] second = stack.peek();
                if (first == null || second == null) return false;
                stack.push(first);
                stack.push(second.clone());
                return true;
            }

            case OP_EQUAL -> {
                byte[] a = stack.pop();
                byte[] b = stack.pop();
                if (a == null || b == null) return false;
                stack.push(Arrays.equals(a,b) ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_EQUALVERIFY -> {
                byte[] x = stack.pop();
                byte[] y = stack.pop();
                if (x == null || y == null) return false;
                if (!Arrays.equals(x,y)) {     //Por temas de interaccion al pushear resultado de EQUAL
                    return false;
                }
                return true;
            }

            case OP_NOT -> {
                byte[] val = stack.pop();
                if (val == null) return false;
                stack.push(val[0] == 0 ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_BOOLAND -> {
                byte[] v1 = stack.pop();
                byte[] v2 = stack.pop();
                if (v1 == null || v2 == null) return false;
                stack.push((v1[0] != 0 && v2[0] != 0) ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_BOOLOR -> {
                byte[] v3 = stack.pop();
                byte[] v4 = stack.pop();
                if (v3 == null || v4 == null) return false;
                stack.push((v3[0] != 0 || v4[0] != 0) ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_ADD -> {
                byte[] n1 = stack.pop();
                byte[] n2 = stack.pop();
                if (n1 == null || n2 == null) return false;
                stack.push(new byte[]{(byte)(n1[0] + n2[0])});
                return true;
            }

            case OP_SUB -> {
                byte[] n3 = stack.pop();
                byte[] n4 = stack.pop();
                if (n3 == null || n4 == null) return false;
                stack.push(new byte[]{(byte)(n4[0] - n3[0])});
                return true;
            }

            case OP_NUMEQUALVERIFY -> {
                byte[] a = stack.pop();
                byte[] b = stack.pop();
                if (a == null || b == null) return false;
                if (a[0] != b[0]) {
                    return false;
                }
                return true;
            }

            case OP_LESSTHAN -> {
                byte[] lt1 = stack.pop();
                byte[] lt2 = stack.pop();
                if (lt1 == null || lt2 == null) return false;
                stack.push((lt2[0] < lt1[0]) ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_GREATERTHAN -> {
                byte[] gt1 = stack.pop();
                byte[] gt2 = stack.pop();
                if (gt1 == null || gt2 == null) return false;
                stack.push((gt2[0] > gt1[0]) ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_LESSTHANOREQUAL -> {
                byte[] le1 = stack.pop();
                byte[] le2 = stack.pop();
                if (le1 == null || le2 == null) return false;
                stack.push((le2[0] <= le1[0]) ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_GREATERTHANOREQUAL -> {
                byte[] ge1 = stack.pop();
                byte[] ge2 = stack.pop();
                if (ge1 == null || ge2 == null) return false;
                stack.push((ge2[0] >= ge1[0]) ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_IF -> 
            {
                byte[] cond = stack.pop();
                if (cond == null) return false;
                boolean result = cond.length != 0 && cond[0] != 0;
                executionStack.push(result);
                return true;
            }

            case OP_NOTIF -> 
            {
                byte[] cond = stack.pop();
                if (cond == null) return false;
                boolean result = cond.length == 0 || cond[0] == 0;
                executionStack.push(result);
                return true;
            }

            case OP_ELSE ->
            {
                if (executionStack.isEmpty()) return false;
                boolean current = executionStack.pop();
                executionStack.push(!current);
                return true;
            }

            case OP_ENDIF ->
            {
                if (executionStack.isEmpty()) return false;
                executionStack.pop();
                return true;
            }

            case OP_VERIFY -> {
                byte[] check = stack.pop();
                if (check == null || check[0] == 0) {
                    return false;
                }
                return true;
            }

            case OP_RETURN -> { return false; }

            case OP_SHA256 -> {
                byte[] data = stack.pop();
                if (data == null) return false;
                try {
                    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
                    byte[] hash = sha256.digest(data);
                    stack.push(hash);
                    return true;
                } catch (NoSuchAlgorithmException e) {
                    return false;
                }
            }

            case OP_HASH256 -> {
                byte[] data = stack.pop();
                if (data == null) return false;
                try {
                    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
                    byte[] first = sha256.digest(data);
                    byte[] second = sha256.digest(first);
                    stack.push(second);
                    return true;
                } catch (NoSuchAlgorithmException e) {
                    return false;
                }
            }

            case OP_HASH160 -> { //No se puede realizar con librerias de Java. 
                byte[] data = stack.pop();
                if (data == null) return false;
                try {
                    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
                    byte[] shaHash = sha256.digest(data);
                    byte[] hash160 = Arrays.copyOfRange(shaHash, 0, 20);
                    stack.push(hash160);
                    return true;
                } catch (NoSuchAlgorithmException e) {
                    return false;
                }
            }

            case OP_CHECKSIG -> {
                byte[] signature = stack.pop();
                byte[] pubkey = stack.pop();
                if (signature == null || pubkey == null) return false;
                boolean valid = signature[0] == pubkey[0]; // simulación
                stack.push(valid ? new byte[]{1} : new byte[]{0});
                return true;
            }

            case OP_CHECKSIGVERIFY -> {
                byte[] sig = stack.pop();
                byte[] pub = stack.pop();
                if (sig == null || pub == null) return false;
                boolean valid = sig[0] == pub[0]; // simulación
                if (!valid) return false;
                return true;
            }

            case OP_CHECKMULTISIG -> 
            {
                byte[] nBytes = stack.pop();
                if (nBytes == null) return false;
                int n = Byte.toUnsignedInt(nBytes[0]);
                List<byte[]> pubKeys = new ArrayList<>();
                for (int i = 0; i < n; i++) {
                    byte[] pub = stack.pop();
                    if (pub == null) return false;
                    pubKeys.add(pub);
                }
                byte[] mBytes = stack.pop();
                if (mBytes == null) return false;
                int m = Byte.toUnsignedInt(nBytes[0]);
                List<byte[]> sigs = new ArrayList<>();
                for (int i = 0; i < m; i++) {
                    byte[] sig = stack.pop();
                    if (sig == null) return false;
                    sigs.add(sig);
                }
                int valid = 0;
                for (byte[] sig : sigs) {
                    for (byte[] pub : pubKeys) {
                        if (sig[0] == pub[0]) { // simulación
                            valid++;
                            break;
                        }
                    }
                }
                stack.push(valid >= m ? new byte[]{1} : new byte[]{0});
                return true;
            }

            default -> {
                return false;
            }
        }
    }
}

