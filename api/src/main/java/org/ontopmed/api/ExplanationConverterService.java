package org.ontopmed.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class ExplanationConverterService {

    @Value("${llm.provider:ollama}")
    private String provider;

    private final WebClient webClient = WebClient.create("http://localhost:11434");
    private final ObjectMapper mapper = new ObjectMapper();

    public String explain(String inputText) throws Exception {
        String prompt = loadPromptTemplate() + "\n\n" + inputText;
        return switch (provider) {
            case "ollama" -> callOllama(prompt);
            case "openai" -> "OpenAI not implemented yet";
            case "claude" -> "Claude not implemented yet";
            default -> throw new IllegalStateException("Unknown provider: " + provider);
        };
    }

    private String callOllama(String prompt) {
        String body = webClient.post()
                .uri("/api/generate")
                .bodyValue(Map.of(
                        "model", "llama3",
                        "prompt", prompt,
                        "stream", false
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            return mapper.readTree(body).get("response").asText();
        } catch (Exception e) {
            return "Could not parse Ollama response: " + body;
        }
    }

    private String loadPromptTemplate() throws Exception {
        Path p = Path.of("explanation/prompts/sim-elh-explainer-to-text.txt");
        return Files.exists(p) ? Files.readString(p) : "Explain the following medical concepts:";
    }
}
