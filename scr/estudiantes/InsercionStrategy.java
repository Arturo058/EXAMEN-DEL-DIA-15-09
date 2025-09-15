package estudiantes;

import java.util.List;

public class InsercionStrategy implements OrdenamientoStrategy {

    @Override
    public void ordenar(List<Estudiante> lista, String campo) {
        for (int i = 1; i < lista.size(); i++) {
            Estudiante key = lista.get(i);
            int j = i - 1;

            switch (campo.toLowerCase()) {
                case "id":
                    while (j >= 0 && lista.get(j).id > key.id) {
                        lista.set(j + 1, lista.get(j));
                        j--;
                    }
                    break;
                case "nombre":
                    while (j >= 0 && lista.get(j).nombre.compareToIgnoreCase(key.nombre) > 0) {
                        lista.set(j + 1, lista.get(j));
                        j--;
                    }
                    break;
                case "promedio":
                    while (j >= 0 && lista.get(j).promedio > key.promedio) {
                        lista.set(j + 1, lista.get(j));
                        j--;
                    }
                    break;
            }
            lista.set(j + 1, key);
        }
    }
}
