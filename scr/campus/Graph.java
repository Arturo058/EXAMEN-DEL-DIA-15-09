package campus;

// Graph.java
import java.util.*;

public class Graph {
    final Map<String, List<Edge>> adj;

    public Graph() {
        adj = new HashMap<>();
    }

    // GETTER PÚBLICO AGREGADO:
    public Map<String, List<Edge>> getAdj() {
        return adj;
    }

    /* ===== Operaciones básicas ===== */
    public void addBuilding(String name) {
        adj.putIfAbsent(name, new ArrayList<>());
    }

    public void addPassage(String u, String v, int weight) {
        addBuilding(u);
        addBuilding(v);
        adj.get(u).add(new Edge(u, v, weight));
        adj.get(v).add(new Edge(v, u, weight)); // grafo no dirigido
    }

    // Mostrar todos los nodos, aunque no tengan aristas
    public void printAdjacency() {
        for (String node : adj.keySet()) {
            System.out.print(node + " -> ");
            List<String> outs = new ArrayList<>();
            if (adj.get(node) != null) {
                for (Edge e : adj.get(node)) outs.add(e.toString());
            }
            System.out.println(outs);
        }
    }

    /* ===== BFS / DFS ===== */
    public List<String> bfs(String start) {
        List<String> order = new ArrayList<>();
        if (!adj.containsKey(start)) return order;
        Set<String> visited = new HashSet<>();
        Queue<String> q = new ArrayDeque<>();
        visited.add(start); q.add(start);
        while (!q.isEmpty()) {
            String u = q.poll();
            order.add(u);
            for (Edge e : adj.get(u)) {
                if (!visited.contains(e.to)) {
                    visited.add(e.to);
                    q.add(e.to);
                }
            }
        }
        return order;
    }

    public List<String> dfs(String start) {
        List<String> order = new ArrayList<>();
        if (!adj.containsKey(start)) return order;
        Set<String> visited = new HashSet<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            String u = stack.pop();
            if (visited.contains(u)) continue;
            visited.add(u);
            order.add(u);
            List<Edge> neighbors = adj.get(u);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                String v = neighbors.get(i).to;
                if (!visited.contains(v)) stack.push(v);
            }
        }
        return order;
    }

    /* ===== Conectividad ===== */
    public boolean isConnected() {
        if (adj.isEmpty()) return true;
        String any = adj.keySet().iterator().next();
        List<String> visitedOrder = bfs(any);
        return visitedOrder.size() == adj.size();
    }

    /* ===== Dijkstra ===== */
    public static class PathResult {
        public final boolean exists;
        public final List<String> path;
        public final int distance;
        public PathResult(boolean exists, List<String> path, int distance) {
            this.exists = exists; this.path = path; this.distance = distance;
        }
    }

    public PathResult dijkstra(String src, String target) {
        if (!adj.containsKey(src) || !adj.containsKey(target)) 
            return new PathResult(false, Collections.emptyList(), -1);

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        for (String node : adj.keySet()) dist.put(node, Integer.MAX_VALUE);
        dist.put(src, 0);

        PriorityQueue<Map.Entry<String,Integer>> pq =
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        pq.add(new AbstractMap.SimpleEntry<>(src, 0));

        while (!pq.isEmpty()) {
            Map.Entry<String,Integer> top = pq.poll();
            String u = top.getKey();
            int d = top.getValue();
            if (d > dist.get(u)) continue;
            if (u.equals(target)) break;
            for (Edge e : adj.get(u)) {
                int nd = d + e.weight;
                if (nd < dist.get(e.to)) {
                    dist.put(e.to, nd);
                    prev.put(e.to, u);
                    pq.add(new AbstractMap.SimpleEntry<>(e.to, nd));
                }
            }
        }

        if (dist.get(target) == Integer.MAX_VALUE)
            return new PathResult(false, Collections.emptyList(), -1);

        LinkedList<String> path = new LinkedList<>();
        String cur = target;
        while (cur != null) {
            path.addFirst(cur);
            cur = prev.get(cur);
        }
        return new PathResult(true, path, dist.get(target));
    }

    /* ===== Kruskal (MST) ===== */
    public static class MSTResult {
        public final List<Edge> edges;
        public final int totalCost;
        public MSTResult(List<Edge> edges, int totalCost) { 
            this.edges = edges; this.totalCost = totalCost; 
        }
    }

    public MSTResult kruskalMST() {
        List<Edge> edges = uniqueUndirectedEdges();
        edges.sort(Comparator.comparingInt(e -> e.weight));

        DSU dsu = new DSU();
        for (String node : adj.keySet()) dsu.make(node);

        List<Edge> mstEdges = new ArrayList<>();
        int total = 0;
        for (Edge e : edges) {
            if (dsu.find(e.from).equals(dsu.find(e.to))) continue;
            dsu.union(e.from, e.to);
            mstEdges.add(e);
            total += e.weight;
        }
        return new MSTResult(mstEdges, total);
    }

    private List<Edge> uniqueUndirectedEdges() {
        List<Edge> edges = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        for (Map.Entry<String, List<Edge>> kv : adj.entrySet()) {
            String u = kv.getKey();
            for (Edge e : kv.getValue()) {
                String key = (u.compareTo(e.to) <= 0) ? u + "->" + e.to : e.to + "->" + u;
                String uniqueId = key + "@" + e.weight;
                if (!seen.contains(uniqueId)) {
                    seen.add(uniqueId);
                    edges.add(new Edge(u, e.to, e.weight));
                }
            }
        }
        return edges;
    }
}