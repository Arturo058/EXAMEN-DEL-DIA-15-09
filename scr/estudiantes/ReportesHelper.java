package estudiantes;

import java.util.List;

public class ReportesHelper {
    public static double calcularPromedioGeneral(List<Estudiante> estudiantes) {
        if (estudiantes.isEmpty()) return 0;
        double suma = 0;
        for (Estudiante e : estudiantes) {
            suma += e.promedio;
        }
        return suma / estudiantes.size();
    }
    
    public static int contarEstudiantesPorCarrera(List<Estudiante> estudiantes, String carrera) {
        int count = 0;
        for (Estudiante e : estudiantes) {
            if (e.carrera.equalsIgnoreCase(carrera)) {
                count++;
            }
        }
        return count;
    }
}