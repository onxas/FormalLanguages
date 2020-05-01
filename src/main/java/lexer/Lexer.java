package lexer;

import fsm.FinalStateMachineGroup;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Лексер
 */
@AllArgsConstructor
@Data
public class Lexer {

    private FinalStateMachineGroup fsmGroup;

    /**
     * Возвращает список токенов из строки
     *
     * @param inputString
     * @return
     */
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
