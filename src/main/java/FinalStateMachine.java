import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class FinalStateMachine {

    private HashSet<String> alphabet;
    private HashSet<String> states;
    private HashSet<String> startStates;
    private HashSet<String> finalStates;
    private HashMap<Pair<String, String>, HashSet<String>> transition;
    private HashSet<String> currentStates;


    public void setAlphabet(HashSet<String> alphabet) {
        this.alphabet = alphabet;
    }

    public void setStates(HashSet<String> states) {
        this.states = states;
    }

    public void setStartStates(HashSet<String> startStates) {
        this.startStates = startStates;
        this.currentStates = (HashSet) startStates;
    }

    public void setFinalStates(HashSet<String> finalStates) {
        this.finalStates = finalStates;
    }

    public void setTransition(HashMap<Pair<String, String>, HashSet<String>> transition) {
        this.transition = transition;
    }

    public void resetMachine() {
        currentStates = (HashSet) startStates;
    }

    private void inputSignal(String signal) {
        HashSet<String> newStates = new HashSet<>();
        for (String state : currentStates) {
            newStates.addAll(transition.get(new Pair<>(state, signal)));
        }
        currentStates = newStates;
    }

    public Pair<Boolean, Integer> maxString(String string, int pos) {
        if (pos >= string.length())
            return new Pair<>(false, 0);
        int count = 0;
        boolean res = false;
        while (pos != string.length()) {
            inputSignal("" + string.charAt(pos++));
            for (String state : currentStates) {
                if (finalStates.contains(state))
                    break;
            }
            count++;
        }
        if (count > 0) res = true;
        return new Pair<Boolean, Integer>(res, count);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalStateMachine that = (FinalStateMachine) o;
        return alphabet.equals(that.alphabet) &&
                states.equals(that.states) &&
                startStates.equals(that.startStates) &&
                finalStates.equals(that.finalStates) &&
                transition.equals(that.transition) &&
                currentStates.equals(that.currentStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alphabet, states, startStates, finalStates, transition, currentStates);
    }
}