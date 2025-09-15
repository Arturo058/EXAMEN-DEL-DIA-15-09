package estudiantes;

import java.util.List;

public class ShellStrategy implements OrdenamientoStrategy {
    @Override
    public void ordenar(List<Estudiante> lista, String campo) {
        int n = lista.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                Estudiante temp = lista.get(i);
                int j; // <-- declarar j aquí para que esté visible en todos los case

                switch (campo) {
                    case "id":
                        for (j = i; j >= gap && lista.get(j - gap).id > temp.id; j -= gap)
                            lista.set(j, lista.get(j - gap));
                        break;

                    case "nombre":
                        for (j = i; j >= gap && lista.get(j - gap).nombre.compareToIgnoreCase(temp.nombre) > 0; j -= gap)
                            lista.set(j, lista.get(j - gap));
                        break;

                    case "promedio":
                        for (j = i; j >= gap && lista.get(j - gap).promedio > temp.promedio; j -= gap)
                            lista.set(j, lista.get(j - gap));
                        break;

                    default:
                        System.out.println("Campo no válido para ordenamiento.");
                        j = i; // asegurar que j esté inicializado
                        break;
                }
                lista.set(j, temp);
            }
        }
    }
}

