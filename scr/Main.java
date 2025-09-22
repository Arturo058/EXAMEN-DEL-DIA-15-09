import campus.CampusMap;
import campus.Graph;
import estudiantes.AVLTree;
import estudiantes.ContextoOrdenamiento;
import estudiantes.MenuEstudiantes;
import incidencias.MenuIncidencias;
import incidencias.MaxHeap;
import reportes.ReportGenerator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        // ✅ Instancias COMPARTIDAS
        AVLTree arbolEstudiantes = new AVLTree();
        ContextoOrdenamiento contextoOrdenamiento = new ContextoOrdenamiento();
        MaxHeap incidencias = new MaxHeap();  // ← Este mismo heap se compartirá
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
                    CampusMap campusMap = new CampusMap(grafoCampus);
                    campusMap.iniciar();
                    break;

                case 2:
                    MenuEstudiantes menuEstudiantes = new MenuEstudiantes(arbolEstudiantes);
                    menuEstudiantes.iniciar();
                    break;

                case 3:
                    // ✅ PASA el heap compartido
                    MenuIncidencias menuIncidencias = new MenuIncidencias(incidencias);
                    menuIncidencias.iniciar();
                    break;

                case 4:
                    System.out.println("\n--- Reportes ---");
                    ReportGenerator reportGenerator = new ReportGenerator();
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