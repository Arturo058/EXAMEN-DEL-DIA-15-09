package estudiantes;

import java.util.List;
import java.util.ArrayList;

public class MergeSortStrategy implements OrdenamientoStrategy {

    @Override
    public void ordenar(List<Estudiante> lista, String campo) {
        if (lista.size() > 1) {
            mergeSort(lista, campo);
        }
    }

    private void mergeSort(List<Estudiante> lista, String campo) {
        if (lista.size() < 2) return;
        int mid = lista.size() / 2;
        List<Estudiante> left = new ArrayList<>(lista.subList(0, mid));
        List<Estudiante> right = new ArrayList<>(lista.subList(mid, lista.size()));

        mergeSort(left, campo);
        mergeSort(right, campo);

        merge(lista, left, right, campo);
    }

    private void merge(List<Estudiante> lista, List<Estudiante> left, List<Estudiante> right, String campo) {
        int i = 0, j = 0, k = 0;
        while (i < left.size() && j < right.size()) {
            boolean condition = false;
            switch (campo.toLowerCase()) {
                case "id": condition = left.get(i).id <= right.get(j).id; break;
                case "nombre": condition = left.get(i).nombre.compareToIgnoreCase(right.get(j).nombre) <= 0; break;
                case "promedio": condition = left.get(i).promedio <= right.get(j).promedio; break;
            }
            lista.set(k++, condition ? left.get(i++) : right.get(j++));
        }
        while (i < left.size()) lista.set(k++, left.get(i++));
        while (j < right.size()) lista.set(k++, right.get(j++));
    }
}
