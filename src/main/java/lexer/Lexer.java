package lexer;

import fsm.FinalStateMachineGroup;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private FinalStateMachineGroup fsmGroup;

    public Lexer(FinalStateMachineGroup fsmGroup) {
        this.fsmGroup = fsmGroup;
    }


    public List<Token> tokenize(String inputString) {
        List<Token> tokens = new ArrayList<>();
        int pos = 0;
        while (pos <= inputString.length()) {
            Pair<Pair<Boolean, Integer>, String> result = fsmGroup.maxString(inputString, pos);
            if (result.getKey().getKey()) {
                tokens.add(new Token(result.getValue(), inputString.substring(pos, pos + result.getKey().getValue())));
                pos += result.getKey().getValue();
            } else return tokens;
        }
        return tokens;
    }
}
