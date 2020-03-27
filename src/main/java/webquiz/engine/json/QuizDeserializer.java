package webquiz.engine.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import webquiz.engine.domain.Quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizDeserializer extends StdDeserializer<Quiz> {

    public QuizDeserializer() {
        this(null);
    }

    public QuizDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Quiz deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        String title = node.get("title").textValue();

        String text = node.get("text").textValue();

        ObjectReader optionsReader = new ObjectMapper().readerFor(new TypeReference<List<String>>() {});
        JsonNode optionsNode = node.get("options");
        List<String> options;
        if (optionsNode == null) {
            options = new ArrayList<>();
        } else {
            options = optionsReader.readValue(optionsNode);
        }

        ObjectReader answerReader = new ObjectMapper().readerFor(new TypeReference<List<Integer>>() {});
        JsonNode answerNode = node.get("answer");
        List<Integer> answer;
        if (answerNode == null) {
            answer = new ArrayList<>();
        } else {
            answer = answerReader.readValue(answerNode);
        }

        return new Quiz(title, text, options, answer);
    }
}
