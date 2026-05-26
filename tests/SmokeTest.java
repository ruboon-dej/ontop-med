import sim.explainer.library.SimExplainer;
import sim.explainer.library.enumeration.ImplementationMethod;
import java.math.BigDecimal;

public class SmokeTest {
    public static void main(String[] args) throws Exception {
        System.out.println("=== Smoke Test: sim-elh-explainer-jar ===");

        SimExplainer explainer = new SimExplainer(
            "tests/ontology/place.krss",
            null, null, null, null, null
        );

        BigDecimal score = explainer.similarity(
            ImplementationMethod.DYNAMIC_SIM,
            "ActivePlace",
            "Mangrove"
        );

        System.out.println("Similarity(ActivePlace, Mangrove) = " + score);
        System.out.println("Expected: ~0.645");
        System.out.println("PASS: " + (score.doubleValue() > 0.6 && score.doubleValue() < 0.7));
    }
}
