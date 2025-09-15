package estudiantes;

import java.util.List;

public class ContextoOrdenamiento {
    private OrdenamientoStrategy strategy;

    public void setStrategy(OrdenamientoStrategy strategy) {
        this.strategy = strategy;
    }

    public void ordenar(List<Estudiante> lista, String campo) {
        if (strategy != null) strategy.ordenar(lista, campo);
    }
}
