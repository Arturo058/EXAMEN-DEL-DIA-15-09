package incidencias;

import java.util.List;

public class ReportesIncidenciasHelper {
    public static int[] contarPrioridades(List<Incidente> incidencias) {
        int alta = 0, media = 0, baja = 0;
        for (Incidente inc : incidencias) {
            switch (inc.prioridad.toUpperCase()) {
                case "ALTA": alta++; break;
                case "MEDIA": media++; break;
                case "BAJA": baja++; break;
            }
        }
        return new int[]{alta, media, baja};
    }

    public static int contarIncidenciasRecientes(List<Incidente> incidencias, int horas) {
        int count = 0;
        java.time.LocalDateTime limite = java.time.LocalDateTime.now().minusHours(horas);
        for (Incidente inc : incidencias) {
            if (inc.timestamp.isAfter(limite)) {
                count++;
            }
        }
        return count;
    }
}