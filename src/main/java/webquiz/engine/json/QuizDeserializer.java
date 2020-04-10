package webquiz.engine.json;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import webquiz.engine.domain.QuizAnswer;
import webquiz.engine.domain.QuizOption;
import webquiz.engine.domain.Quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuizDeserializer extends StdDeserializer<Quiz> {

    public QuizDeserializer() {
        this(null);
    }

    public QuizDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Quiz deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Quiz quiz = new Quiz();
        JsonNode node = parser.getCodec().readTree(parser);

        String title = node.get("title").textValue();
        quiz.setTitle(title);

        String text = node.get("text").textValue();
        quiz.setText(text);

        ObjectReader optionsReader = new ObjectMapper().readerFor(new TypeReference<List<String>>() {});
        JsonNode optionsNode = node.get("options");
        List<String> optionsString;
        if (optionsNode == null) {
            throw new JsonParseException(parser, "'options' field has invalid format.");
        } else if (optionsNode.isEmpty()) {
            optionsString = new ArrayList<>();
        } else {
            optionsString = optionsReader.readValue(optionsNode);
        }
        List<QuizOption> quizOptions = optionsString.stream()
                .map(s -> new QuizOption(s, quiz)).collect(Collectors.toList());
        quiz.setQuizOptions(quizOptions);

        ObjectReader answerReader = new ObjectMapper().readerFor(new TypeReference<List<Integer>>() {});
        JsonNode answerNode = node.get("answer");
        List<Integer> answerInt;
        if (answerNode == null) {
            answerInt = new ArrayList<>();
        } else {
            answerInt = answerReader.readValue(answerNode);
        }
        List<QuizAnswer> quizAnswers = answerInt.stream()
                .map(i -> new QuizAnswer(i, quiz)).collect(Collectors.toList());
        quiz.setQuizAnswers(quizAnswers);

        return quiz;
    }
}
