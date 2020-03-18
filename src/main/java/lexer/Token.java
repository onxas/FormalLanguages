package lexer;

public class Token {

    private String type;
    private String id;

    public Token(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
