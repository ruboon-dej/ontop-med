package org.ontopmed.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MedVKGController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private SimilarityBridge similarityBridge;

    @Autowired
    private ExplanationConverterService explainer;

    @GetMapping("/compare")
    public ResponseEntity<Map<String, Object>> compare(
            @RequestParam String concept1,
            @RequestParam String concept2) throws Exception {

        // Phase 2: SPARQL query via Ontop
        String sparqlResult = queryService.queryConcepts(concept1, concept2);

        // Phase 3: Similarity score + backtrace
        Map<String, Object> simResult = similarityBridge.getSimilarity(concept1, concept2);

        // Phase 4: LLM explanation of the backtrace
        String explanation = explainer.explain(simResult.get("backtrace").toString());

        return ResponseEntity.ok(Map.of(
            "concept1",     concept1,
            "concept2",     concept2,
            "sparqlResult", sparqlResult,
            "score",        simResult.get("score"),
            "backtrace",    simResult.get("backtrace"),
            "explanation",  explanation
        ));
    }
}
