package fsm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;
import json.FSMDesiarializer;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
/**
 * Конечный недетерминированный автомат
 */
public class FinalStateMachine {

    private Set<String> alphabet;
    private Set<String> states;
    private Set<String> startStates;
    private Set<String> finalStates;
    private Map<Pair<String, String>, Set<String>> transitions;
    private Set<String> currentStates;
    private Long priority;
    //Тип токена, который распознаёт автомат
    private String type;


    public void setStartStates(Set<String> startStates) {
        this.startStates = startStates;
        this.currentStates = new HashSet<>(startStates);
    }

    public static FinalStateMachine createFromJson(File file) {
        Gson gson = new GsonBuilder().registerTypeAdapter(FinalStateMachine.class, new FSMDesiarializer()).create();
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

    /**
     * Возвтращает автомат в исходное состояние
     */
    public void resetMachine() {
        currentStates = startStates;
    }

    /**
     * Переход по сигналу в следующие состояния
     *
     * @param signal
     * @return
     */
    private boolean inputSignal(String signal) {
        HashSet<String> newStates = new HashSet<>();
        try {
            for (String state : currentStates) {
                newStates.addAll(transitions.get(new Pair<>(state, signal)));
            }
        } catch (NullPointerException e) {
            return false;
        }
        currentStates = newStates;
        return true;
    }

    /**
     * Возвращает максимальную подстроку принадлежающую языку, который задаёт автомат
     *
     * @param string
     * @param pos
     * @return
     */
    public Pair<Boolean, Integer> maxString(String string, int pos) {
        if (pos >= string.length())
            return new Pair<>(false, 0);
        int count = 0;
        boolean res = false;
        while (pos != string.length()) {
            if (inputSignal(string.substring(pos, pos + 1))) {
                count++;
                res = true;
                pos++;
                //првоеряем заключительное состояние
                for (String state : currentStates) {
                    if (finalStates.contains(state)) {
                        resetMachine();
                        return new Pair<>(res, count);
                    }
                }
            }
            //не дошли до заключительного состояния
            else {
                resetMachine();
                return new Pair<>(false, 0);
            }

        }
        return new Pair<>(res, count);
    }

}