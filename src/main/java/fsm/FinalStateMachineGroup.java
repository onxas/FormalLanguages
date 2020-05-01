package fsm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.util.Pair;
import json.FSMGroupDesiarializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;

@Data
@AllArgsConstructor
/**
 * Группа автоматов
 */
public class FinalStateMachineGroup {

    private List<FinalStateMachine> fsmList;

    /**
     * Ищет маскимальную подстроку принадлежащую языку одного из автоматов из группы
     *
     * @param input
     * @param pos
     * @return
     */
    public Pair<Pair<Boolean, Integer>, String> maxString(String input, int pos) {
        fsmList.sort(Comparator.comparing(FinalStateMachine::getPriority));
        Pair<Pair<Boolean, Integer>, String> result = new Pair<>(new Pair<>(false, 0), null);
        for (FinalStateMachine fsm : fsmList) {
            Pair<Boolean, Integer> maxStringResult = fsm.maxString(input, pos);
            if (maxStringResult.getKey() && maxStringResult.getValue() >= result.getKey().getValue()) {
                result = new Pair<>(new Pair<>(true, maxStringResult.getValue()), fsm.getType());
            }
        }
        return result;
    }

    /**
     * Создаёт группу автомата из файла, который содержит пути к файлам автоматов из группы
     *
     * @param file
     * @return
     */
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
