package estudiantes;

public class NodoAVL {
    Estudiante data;
    NodoAVL left, right;
    int height;

    public NodoAVL(Estudiante data) {
        this.data = data;
        this.height = 1;
    }
}
