package org.ontopmed.api;

import org.springframework.stereotype.Service;
import sim.explainer.library.SimExplainer;
import sim.explainer.library.enumeration.ImplementationMethod;
import java.math.BigDecimal;
import java.util.Map;

@Service
public class SimilarityBridge {

    private static final String ONTOLOGY_PATH = "../ontology/custom-med.owl";

    public Map<String, Object> getSimilarity(String concept1, String concept2) throws Exception {
        SimExplainer explainer = new SimExplainer(
            ONTOLOGY_PATH,
            null, null, null, null, null
        );

        BigDecimal score = explainer.similarity(
            ImplementationMethod.DYNAMIC_SIM,
            concept1,
            concept2
        );

        String explanationJson = explainer.getExplanationAsJson(concept1, concept2).toString();

        return Map.of(
            "score", score,
            "backtrace", explanationJson
        );
    }
}
