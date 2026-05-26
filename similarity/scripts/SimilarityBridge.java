@RestController
@RequestMapping("/similarity")
public class SimilarityBridge {

    @GetMapping
    public ResponseEntity<Map<String, Object>> getSimilarity(
            @RequestParam String concept1,
            @RequestParam String concept2,
            @RequestParam(defaultValue = "snomed") String ontology) throws Exception {

        SimExplainer sim = new SimExplainer(resolveOntologyPath(ontology));
        double score = sim.similarity(ImplementationMethod.DYNAMIC_SIM, concept1, concept2);
        String backtraceJson = sim.getExplanationAsJson();

        return ResponseEntity.ok(Map.of(
            "score", score,
            "backtrace", new ObjectMapper().readTree(backtraceJson)
        ));
    }

    private String resolveOntologyPath(String name) {
        return switch (name) {
            case "snomed" -> "similarity/preference-profiles/../ontology/snomed-ct.owl";
            default -> "ontology/custom-med.owl";
        };
    }
}
