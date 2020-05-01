import fsm.FinalStateMachine;
import fsm.FinalStateMachineGroup;
import javafx.util.Pair;
import lexer.Lexer;
import lexer.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

public class Tests {

    @Test
    public void TestDeserializationFSM() {
        File jsonFSM = new File("D:\\FormalLanguages\\src\\main\\resources\\testFSM.json");
        FinalStateMachine finalStateMachine = FinalStateMachine.createFromJson(jsonFSM);
    }

    @Test
    public void testMaxString() {
        File file = new File("D:\\FormalLanguages\\\\src\\\\main\\\\resources\\\\whitespace.json");
        FinalStateMachine fsm = FinalStateMachine.createFromJson(file);

        String input = " ";
        Assertions.assertEquals(new Pair<>(true, 1), fsm.maxString(input, 0));

        input = "X X";
        Assertions.assertEquals(new Pair<>(true, 1), fsm.maxString(input, 1));
        Assertions.assertEquals(new Pair<>(false, 0), fsm.maxString(input, 0));
    }

    @Test
    public void testDesirializeFSMGroup() {
        File file = new File("D:\\FormalLanguages\\src\\main\\resources\\fsmGroup.json");
        FinalStateMachineGroup fsmGroup = FinalStateMachineGroup.createFromFile(file);
    }

    @Test
    public void testLexer() {
        File file = new File("D:\\FormalLanguages\\src\\main\\resources\\fsmGroup.json");
        FinalStateMachineGroup fsmGroup = FinalStateMachineGroup.createFromFile(file);
        String input = "begin end";

        Lexer lexer = new Lexer(fsmGroup);
        List<Token> tokens = lexer.tokenize(input);

        Assertions.assertEquals("start", tokens.get(0).getType());
        Assertions.assertEquals("whitespace", tokens.get(1).getType());
        Assertions.assertEquals("finish", tokens.get(2).getType());
        file = new File("D:\\FormalLanguages\\src\\main\\resources\\fsmGroup2.json");
        fsmGroup = FinalStateMachineGroup.createFromFile(file);
        lexer.setFsmGroup(fsmGroup);
        tokens = lexer.tokenize(input);
        Assertions.assertEquals("priority", tokens.get(0).getType());
        Assertions.assertEquals("whitespace", tokens.get(1).getType());
        Assertions.assertEquals("finish", tokens.get(2).getType());
    }
}
