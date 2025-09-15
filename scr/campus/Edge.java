package campus;

// Edge.java
public class Edge {
    public final String from;
    public final String to;
    public final int weight;

    public Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        // Versión final: muestra ambos nodos y el peso
        return "(" + from + "-" + to + ", w=" + weight + ")";
    }
}
