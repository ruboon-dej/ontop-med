import org.example.SimExplainer;
import org.example.ImplementationMethod;

public class SmokeTest {
    public static void main(String[] args) throws Exception {
        SimExplainer sim = new SimExplainer("path/to/place.krss");
        double score = sim.similarity(
            ImplementationMethod.DYNAMIC_SIM, "ActivePlace", "Mangrove"
        );
        System.out.println("Score: " + score);   // expect ~0.645
        System.out.println(sim.getExplanationAsJson());
    }
}