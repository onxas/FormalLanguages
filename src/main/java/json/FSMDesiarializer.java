package json;

import com.google.gson.*;
import fsm.FinalStateMachine;
import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Дессериализатор автомата
 */
public class FSMDesiarializer implements JsonDeserializer<FinalStateMachine> {
    @Override
    public FinalStateMachine deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        Set<String> alphabet = context.deserialize(
                json.getAsJsonArray("alphabet"), HashSet.class
        );
        Set<String> states = context.deserialize(
                json.getAsJsonArray("states"), HashSet.class
        );
        Set<String> finalStates = context.deserialize(
                json.getAsJsonArray("finalStates"), HashSet.class
        );
        Set<String> startStates = context.deserialize(
                json.getAsJsonArray("startStates"), HashSet.class
        );
        Long priority = context.deserialize(json.get("priority"), Long.class);

        String id = context.deserialize(json.get("type"), String.class);

        Map<String, Map<String, List<String>>> matrix = context.deserialize(json.getAsJsonObject("matrix"), Map.class);
        Map<Pair<String, String>, Set<String>> transitions = matrix.entrySet().stream()
                .flatMap(x -> x.getValue().entrySet().stream()
                        .map(y -> new Pair<>(new Pair<>(x.getKey(), y.getKey()), new HashSet<>(y.getValue()))))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        FinalStateMachine fsm = new FinalStateMachine();
        fsm.setAlphabet(alphabet);
        fsm.setStates(states);
        fsm.setFinalStates(finalStates);
        fsm.setStartStates(startStates);
        fsm.setTransitions(transitions);
        fsm.setPriority(priority);
        fsm.setType(id);

        return fsm;
    }
}
