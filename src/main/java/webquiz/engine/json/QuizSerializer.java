package webquiz.engine.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import webquiz.engine.domain.QuizOption;
import webquiz.engine.domain.Quiz;

import java.io.IOException;

public class QuizSerializer extends StdSerializer<Quiz> {

    public QuizSerializer() {
        this(null);
    }

    public QuizSerializer(Class<Quiz> t) {
        super(t);
    }

    @Override
    public void serialize(Quiz quiz, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("id", quiz.getId());
        gen.writeStringField("title", quiz.getTitle());
        gen.writeStringField("text", quiz.getText());
        gen.writeFieldName("options");
        gen.writeStartArray();
        for (QuizOption quizOption : quiz.getQuizOptions()) {
            gen.writeString(quizOption.getValue());
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }
}
