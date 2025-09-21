package estudiantes;

import java.util.*;

public class MenuEstudiantes {

    private AVLTree tree;
    private Scanner sc;
    private ContextoOrdenamiento contexto;

    public MenuEstudiantes() {
        tree = new AVLTree();
        sc = new Scanner(System.in);
        contexto = new ContextoOrdenamiento();
    }

    public void iniciar() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== GESTIÓN DE ESTUDIANTES =====");
            System.out.println("1. Alta de estudiante");
            System.out.println("2. Baja de estudiante");
            System.out.println("3. Buscar estudiante por ID");
            System.out.println("4. Buscar estudiante por nombre");
            System.out.println("5. Ordenar estudiantes");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");

            String input = sc.nextLine();
            int opt;
            try { 
                opt = Integer.parseInt(input); 
            } catch (NumberFormatException e) { 
                System.out.println("Opción no válida."); 
                continue; 
            }

            switch (opt) {
                case 1: // ALTA
                    altaEstudiante();
                    break;

                case 2: // BAJA
                    bajaEstudiante();
                    break;

                case 3: // BUSCAR POR ID
                    buscarPorId();
                    break;

                case 4: // BUSCAR POR NOMBRE
                    buscarPorNombre();
                    break;

                case 5: // ORDENAR
                    ordenarEstudiantes();
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

    private void altaEstudiante() {
        System.out.println("\n=== ALTA DE ESTUDIANTE ===");
        
        int id;
        while (true) {
            System.out.print("ID: ");
            String s = sc.nextLine();
            try { 
                id = Integer.parseInt(s); 
                break; 
            } catch (NumberFormatException e) { 
                System.out.println("❌ Error: Debes ingresar un número entero válido para ID."); 
            }
        }

        String nombre;
        while (true) {
            System.out.print("Nombre: "); 
            nombre = sc.nextLine().trim();
            
            if (nombre.isEmpty()) {
                System.out.println("❌ Error: El nombre no puede estar vacío.");
                continue;
            }
            
            if (!contieneSoloLetrasYEspacios(nombre)) {
                System.out.println("❌ Error: El nombre solo puede contener letras y espacios. No se permiten números ni caracteres especiales.");
                continue;
            }
            
            break;
        }

        String carrera;
        while (true) {
            System.out.print("Carrera: "); 
            carrera = sc.nextLine().trim();
            
            if (carrera.isEmpty()) {
                System.out.println("❌ Error: La carrera no puede estar vacía.");
                continue;
            }
            
            if (!contieneSoloLetrasYEspacios(carrera)) {
                System.out.println("❌ Error: La carrera solo puede contener letras y espacios. No se permiten números ni caracteres especiales.");
                continue;
            }
            
            break;
        }

        double promedio;
        while (true) {
            System.out.print("Promedio: ");
            String s = sc.nextLine();
            try { 
                promedio = Double.parseDouble(s); 
                if (promedio < 0 || promedio > 10) {
                    System.out.println("❌ Error: El promedio debe estar entre 0 y 10.");
                    continue;
                }
                break; 
            } catch (NumberFormatException e) { 
                System.out.println("❌ Error: Debes ingresar un número válido para promedio (ej: 8.5)."); 
            }
        }

        tree.insert(new Estudiante(id, nombre, carrera, promedio));
        System.out.println("✅ Estudiante agregado exitosamente.");
    }

    private void bajaEstudiante() {
        System.out.println("\n=== BAJA DE ESTUDIANTE ===");
        
        int delId;
        while (true) {
            System.out.print("ID del estudiante a eliminar: ");
            String s = sc.nextLine();
            try { 
                delId = Integer.parseInt(s); 
                break; 
            } catch (NumberFormatException e) { 
                System.out.println("❌ Error: Debes ingresar un número entero válido para ID."); 
            }
        }
        
        if (tree.searchById(delId) != null) {
            tree.delete(delId);
            System.out.println("✅ Estudiante eliminado.");
        } else {
            System.out.println("❌ Estudiante no encontrado.");
        }
    }

    private void buscarPorId() {
        System.out.println("\n=== BUSCAR POR ID ===");
        
        int searchId;
        while (true) {
            System.out.print("ID a buscar: ");
            String s = sc.nextLine();
            try { 
                searchId = Integer.parseInt(s); 
                break; 
            } catch (NumberFormatException e) { 
                System.out.println("❌ Error: Debes ingresar un número entero válido para ID."); 
            }
        }
        
        Estudiante e = tree.searchById(searchId);
        System.out.println((e != null) ? "✅ " + e : "❌ Estudiante no encontrado.");
    }

    private void buscarPorNombre() {
        System.out.println("\n=== BUSCAR POR NOMBRE ===");
        
        System.out.print("Nombre a buscar: "); 
        String searchName = sc.nextLine();
        List<Estudiante> found = tree.searchByName(searchName);
        
        if (found.isEmpty()) {
            System.out.println("❌ No se encontraron estudiantes.");
        } else {
            System.out.println("✅ Estudiantes encontrados:");
            found.forEach(System.out::println);
        }
    }

    private void ordenarEstudiantes() {
        System.out.println("\n=== ORDENAR ESTUDIANTES ===");
        
        List<Estudiante> lista = tree.inOrder();
        if (lista.isEmpty()) {
            System.out.println("❌ No hay estudiantes para ordenar.");
            return;
        }

        // Mostrar estudiantes actuales
        System.out.println("📋 Estudiantes actuales:");
        for (int i = 0; i < lista.size(); i++) {
            Estudiante e = lista.get(i);
            System.out.println((i + 1) + ". ID: " + e.id + ", Nombre: " + e.nombre + 
                              ", Carrera: " + e.carrera + ", Promedio: " + e.promedio);
        }

        // Elegir campo
        System.out.println("\n¿Por qué campo quieres ordenar?");
        System.out.println("1. ID (número de identificación)");
        System.out.println("2. NOMBRE (apellidos y nombres)");
        System.out.println("3. PROMEDIO (calificación académica)");
        System.out.print("Elige 1, 2 o 3: ");

        int opcionCampo;
        while (true) {
            String inputCampo = sc.nextLine();
            try {
                opcionCampo = Integer.parseInt(inputCampo);
                if (opcionCampo < 1 || opcionCampo > 3) {
                    System.out.print("❌ Error: Solo 1, 2 o 3. Elige nuevamente: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("❌ Error: Debes ingresar un número (1, 2 o 3). Intenta de nuevo: ");
            }
        }

        String campo = "";
        String tipoDato = "";
        switch (opcionCampo) {
            case 1: 
                campo = "id";
                tipoDato = "NÚMERO (ID del estudiante)";
                break;
            case 2: 
                campo = "nombre";
                tipoDato = "TEXTO (apellidos y nombres)";
                break;
            case 3: 
                campo = "promedio";
                tipoDato = "NÚMERO (calificación de 0 a 10)";
                break;
        }

        System.out.println("\nHas elegido ordenar por: " + campo.toUpperCase() + " - " + tipoDato);

        // Elegir algoritmo
        System.out.println("\n¿Qué algoritmo de ordenamiento quieres usar?");
        System.out.println("1. INSERCIÓN - Bueno para listas pequeñas");
        System.out.println("2. SELECCIÓN - Simple pero lento");
        System.out.println("3. SHELL - Mejora de inserción con brechas");
        System.out.println("4. QUICKSORT - Rápido en la práctica");
        System.out.println("5. MERGESORT - Estable y consistente");
        System.out.println("6. HEAPSORT - Usa estructura de montículo");
        
        int maxOpcion = 6;
        if (campo.equals("id")) {
            System.out.println("7. RADIX - Solo para números (ID) - Más rápido para números");
            maxOpcion = 7;
        } else {
            System.out.println("7. RADIX - ❌ NO DISPONIBLE (solo para ID)");
        }
        
        System.out.print("Elige del 1 al " + maxOpcion + ": ");

        int alg;
        while (true) {
            String inputAlg = sc.nextLine();
            try {
                alg = Integer.parseInt(inputAlg);
                
                if (alg < 1 || alg > maxOpcion) {
                    System.out.print("❌ Error: Solo del 1 al " + maxOpcion + ". Elige nuevamente: ");
                    continue;
                }
                
                if (alg == 7 && !campo.equals("id")) {
                    System.out.print("❌ Error: Radix solo funciona con ID. Elige otro algoritmo (1-6): ");
                    continue;
                }
                
                break;
            } catch (NumberFormatException e) {
                System.out.print("❌ Error: Debes ingresar un número. Intenta de nuevo: ");
            }
        }

        // Confirmación
        System.out.println("\n🔹 **PROCESO A REALIZAR:**");
        System.out.println("1. Se tomarán los " + lista.size() + " estudiantes actuales");
        System.out.println("2. Se ordenarán por: " + campo.toUpperCase());
        System.out.println("3. Usando algoritmo: " + obtenerNombreAlgoritmo(alg));
        System.out.println("4. El resultado se mostrará en orden " + 
                          (campo.equals("nombre") ? "alfabético" : "numérico"));
        
        System.out.print("\n¿Continuar? (s/n): ");
        String confirmacion = sc.nextLine();
        if (!confirmacion.equalsIgnoreCase("s")) {
            System.out.println("❌ Ordenamiento cancelado.");
            return;
        }

        // Aplicar el algoritmo
        switch (alg) {
            case 1: contexto.setStrategy(new InsercionStrategy()); break;
            case 2: contexto.setStrategy(new SeleccionStrategy()); break;
            case 3: contexto.setStrategy(new ShellStrategy()); break;
            case 4: contexto.setStrategy(new QuickSortStrategy()); break;
            case 5: contexto.setStrategy(new MergeSortStrategy()); break;
            case 6: contexto.setStrategy(new HeapSortStrategy()); break;
            case 7: contexto.setStrategy(new RadixStrategy()); break;
        }

        System.out.println("\n⏳ Ordenando " + lista.size() + " estudiantes...");
        long startTime = System.nanoTime();
        contexto.ordenar(lista, campo);
        long endTime = System.nanoTime();
        long durationMs = (endTime - startTime) / 1000000;

        System.out.println("✅ ¡Ordenamiento completado en " + durationMs + " milisegundos!");
        System.out.println("\n📋 RESULTADO ORDENADO POR " + campo.toUpperCase() + ":");
        System.out.println("==========================================");
        
        for (int i = 0; i < lista.size(); i++) {
            Estudiante est = lista.get(i);
            String numero = String.format("%2d", i + 1) + ".";
            
            if (campo.equals("id")) {
                System.out.println(numero + " ID: " + est.id + " - " + est.nombre + " - Promedio: " + est.promedio);
            } else if (campo.equals("nombre")) {
                System.out.println(numero + " " + est.nombre + " - ID: " + est.id + " - Promedio: " + est.promedio);
            } else {
                System.out.println(numero + " Promedio: " + est.promedio + " - " + est.nombre + " - ID: " + est.id);
            }
        }
        
        System.out.println("==========================================");
    }

    // Método auxiliar para validar que solo contiene letras y espacios
    private boolean contieneSoloLetrasYEspacios(String texto) {
        for (char c : texto.toCharArray()) {
            // Permitir letras (mayúsculas y minúsculas), espacios y letras con acentos
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    private String obtenerNombreAlgoritmo(int alg) {
        switch (alg) {
            case 1: return "INSERCIÓN";
            case 2: return "SELECCIÓN";
            case 3: return "SHELL";
            case 4: return "QUICKSORT";
            case 5: return "MERGESORT";
            case 6: return "HEAPSORT";
            case 7: return "RADIX";
            default: return "DESCONOCIDO";
        }
    }
}