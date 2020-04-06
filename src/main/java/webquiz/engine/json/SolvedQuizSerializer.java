package webquiz.engine.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import webquiz.engine.domain.SolvedQuiz;

import java.io.IOException;

public class SolvedQuizSerializer extends StdSerializer<SolvedQuiz> {

    public SolvedQuizSerializer() {
        this(null);
    }

    public SolvedQuizSerializer(Class<SolvedQuiz> t) {
        super(t);
    }

    @Override
    public void serialize(SolvedQuiz solvedQuiz, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        gen.writeNumberField("id", solvedQuiz.getQuiz().getId());
        gen.writeStringField("completedAt", solvedQuiz.getCompletedAt().toString());

        gen.writeEndObject();
    }
}
