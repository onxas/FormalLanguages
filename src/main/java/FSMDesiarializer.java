import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import javafx.util.Pair;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;

public class FSMDesiarializer implements JsonDeserializer<FinalStateMachine> {
    @Override
    public FinalStateMachine deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();
        HashSet<String> alphabet = context.deserialize(
                json.getAsJsonArray("alphabet"), HashSet.class
        );
        HashSet<String> states = context.deserialize(
                json.getAsJsonArray("states"), HashSet.class
        );
        HashSet<String> finalStates = context.deserialize(
                json.getAsJsonArray("finalStates"), HashSet.class
        );
        HashSet<String> startStates = context.deserialize(
                json.getAsJsonArray("startStates"), HashSet.class
        );

        HashSet<String> currenstStates = context.deserialize(
                json.getAsJsonArray("currentStates"), HashSet.class
        );
        HashMap<Pair<String, String>, HashSet<String>> transition = context.deserialize(json.getAsJsonArray("transition"),
                new TypeToken<HashMap<Pair<String, String>, HashSet<String>>>() {
                }.getType());


        FinalStateMachine fsm = new FinalStateMachine();
        fsm.setAlphabet(alphabet);
        fsm.setStates(states);
        fsm.setFinalStates(finalStates);
        fsm.setStartStates(startStates);
        fsm.setTransition(transition);

        return fsm;
    }
}
