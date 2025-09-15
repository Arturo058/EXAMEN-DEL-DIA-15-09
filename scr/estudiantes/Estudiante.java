package estudiantes;

public class Estudiante {
    int id;
    String nombre;
    String carrera;
    double promedio;

    public Estudiante(int id, String nombre, String carrera, double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.carrera = carrera;
        this.promedio = promedio;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Carrera: " + carrera + ", Promedio: " + promedio;
    }
}
