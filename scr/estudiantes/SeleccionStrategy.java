package estudiantes;

import java.util.List;
import java.util.Collections;

public class SeleccionStrategy implements OrdenamientoStrategy {

    @Override
    public void ordenar(List<Estudiante> lista, String campo) {
        int n = lista.size();
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                boolean condition = false;
                switch (campo.toLowerCase()) {
                    case "id": condition = lista.get(j).id < lista.get(min_idx).id; break;
                    case "nombre": condition = lista.get(j).nombre.compareToIgnoreCase(lista.get(min_idx).nombre) < 0; break;
                    case "promedio": condition = lista.get(j).promedio < lista.get(min_idx).promedio; break;
                }
                if (condition) min_idx = j;
            }
            Collections.swap(lista, i, min_idx);
        }
    }
}

