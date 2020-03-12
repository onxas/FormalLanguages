import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Tests {

    Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().registerTypeAdapter(FinalStateMachine.class, new FSMDesiarializer()).create();
    File jsonFSM = new File("D:\\Study\\FormalLang\\src\\main\\resources\\testFSM.json");
    FinalStateMachine testFSM;

    FinalStateMachine createFromJson(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return gson.fromJson(json.toString(), FinalStateMachine.class);
        } catch (Exception ex) {
            return null;
        }
    }

    @Test
    public void TestDeserializationFSM() {
        FinalStateMachine finalStateMachine = createFromJson(jsonFSM);
    }

    @Test
    public void testMaxString() {
        FinalStateMachine fsm = new FinalStateMachine();
        Set<String> alphabet = new HashSet<>();
        Set<String> states = new HashSet<>(Arrays.asList("noChar", "lastLow", "lastUpper", "lastSame"));
        Set<String> startStates = new HashSet<>(Arrays.asList("noChar"));
        Set<String> endStates = new HashSet<>(Arrays.asList("lastSame"));
        Map<Pair<String, String>, Set<String>> transitions = new HashMap<>();

        for (char i = 'a'; i <= 'z'; i++) {
            String symbol = "" + i;
            alphabet.add(symbol);
            transitions.put(new Pair<>("noChar", symbol), new HashSet<>(Arrays.asList("lastLow")));
            transitions.put(new Pair<>("lastUpper", symbol), new HashSet<>(Arrays.asList("lastLow")));
            transitions.put(new Pair<>("lastLow", symbol), new HashSet<>(Arrays.asList("lastSame")));
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            String symbol = "" + i;
            alphabet.add(symbol);
            transitions.put(new Pair<>("noChar", symbol), new HashSet<>(Arrays.asList("lastUpper")));
            transitions.put(new Pair<>("lastLow", symbol), new HashSet<>(Arrays.asList("lastUpper")));
            transitions.put(new Pair<>("lastUpper", symbol), new HashSet<>(Arrays.asList("lastSame")));
        }

        fsm.setStates(states);
        fsm.setStartStates(startStates);
        fsm.setFinalStates(endStates);
        fsm.setAlphabet(alphabet);
        fsm.setTransitions(transitions);

        String testString = "aBaB";
        Assertions.assertEquals(new Pair<>(true, 4), fsm.maxString(testString, 0));

        fsm.resetMachine();
        testString = "xxxAvAdxxx";
        Assertions.assertEquals(new Pair<>(true, 4), fsm.maxString(testString, 3));

        fsm.resetMachine();
        Assertions.assertEquals(new Pair<>(true, 1), fsm.maxString(testString, 0));

        fsm.resetMachine();
        testString = "1AbCd";
        Assertions.assertEquals(new Pair<>(false, 0), fsm.maxString(testString, 0));
    }
}
