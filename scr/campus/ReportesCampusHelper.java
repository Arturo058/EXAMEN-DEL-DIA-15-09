package campus;

import java.util.Map;
import java.util.List;
import java.util.Map.Entry;

public class ReportesCampusHelper {
    public static int contarTotalAristas(Graph grafo) {
        int totalAristas = 0;
        for (Entry<String, List<Edge>> entry : grafo.getAdj().entrySet()) {  // ← Cambiado a getAdj()
            totalAristas += entry.getValue().size();
        }
        return totalAristas / 2;
    }

    public static int calcularPesoTotal(Graph grafo) {
        int totalPeso = 0;
        for (Entry<String, List<Edge>> entry : grafo.getAdj().entrySet()) {  // ← Cambiado a getAdj()
            for (Edge edge : entry.getValue()) {
                totalPeso += edge.weight;
            }
        }
        return totalPeso / 2;
    }

    public static int[] encontrarMinMaxPeso(Graph grafo) {
        int minPeso = Integer.MAX_VALUE;
        int maxPeso = Integer.MIN_VALUE;
        
        for (Entry<String, List<Edge>> entry : grafo.getAdj().entrySet()) {  // ← Cambiado a getAdj()
            for (Edge edge : entry.getValue()) {
                minPeso = Math.min(minPeso, edge.weight);
                maxPeso = Math.max(maxPeso, edge.weight);
            }
        }
        return new int[]{minPeso, maxPeso};
    }
}