COMPLEJIDAD DE ALGORITMOS - MÓDULO GRAFO (1.1)
===================================================

ALGORITMOS IMPLEMENTADOS:

1. BFS (Breadth-First Search)

Complejidad: O(V + E)

V = número de vértices (edificios)

E = número de aristas (pasillos)

Visita cada vértice y arista exactamente una vez

2. DFS (Depth-First Search)

Complejidad: O(V + E)

Misma complejidad que BFS, diferente orden de visita

3. DIJKSTRA (Ruta más corta)

Complejidad: O((V + E) log V)

Implementado con PriorityQueue (heap binario)

Cada operación de extracción: O(log V)

V extracciones + E actualizaciones

4. KRUSKAL (MST - Árbol de expansión mínima)

Complejidad: O(E log E)

Ordenar aristas: O(E log E)

Operaciones Union-Find: O(E α(V))

α = función inversa de Ackermann (casi constante)

5. VERIFICACIÓN DE CONECTIVIDAD

Complejidad: O(V + E)

Utiliza BFS internamente

Detecta si el grafo es conexo o disconexo