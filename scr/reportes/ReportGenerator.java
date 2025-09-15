package reportes;

import campus.Graph;
import estudiantes.AVLTree;
import estudiantes.ContextoOrdenamiento;
import estudiantes.Estudiante;
import estudiantes.OrdenamientoStrategy;
import incidencias.MaxHeap;

import java.util.List;
import java.util.ArrayList;

public class ReportGenerator {

    /* =========================
       1. TIEMPOS DE ORDENAMIENTO
       ========================= */
    public void generarReporteOrdenamiento(AVLTree arbolEstudiantes, ContextoOrdenamiento contexto) {
        System.out.println("\n=== COMPARATIVA DE ALGORITMOS DE ORDENAMIENTO ===");

        List<Estudiante> estudiantes = arbolEstudiantes.inOrder();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes para comparar");
            return;
        }

        System.out.println("Cantidad de estudiantes: " + estudiantes.size());
        System.out.println("\nTiempos en milisegundos:");
        System.out.println("Algoritmo     | ID   | Nombre | Promedio");
        System.out.println("----------------------------------------");

        medirTiempoAlgoritmo("Inserción", new estudiantes.InsercionStrategy(), estudiantes, contexto);
        medirTiempoAlgoritmo("Selección", new estudiantes.SeleccionStrategy(), estudiantes, contexto);
        medirTiempoAlgoritmo("Shell", new estudiantes.ShellStrategy(), estudiantes, contexto);
        medirTiempoAlgoritmo("QuickSort", new estudiantes.QuickSortStrategy(), estudiantes, contexto);
        medirTiempoAlgoritmo("MergeSort", new estudiantes.MergeSortStrategy(), estudiantes, contexto);
        medirTiempoAlgoritmo("HeapSort", new estudiantes.HeapSortStrategy(), estudiantes, contexto);
        medirTiempoAlgoritmo("Radix", new estudiantes.RadixStrategy(), estudiantes, contexto);
    }

    private void medirTiempoAlgoritmo(String nombre, OrdenamientoStrategy estrategia,
                                      List<Estudiante> estudiantes, ContextoOrdenamiento contexto) {
        System.out.printf("%-12s |", nombre);

        List<Estudiante> copia1 = new ArrayList<>(estudiantes);
        long tiempoId = medirTiempoEjecucion(copia1, contexto, estrategia, "id");
        System.out.printf(" %5d |", tiempoId);

        List<Estudiante> copia2 = new ArrayList<>(estudiantes);
        long tiempoNombre = medirTiempoEjecucion(copia2, contexto, estrategia, "nombre");
        System.out.printf(" %6d |", tiempoNombre);

        List<Estudiante> copia3 = new ArrayList<>(estudiantes);
        long tiempoPromedio = medirTiempoEjecucion(copia3, contexto, estrategia, "promedio");
        System.out.printf(" %8d |%n", tiempoPromedio);
    }

    private long medirTiempoEjecucion(List<Estudiante> lista, ContextoOrdenamiento contexto,
                                      OrdenamientoStrategy estrategia, String campo) {
        try {
            contexto.setStrategy(estrategia);
            long start = System.nanoTime();
            contexto.ordenar(lista, campo);
            long end = System.nanoTime();
            return (end - start) / 1000000;
        } catch (Exception e) {
            return -1;
        }
    }

    /* =========================
       2. MÉTRICAS DE RUTAS
       ========================= */
    public void generarReporteRutas(Graph grafoCampus) {
        System.out.println("\n=== MÉTRICAS DE RUTAS EN EL CAMPUS ===");
        System.out.println("Total de edificios: " + grafoCampus.getAdj().size());

        System.out.println("¿El campus es conexo?: " + grafoCampus.isConnected());

        if (!grafoCampus.getAdj().isEmpty()) {
            String start = grafoCampus.getAdj().keySet().iterator().next();
            System.out.println("Recorrido BFS desde " + start + ": " + grafoCampus.bfs(start));
            System.out.println("Recorrido DFS desde " + start + ": " + grafoCampus.dfs(start));
        }

        // MST con Kruskal
        Graph.MSTResult mst = grafoCampus.kruskalMST();
        System.out.println("Costo total del árbol de expansión mínima: " + mst.totalCost);
        System.out.println("Aristas seleccionadas: " + mst.edges);
    }

    /* =========================
       3. TAMAÑOS DE ESTRUCTURAS
       ========================= */
    public void generarReporteEstructuras(AVLTree arbolEstudiantes, MaxHeap incidencias, Graph grafoCampus) {
        System.out.println("\n=== TAMAÑOS DE ESTRUCTURAS ===");
        System.out.println("Cantidad de estudiantes registrados: " + arbolEstudiantes.inOrder().size());
        System.out.println("Cantidad de incidencias en cola: " + incidencias.size());
        System.out.println("Edificios en el campus: " + grafoCampus.getAdj().size());

        // contar aristas
        int totalAristas = grafoCampus.getAdj().values().stream().mapToInt(List::size).sum() / 2;
        System.out.println("Pasillos (aristas) en el campus: " + totalAristas);
    }
    // Método maestro para generar todos los reportes de una sola vez
public void generarReportes(
        estudiantes.AVLTree arbolEstudiantes,
        estudiantes.ContextoOrdenamiento contexto,
        incidencias.MaxHeap incidencias,
        campus.Graph grafoCampus) {

    // 1. Reporte de ordenamiento
    generarReporteOrdenamiento(arbolEstudiantes, contexto);

    // 2. Reporte de rutas en el campus
    generarReporteRutas(grafoCampus);

    // 3. Reporte de tamaños de estructuras
    generarReporteEstructuras(arbolEstudiantes, incidencias, grafoCampus);
}

}
