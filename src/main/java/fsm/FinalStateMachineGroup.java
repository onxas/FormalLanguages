package fsm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;
import json.FSMGroupDesiarializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class FinalStateMachineGroup {

    private List<FinalStateMachine> fsmList;

    public FinalStateMachineGroup(List<FinalStateMachine> fsmList) {
        this.fsmList = fsmList;
    }

    public List<FinalStateMachine> getFsmList() {
        return fsmList;
    }

    public void setFsmList(List<FinalStateMachine> fsmList) {
        this.fsmList = fsmList;
    }

    //maxString for all machines in list. Return result and type of token
    public Pair<Pair<Boolean, Integer>, String> maxString(String input, int pos) {
        for (FinalStateMachine fsm : fsmList) {
            Pair<Pair<Boolean, Integer>, String> result = new Pair<>(fsm.maxString(input, pos), fsm.getType());
            if (result.getKey().getKey()) return result;
        }
        return new Pair<>(new Pair<>(false, 0), null);
    }

    //File - array of path`s of fsm json`s
    public static FinalStateMachineGroup createFromFile(File file) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(FinalStateMachineGroup.class, new FSMGroupDesiarializer()).create();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            return gson.fromJson(json.toString(), FinalStateMachineGroup.class);
        } catch (Exception ex) {
            return null;
        }
    }
}
