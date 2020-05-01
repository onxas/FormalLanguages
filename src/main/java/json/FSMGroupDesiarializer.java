package json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import fsm.FinalStateMachine;
import fsm.FinalStateMachineGroup;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Дессериализатор группы автоматов
 */
public class FSMGroupDesiarializer implements JsonDeserializer<FinalStateMachineGroup> {
    @Override
    public FinalStateMachineGroup deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        List<String> fsmJsonPaths = context.deserialize(jsonElement, List.class);
        List<FinalStateMachine> fsmList = new ArrayList<>();
        for (String path : fsmJsonPaths) {
            File jsonFile = new File(path);
            fsmList.add(FinalStateMachine.createFromJson(jsonFile));
        }
        return new FinalStateMachineGroup(fsmList);
    }
}
