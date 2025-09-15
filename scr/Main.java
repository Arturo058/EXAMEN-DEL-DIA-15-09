import campus.CampusMap;
import campus.Graph;
import estudiantes.AVLTree;
import estudiantes.ContextoOrdenamiento;
import incidencias.MenuIncidencias;
import incidencias.MaxHeap;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        // ✅ Instancias principales que usaremos en los reportes
        AVLTree arbolEstudiantes = new AVLTree();
        ContextoOrdenamiento contextoOrdenamiento = new ContextoOrdenamiento();
        MaxHeap incidencias = new MaxHeap();
        Graph grafoCampus = new Graph();

        while (!salir) {
            System.out.println("\n=== CAMPUS NAVIGATOR & ANALYTICS ===");
            System.out.println("1. Módulo Campus");
            System.out.println("2. Módulo Estudiantes");
            System.out.println("3. Módulo Incidencias");
            System.out.println("4. Reportes");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    // Llamar al módulo Campus desde el paquete campus
                    CampusMap campusMap = new CampusMap();
                    campusMap.iniciar();
                    break;

                case 2:
                    estudiantes.MenuEstudiantes menuEstudiantes = new estudiantes.MenuEstudiantes();
                    menuEstudiantes.iniciar();
                    break;

                case 3:
                    System.out.println("\n--- Módulo Incidencias ---");
                    MenuIncidencias menuIncidencias = new MenuIncidencias();
                    menuIncidencias.iniciar();
                    break;

                case 4:
                    System.out.println("\n--- Reportes ---");
                    reportes.ReportGenerator reportGenerator = new reportes.ReportGenerator();
                    // ✅ Llamamos al método maestro pasando las estructuras
                    reportGenerator.generarReportes(arbolEstudiantes, contextoOrdenamiento, incidencias, grafoCampus);
                    break;

                case 5:
                    salir = true;
                    System.out.println("¡Gracias por usar Campus Navigator & Analytics!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        sc.close();
    }
}
