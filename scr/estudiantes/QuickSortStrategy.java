package estudiantes;

import java.util.List;
import java.util.Collections;

public class QuickSortStrategy implements OrdenamientoStrategy {

    @Override
    public void ordenar(List<Estudiante> lista, String campo) {
        quickSort(lista, 0, lista.size() - 1, campo);
    }

    private void quickSort(List<Estudiante> arr, int low, int high, String campo) {
        if (low < high) {
            int pi = partition(arr, low, high, campo);
            quickSort(arr, low, pi - 1, campo);
            quickSort(arr, pi + 1, high, campo);
        }
    }

    private int partition(List<Estudiante> arr, int low, int high, String campo) {
        Estudiante pivot = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            boolean condition = false;
            switch (campo.toLowerCase()) {
                case "id": condition = arr.get(j).id <= pivot.id; break;
                case "nombre": condition = arr.get(j).nombre.compareToIgnoreCase(pivot.nombre) <= 0; break;
                case "promedio": condition = arr.get(j).promedio <= pivot.promedio; break;
            }
            if (condition) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, high);
        return i + 1;
    }
}

