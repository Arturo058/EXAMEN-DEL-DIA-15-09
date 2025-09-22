package incidencias;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MenuIncidencias {
    private MaxHeap heap;
    private Scanner sc;

    public MenuIncidencias() {
        heap = new MaxHeap();
        sc = new Scanner(System.in);
    }

    public MenuIncidencias(MaxHeap heapExistente) {
        this.heap = heapExistente;  
        sc = new Scanner(System.in);
    }

    public void iniciar() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== GESTIÓN DE INCIDENCIAS =====");
            System.out.println("1. Insertar incidencia");
            System.out.println("2. Atender siguiente incidencia (máxima prioridad)");
            System.out.println("3. Ver próxima incidencia (sin atender)");
            System.out.println("4. Cambiar prioridad de incidencia");
            System.out.println("5. Listar todas las incidencias");
            System.out.println("6. Construir heap desde lista");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");

            int opt;
            try {
                opt = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("¡Opción no válida! Ingresa un número.");
                continue;
            }

            switch (opt) {
                case 1:
                    insertarIncidencia();
                    break;
                case 2:
                    atenderIncidencia();
                    break;
                case 3:
                    verProximaIncidencia();
                    break;
                case 4:
                    cambiarPrioridad();
                    break;
                case 5:
                    listarIncidencias();
                    break;
                case 6:
                    construirHeap();
                    break;
                case 0:
                    exit = true;
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void insertarIncidencia() {
        System.out.print("Título de la incidencia: ");
        String titulo = sc.nextLine();
        
        String prioridad;
        while (true) {
            System.out.print("Prioridad (Alta/Media/Baja): ");
            prioridad = sc.nextLine().toUpperCase();
            if (prioridad.equals("ALTA") || prioridad.equals("MEDIA") || prioridad.equals("BAJA")) {
                break;
            }
            System.out.println("Prioridad no válida. Use: Alta, Media o Baja");
        }

        heap.insert(new Incidente(titulo, prioridad));
        System.out.println("Incidencia agregada exitosamente.");
    }

    private void atenderIncidencia() {
        Incidente atendida = heap.extractMax();
        if (atendida != null) {
            System.out.println("Incidencia atendida: " + atendida);
        } else {
            System.out.println("No hay incidencias pendientes.");
        }
    }

    private void verProximaIncidencia() {
        Incidente proxima = heap.peek();
        if (proxima != null) {
            System.out.println("Próxima incidencia a atender: " + proxima);
        } else {
            System.out.println("No hay incidencias pendientes.");
        }
    }

    private void cambiarPrioridad() {
        // Primero mostrar todas las incidencias con sus IDs
        List<Incidente> incidencias = heap.getAll();
        if (incidencias.isEmpty()) {
            System.out.println("No hay incidencias registradas.");
            return;
        }
        
        System.out.println("\n=== INCIDENCIAS ACTUALES ===");
        for (Incidente inc : incidencias) {
            System.out.println(inc);
        }
        
        System.out.print("\nID de la incidencia a modificar: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            
            // Verificar que el ID exista
            boolean idExiste = false;
            for (Incidente inc : incidencias) {
                if (inc.id == id) {
                    idExiste = true;
                    break;
                }
            }
            
            if (!idExiste) {
                System.out.println("Error: No existe una incidencia con ID " + id);
                return;
            }
            
            String nuevaPrioridad;
            while (true) {
                System.out.print("Nueva prioridad (Alta/Media/Baja): ");
                nuevaPrioridad = sc.nextLine().toUpperCase();
                if (nuevaPrioridad.equals("ALTA") || nuevaPrioridad.equals("MEDIA") || nuevaPrioridad.equals("BAJA")) {
                    break;
                }
                System.out.println("Prioridad no válida. Use: Alta, Media o Baja");
            }
            
            heap.changePriority(id, nuevaPrioridad);
            System.out.println("Prioridad actualizada exitosamente.");
            
        } catch (NumberFormatException e) {
            System.out.println("Error: ID debe ser un número válido.");
        }
    }

    private void listarIncidencias() {
        List<Incidente> incidencias = heap.getAll();
        if (incidencias.isEmpty()) {
            System.out.println("No hay incidencias registradas.");
        } else {
            System.out.println("\n=== LISTA DE INCIDENCIAS ===");
            for (Incidente inc : incidencias) {
                System.out.println(inc);
            }
            System.out.println("Total: " + incidencias.size() + " incidencias");
        }
    }

    private void construirHeap() {
        System.out.println("=== CONSTRUIR HEAP DESDE LISTA ===");
        System.out.println("Ingrese las incidencias en formato: Título,Prioridad");
        System.out.println("Ejemplo: Fuga de agua,Alta");
        System.out.println("Escriba 'fin' para terminar");
        
        List<Incidente> listaIncidencias = new ArrayList<>();
        
        while (true) {
            System.out.print("Incidencia: ");
            String input = sc.nextLine();
            
            if (input.equalsIgnoreCase("fin")) {
                break;
            }
            
            String[] partes = input.split(",");
            if (partes.length != 2) {
                System.out.println("Formato incorrecto. Use: Título,Prioridad");
                continue;
            }
            
            String titulo = partes[0].trim();
            String prioridad = partes[1].trim().toUpperCase();
            
            if (!prioridad.equals("ALTA") && !prioridad.equals("MEDIA") && !prioridad.equals("BAJA")) {
                System.out.println("Prioridad debe ser: Alta, Media o Baja");
                continue;
            }
            
            listaIncidencias.add(new Incidente(titulo, prioridad));
            System.out.println("Incidencia agregada a la lista temporal.");
        }
        
        if (!listaIncidencias.isEmpty()) {
            heap.buildHeap(listaIncidencias);
            System.out.println("Heap construido con " + listaIncidencias.size() + " incidencias.");
        } else {
            System.out.println("No se agregaron incidencias. Heap sin cambios.");
        }
    }
}