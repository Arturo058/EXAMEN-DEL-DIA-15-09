package reportes;

import campus.Graph;
import campus.ReportesCampusHelper;

public class MetricasCampus {
    public void generarReporte(Graph grafoCampus) {
        if (grafoCampus.getAdj().isEmpty()) {  // ← Cambiado a getAdj()
            System.out.println("El grafo del campus está vacío");
            return;
        }

        System.out.println("\n=== MÉTRICAS DEL CAMPUS ===");
        System.out.println("Número de edificios: " + grafoCampus.getAdj().size());  // ← Cambiado a getAdj()
        
        int totalAristas = ReportesCampusHelper.contarTotalAristas(grafoCampus);
        int totalPeso = ReportesCampusHelper.calcularPesoTotal(grafoCampus);
        int[] minMax = ReportesCampusHelper.encontrarMinMaxPeso(grafoCampus);

        System.out.println("Número de pasillos: " + totalAristas);
        System.out.println("Densidad del grafo: " + String.format("%.4f", 
            (2.0 * totalAristas) / (grafoCampus.getAdj().size() * (grafoCampus.getAdj().size() - 1))));  // ← Cambiado a getAdj()
        System.out.println("Peso promedio de pasillos: " + (totalPeso / totalAristas));
        System.out.println("Pasillo más corto: " + minMax[0] + " unidades");
        System.out.println("Pasillo más largo: " + minMax[1] + " unidades");
        System.out.println("Grafo conexo: " + grafoCampus.isConnected());
    }
}