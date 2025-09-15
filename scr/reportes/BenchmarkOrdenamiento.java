package reportes;

import estudiantes.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class BenchmarkOrdenamiento {
    public void generarReporte(AVLTree arbolEstudiantes, ContextoOrdenamiento contexto) {
        List<Estudiante> estudiantes = arbolEstudiantes.inOrder();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes para realizar benchmarks");
            return;
        }

        System.out.println("\n=== BENCHMARK ALGORITMOS DE ORDENAMIENTO ===");
        System.out.println("Número de estudiantes: " + estudiantes.size());
        System.out.println("\nTiempos en milisegundos:");

        // Crear copias para cada prueba
        List<Estudiante>[] copias = new ArrayList[7];
        for (int i = 0; i < 7; i++) {
            copias[i] = new ArrayList<>(estudiantes);
        }

        // Benchmark para cada campo
        String[] campos = {"id", "nombre", "promedio"};
        for (String campo : campos) {
            if (campo.equals("id") || !campo.equals("promedio")) {
                System.out.println("\n--- Campo: " + campo.toUpperCase() + " ---");
                
                medirTiempo("Inserción", copias[0], contexto, new InsercionStrategy(), campo);
                medirTiempo("Selección", copias[1], contexto, new SeleccionStrategy(), campo);
                medirTiempo("Shell", copias[2], contexto, new ShellStrategy(), campo);
                medirTiempo("QuickSort", copias[3], contexto, new QuickSortStrategy(), campo);
                medirTiempo("MergeSort", copias[4], contexto, new MergeSortStrategy(), campo);
                medirTiempo("HeapSort", copias[5], contexto, new HeapSortStrategy(), campo);
                
                if (campo.equals("id")) {
                    medirTiempo("Radix", copias[6], contexto, new RadixStrategy(), campo);
                }
            }
        }
    }

    private void medirTiempo(String nombre, List<Estudiante> lista, ContextoOrdenamiento contexto, 
                            OrdenamientoStrategy estrategia, String campo) {
        try {
            contexto.setStrategy(estrategia);
            long startTime = System.nanoTime();
            contexto.ordenar(lista, campo);
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.printf("%-12s: %5d ms%n", nombre, duration);
        } catch (Exception e) {
            System.out.printf("%-12s: Error%n", nombre);
        }
    }
}