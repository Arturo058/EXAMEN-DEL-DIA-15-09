package estudiantes;

import java.util.List;
import java.util.Collections;

public class HeapSortStrategy implements OrdenamientoStrategy {

    @Override
    public void ordenar(List<Estudiante> lista, String campo) {
        int n = lista.size();
        for (int i = n / 2 - 1; i >= 0; i--) heapify(lista, n, i, campo);
        for (int i = n - 1; i >= 0; i--) {
            Collections.swap(lista, 0, i);
            heapify(lista, i, 0, campo);
        }
    }

    private void heapify(List<Estudiante> arr, int n, int i, String campo) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        switch (campo.toLowerCase()) {
            case "id":
                if (l < n && arr.get(l).id > arr.get(largest).id) largest = l;
                if (r < n && arr.get(r).id > arr.get(largest).id) largest = r;
                break;
            case "nombre":
                if (l < n && arr.get(l).nombre.compareToIgnoreCase(arr.get(largest).nombre) > 0) largest = l;
                if (r < n && arr.get(r).nombre.compareToIgnoreCase(arr.get(largest).nombre) > 0) largest = r;
                break;
            case "promedio":
                if (l < n && arr.get(l).promedio > arr.get(largest).promedio) largest = l;
                if (r < n && arr.get(r).promedio > arr.get(largest).promedio) largest = r;
                break;
        }

        if (largest != i) {
            Collections.swap(arr, i, largest);
            heapify(arr, n, largest, campo);
        }
    }
}
