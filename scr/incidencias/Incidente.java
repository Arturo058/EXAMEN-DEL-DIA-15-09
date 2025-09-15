package incidencias;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Incidente {
    public int id;
    public String titulo;
    public String prioridad; // "Alta", "Media", "Baja"
    public LocalDateTime timestamp;
    public static int contadorId = 1;

    public Incidente(String titulo, String prioridad) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.prioridad = prioridad;
        this.timestamp = LocalDateTime.now();
    }

    public int getPrioridadNumero() {
        switch (prioridad.toUpperCase()) {
            case "ALTA": return 3;
            case "MEDIA": return 2;
            case "BAJA": return 1;
            default: return 0;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "ID: " + id + " | " + titulo + " | Prioridad: " + prioridad + " | Fecha: " + timestamp.format(formatter);
    }
}