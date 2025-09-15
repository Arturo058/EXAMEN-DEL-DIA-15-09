package campus;
// DSU.java
import java.util.HashMap;
import java.util.Map;

public class DSU {
    private final Map<String, String> parent;

    public DSU() {
        parent = new HashMap<>();
    }

    public void make(String x) {
        parent.put(x, x);
    }

    public String find(String x) {
        if (!parent.get(x).equals(x)) {
            parent.put(x, find(parent.get(x)));
        }
        return parent.get(x);
    }

    public void union(String x, String y) {
        String px = find(x);
        String py = find(y);
        if (!px.equals(py)) {
            parent.put(px, py);
        }
    }
}
