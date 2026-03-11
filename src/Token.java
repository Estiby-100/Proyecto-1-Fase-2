import java.util.Arrays;
public class Token {

    private final TokenType type;
    private final OpCode opCode;   
    private final byte[] data;     

    private Token(TokenType type, OpCode opCode, byte[] data) {
        this.type = type;
        this.opCode = opCode;
        this.data = data;
    }

    public static Token opcode(OpCode op) {
        return new Token(TokenType.OPCODE, op, null);
    }

    public static Token data(byte[] data) {
        return new Token(TokenType.DATA, null, data);
    }

    public TokenType getType() {
        return type;
    }

    public OpCode getOpCode() {
        return opCode;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return (type == TokenType.DATA) ? "DATA(" + Arrays.toString(data) + ")" : opCode.name();
    }
}
