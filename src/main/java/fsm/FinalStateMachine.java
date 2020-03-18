package fsm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;
import json.FSMDesiarializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class FinalStateMachine {

    private Set<String> alphabet;
    private Set<String> states;
    private Set<String> startStates;
    private Set<String> finalStates;
    private Map<Pair<String, String>, Set<String>> transitions;
    private Set<String> currentStates;
    //type of token, which is recognized by the machine
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public void setStartStates(Set<String> startStates) {
        this.startStates = startStates;
        this.currentStates = ((Set) ((HashSet) startStates).clone());
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }

    public void setTransitions(Map<Pair<String, String>, Set<String>> transitions) {
        this.transitions = transitions;
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

    //return machine in start condition
    public void resetMachine() {
        currentStates = startStates;
    }

    //transition by signal
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
                //checking for a final state
                for (String state : currentStates) {
                    if (finalStates.contains(state)) {
                        resetMachine();
                        return new Pair<>(res, count);
                    }
                }
            }
            //can`t reach final state
            else {
                resetMachine();
                return new Pair<>(false, 0);
            }

        }
        return new Pair<>(res, count);
    }

}