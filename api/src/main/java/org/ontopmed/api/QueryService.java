package org.ontopmed.api;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class QueryService {

    private final WebClient webClient = WebClient.create("http://localhost:8080");

    public String queryConcepts(String concept1, String concept2) {
        String sparql =
            "PREFIX idmp: <https://spec.pistoiaalliance.org/idmp/ontology/ISO/> " +
            "SELECT ?drug ?name WHERE { " +
            "?drug a idmp:MedicinalProduct ; " +
            "idmp:hasName ?name . }";

        String encoded = URLEncoder.encode(sparql, StandardCharsets.UTF_8);

        return webClient.get()
                .uri(URI.create("http://localhost:8080/sparql?query=" + encoded))
                .header("Accept", "application/sparql-results+json")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
