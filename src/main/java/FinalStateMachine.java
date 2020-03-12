import javafx.util.Pair;

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

    public void resetMachine() {
        currentStates = startStates;
    }

    private void inputSignal(String signal) {
        HashSet<String> newStates = new HashSet<>();
        for (String state : currentStates) {
            newStates.addAll(transitions.get(new Pair<>(state, signal)));
        }
        currentStates = newStates;
    }

    public Pair<Boolean, Integer> maxString(String string, int pos) {
        if (pos >= string.length())
            return new Pair<>(false, 0);
        int count = 0;
        boolean res = false;
        while (pos != string.length()) {
            try {
                inputSignal(string.substring(pos, pos + 1));
            } catch (NullPointerException e) {
                return new Pair<>(res, count);
            }
            for (String state : currentStates) {
                if (finalStates.contains(state))
                    return new Pair<>(res, count);
            }
            count++;
            res = true;
            pos++;
        }
        return new Pair<>(res, count);
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
                transitions.equals(that.transitions) &&
                currentStates.equals(that.currentStates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alphabet, states, startStates, finalStates, transitions, currentStates);
    }
}