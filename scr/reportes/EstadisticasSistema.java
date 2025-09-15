package reportes;

import estudiantes.AVLTree;
import estudiantes.Estudiante;
import estudiantes.ReportesHelper;
import incidencias.MaxHeap;
import incidencias.Incidente;
import incidencias.ReportesIncidenciasHelper;
import campus.Graph;
import campus.ReportesCampusHelper;
import java.util.List;

public class EstadisticasSistema {
    public void generarReporte(AVLTree arbolEstudiantes, MaxHeap heapIncidencias, Graph grafoCampus) {
        System.out.println("\n=== ESTADÍSTICAS DEL SISTEMA ===");
        
        // Estadísticas de estudiantes
        List<Estudiante> estudiantes = arbolEstudiantes.inOrder();
        System.out.println("\n--- ESTUDIANTES ---");
        System.out.println("Total registrados: " + estudiantes.size());
        if (!estudiantes.isEmpty()) {
            double promedioGeneral = ReportesHelper.calcularPromedioGeneral(estudiantes);
            System.out.println("Promedio general: " + String.format("%.2f", promedioGeneral));
        }

        // Estadísticas de incidencias
        List<Incidente> incidencias = heapIncidencias.getAll();
        System.out.println("\n--- INCIDENCIAS ---");
        System.out.println("Total pendientes: " + incidencias.size());
        int[] prioridades = ReportesIncidenciasHelper.contarPrioridades(incidencias);
        System.out.println("Alta: " + prioridades[0] + " | Media: " + prioridades[1] + " | Baja: " + prioridades[2]);

        // Estadísticas del campus
        System.out.println("\n--- CAMPUS ---");
        System.out.println("Edificios: " + grafoCampus.getAdj().size());  // ← Cambiado a getAdj()
        int totalPasillos = ReportesCampusHelper.contarTotalAristas(grafoCampus);
        System.out.println("Pasillos: " + totalPasillos);
    }
}