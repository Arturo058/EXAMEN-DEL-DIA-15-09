package estudiantes;

import java.util.List;
import java.util.ArrayList;

public class RadixStrategy implements OrdenamientoStrategy {

    @Override
    public void ordenar(List<Estudiante> lista, String campo) {
        if (!campo.equalsIgnoreCase("id")) {
            System.out.println("Radix solo aplica para ID.");
            return;
        }
        int max = lista.stream().mapToInt(e -> e.id).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) countingSort(lista, exp);
    }

    private void countingSort(List<Estudiante> lista, int exp) {
        int n = lista.size();
        List<Estudiante> output = new ArrayList<>(lista);
        int[] count = new int[10];

        for (Estudiante e : lista) count[(e.id / exp) % 10]++;
        for (int i = 1; i < 10; i++) count[i] += count[i - 1];

        for (int i = n - 1; i >= 0; i--) {
            Estudiante e = lista.get(i);
            int index = (e.id / exp) % 10;
            output.set(count[index] - 1, e);
            count[index]--;
        }

        for (int i = 0; i < n; i++) lista.set(i, output.get(i));
    }
}
