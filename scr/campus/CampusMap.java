package campus;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class CampusMap {

    private Graph g;
    private Scanner sc;

    
    public CampusMap() {
        g = new Graph();
        sc = new Scanner(System.in);
    }

    public CampusMap(Graph grafoExistente) {
        this.g = grafoExistente;  
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== CAMPUS NAVIGATOR =====");
            System.out.println("1. Cargar grafo de ejemplo");
            System.out.println("2. Crear grafo personalizado");
            System.out.println("3. Mostrar lista de adyacencia");
            System.out.println("4. Verificar si el grafo es disconexo");
            System.out.println("5. BFS desde un edificio");
            System.out.println("6. DFS desde un edificio");
            System.out.println("7. Dijkstra (ruta mínima entre edificios)");
            System.out.println("8. MST (Kruskal)");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");

            String input = sc.nextLine();
            int opt;
            try {
                opt = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("¡Opción no válida! Ingresa un número del menú.");
                continue;
            }

            switch (opt) {
                case 1:
                    cargarGrafoEjemplo(g);
                    System.out.println("Grafo cargado exitosamente.");
                    System.out.println("Edificios disponibles: " + g.adj.keySet());
                    break;

                case 2:
                    crearGrafoPersonalizado();
                    break;

                case 3:
                    mostrarListaAdyacencia();
                    break;

                case 4:
                    verificarGrafoDisconexo();
                    break;

                case 5:
                    if (g.adj.isEmpty()) {
                        System.out.println("¡El grafo está vacío! Carga primero el grafo.");
                        break;
                    }

                    String startBFS;
                    while (true) {
                        System.out.print("Ingresa edificio de inicio BFS: ");
                        startBFS = sc.nextLine().toUpperCase();
                        if (g.adj.containsKey(startBFS)) break;
                        System.out.println("¡Edificio no válido! Debes ingresar uno de los salones existentes: " + g.adj.keySet());
                    }
                    List<String> bfs = g.bfs(startBFS);
                    System.out.println("BFS desde " + startBFS + ": " + bfs);
                    break;

                case 6:
                    if (g.adj.isEmpty()) {
                        System.out.println("¡El grafo está vacío! Carga primero el grafo.");
                        break;
                    }

                    String startDFS;
                    while (true) {
                        System.out.print("Ingresa edificio de inicio DFS: ");
                        startDFS = sc.nextLine().toUpperCase();
                        if (g.adj.containsKey(startDFS)) break;
                        System.out.println("¡Edificio no válido! Debes ingresar uno de los salones existentes: " + g.adj.keySet());
                    }
                    List<String> dfs = g.dfs(startDFS);
                    System.out.println("DFS desde " + startDFS + ": " + dfs);
                    break;

                case 7:
                    if (g.adj.isEmpty()) {
                        System.out.println("¡El grafo está vacío! Carga primero el grafo.");
                        break;
                    }

                    String src, dest;
                    while (true) {
                        System.out.print("Edificio de inicio: ");
                        src = sc.nextLine().toUpperCase();
                        System.out.print("Edificio destino: ");
                        dest = sc.nextLine().toUpperCase();
                        if (g.adj.containsKey(src) && g.adj.containsKey(dest)) break;
                        System.out.println("¡Edificio(s) no válidos! Edificios disponibles: " + g.adj.keySet());
                    }

                    Graph.PathResult dr = g.dijkstra(src, dest);
                    if (dr.exists) {
                        System.out.println("Camino: " + dr.path);
                        System.out.println("Costo total: " + dr.distance);
                    } else {
                        System.out.println("No hay ruta entre " + src + " y " + dest);
                    }
                    break;

                case 8:
                    if (g.adj.isEmpty()) {
                        System.out.println("¡El grafo está vacío! Carga primero el grafo.");
                        break;
                    }
                    Graph.MSTResult mst = g.kruskalMST();
                    System.out.println("MST (Kruskal):");
                    System.out.println("Aristas: " + mst.edges);
                    System.out.println("Costo total: " + mst.totalCost);
                    break;

                case 0:
                    exit = true;
                    System.out.println("Volviendo al menú principal...");
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private void cargarGrafoEjemplo(Graph g) {
        // Limpiar grafo existente primero
        g.adj.clear();
        
        for (String node : new String[]{"A","B","C","D","E","F","G"}) 
            g.addBuilding(node);

        g.addPassage("A","B",2);
        g.addPassage("A","C",3);
        g.addPassage("B","D",4);
        g.addPassage("C","E",5);
        g.addPassage("D","F",6);
        g.addPassage("E","F",1);
        g.addPassage("F","G",2);
    }

    private void crearGrafoPersonalizado() {
        // Limpiar grafo existente
        g.adj.clear();
        
        System.out.println("\n--- CREAR GRAFO PERSONALIZADO ---");
        
        // Agregar edificios
        System.out.println("Ingrese los nombres de los edificios (separados por comas):");
        System.out.println("Ejemplo: A,B,C,D");
        String edificiosInput = sc.nextLine();
        String[] edificios = edificiosInput.split(",");
        
        for (String edificio : edificios) {
            String edificioLimpio = edificio.trim().toUpperCase();
            if (!edificioLimpio.isEmpty()) {
                g.addBuilding(edificioLimpio);
            }
        }
        
        System.out.println("Edificios agregados: " + g.adj.keySet());
        
        // Agregar conexiones
        System.out.println("\nAhora agregue las conexiones entre edificios.");
        System.out.println("Ingrese 'fin' para terminar.");
        
        while (true) {
            System.out.print("\nIngrese conexión (formato: origen,destino,peso): ");
            String conexionInput = sc.nextLine();
            
            if (conexionInput.equalsIgnoreCase("fin")) {
                break;
            }
            
            String[] partes = conexionInput.split(",");
            if (partes.length != 3) {
                System.out.println("Formato incorrecto. Use: origen,destino,peso");
                continue;
            }
            
            try {
                String origen = partes[0].trim().toUpperCase();
                String destino = partes[1].trim().toUpperCase();
                int peso = Integer.parseInt(partes[2].trim());
                
                if (!g.adj.containsKey(origen)) {
                    g.addBuilding(origen);
                }
                if (!g.adj.containsKey(destino)) {
                    g.addBuilding(destino);
                }
                
                g.addPassage(origen, destino, peso);
                System.out.println("Conexión agregada: " + origen + " - " + destino + " (peso: " + peso + ")");
                
            } catch (NumberFormatException e) {
                System.out.println("Error: El peso debe ser un número entero.");
            }
        }
        
        System.out.println("Grafo personalizado creado exitosamente!");
        System.out.println("Edificios: " + g.adj.keySet());
    }

    private void mostrarListaAdyacencia() {
        if (g.adj.isEmpty()) {
            System.out.println("¡El grafo está vacío! No hay lista de adyacencia que mostrar.");
            return;
        }
        
        System.out.println("\n--- LISTA DE ADYACENCIA ---");
        g.printAdjacency();
    }

    private void verificarGrafoDisconexo() {
        if (g.adj.isEmpty()) {
            System.out.println("¡El grafo está vacío! No se puede verificar conectividad.");
            return;
        }

        boolean esConectado = g.isConnected();
        
        System.out.println("\n--- VERIFICACIÓN DE CONECTIVIDAD ---");
        if (esConectado) {
            System.out.println("✅ El grafo es CONEXO (todos los edificios están conectados)");
        } else {
            System.out.println("❌ El grafo es DISCONEXO (hay edificios aislados o componentes separados)");
            
            // Mostrar componentes conexos
            System.out.println("\nComponentes conexos encontrados:");
            encontrarComponentesConexos();
        }
    }

    private void encontrarComponentesConexos() {
        Set<String> visitados = new HashSet<>();
        int componenteCount = 1;

        for (String vertice : g.adj.keySet()) {
            if (!visitados.contains(vertice)) {
                // Encontrar todos los nodos conectados a este vértice usando BFS
                List<String> componente = new ArrayList<>();
                Queue<String> cola = new LinkedList<>();
                cola.add(vertice);
                visitados.add(vertice);
                componente.add(vertice);

                while (!cola.isEmpty()) {
                    String actual = cola.poll();
                    for (Edge arista : g.adj.get(actual)) {
                        String vecino = arista.to;
                        if (!visitados.contains(vecino)) {
                            visitados.add(vecino);
                            cola.add(vecino);
                            componente.add(vecino);
                        }
                    }
                }

                System.out.println("Componente " + componenteCount + ": " + componente);
                componenteCount++;
            }
        }
    }
}