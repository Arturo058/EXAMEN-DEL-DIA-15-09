package estudiantes;

import java.util.List;

public interface OrdenamientoStrategy {
    void ordenar(List<Estudiante> lista, String campo);
}
