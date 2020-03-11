import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Tests {

    Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().registerTypeAdapter(FinalStateMachine.class, new FSMDesiarializer()).create();
    File fsm;
    FinalStateMachine testFSM;

    public static FinalStateMachine getExampleFSM() {
        FinalStateMachine fsm = new FinalStateMachine();
        fsm.setAlphabet(new HashSet<>(Arrays.asList("a", "b")));
        fsm.setStates(new HashSet<>(Arrays.asList("0", "1", "2", "3", "4", "5")));
        fsm.setStartStates(new HashSet<>(Arrays.asList("0", "1")));
        fsm.setFinalStates(new HashSet<>(Arrays.asList("4", "5")));
        fsm.setTransition(new HashMap<>() {{
            put(new Pair<>("0", "a"), new HashSet<>(Arrays.asList("2")));
        }});
        return fsm;
    }

    @Test
    public void TestDeserializationFSM() throws IOException {
        fsm = new File("D:\\Study\\FormalLang\\src\\main\\resources\\serializeFSM.txt");
        testFSM = getExampleFSM();
        String output = gson.toJson(testFSM);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fsm));
        writer.write(output);
        writer.close();
        BufferedReader reader = new BufferedReader(new FileReader(fsm));
        String input = "";
        String line;
        while ((line = reader.readLine()) != null) {
            input += line;
        }
        reader.close();
        FinalStateMachine fsm = gson.fromJson(input, FinalStateMachine.class);
        Assertions.assertEquals(fsm, testFSM);
    }

    @Test
    public void testMaxString() throws IOException {
        testFSM = new FinalStateMachine();
        BufferedReader reader = new BufferedReader(new FileReader(fsm));
        String input = "";
        String line;
        while ((line = reader.readLine()) != null) {
            input += line;
        }
        reader.close();
        FinalStateMachine fsm = gson.fromJson(input, FinalStateMachine.class);
        String exampleString = ""
    }
}
