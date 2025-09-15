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

    // Método que antes era el main
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
                    int id;
                    while (true) {
                        System.out.print("ID: ");
                        String s = sc.nextLine();
                        try { 
                            id = Integer.parseInt(s); 
                            break; 
                        } catch (NumberFormatException e) { 
                            System.out.println("Debes ingresar un número entero válido para ID."); 
                        }
                    }

                    System.out.print("Nombre: "); 
                    String nombre = sc.nextLine();

                    System.out.print("Carrera: "); 
                    String carrera = sc.nextLine();

                    double promedio;
                    while (true) {
                        System.out.print("Promedio: ");
                        String s = sc.nextLine();
                        try { 
                            promedio = Double.parseDouble(s); 
                            break; 
                        } catch (NumberFormatException e) { 
                            System.out.println("Debes ingresar un número válido para promedio."); 
                        }
                    }

                    tree.insert(new Estudiante(id, nombre, carrera, promedio));
                    System.out.println("Estudiante agregado.");
                    break;

                case 2: // BAJA
                    int delId;
                    while (true) {
                        System.out.print("ID del estudiante a eliminar: ");
                        String s = sc.nextLine();
                        try { 
                            delId = Integer.parseInt(s); 
                            break; 
                        } catch (NumberFormatException e) { 
                            System.out.println("Debes ingresar un número entero válido para ID."); 
                        }
                    }
                    if (tree.searchById(delId) != null) {
                        tree.delete(delId);
                        System.out.println("Estudiante eliminado.");
                    } else System.out.println("Estudiante no encontrado.");
                    break;

                case 3: // BUSCAR POR ID
                    int searchId;
                    while (true) {
                        System.out.print("ID a buscar: ");
                        String s = sc.nextLine();
                        try { 
                            searchId = Integer.parseInt(s); 
                            break; 
                        } catch (NumberFormatException e) { 
                            System.out.println("Debes ingresar un número entero válido para ID."); 
                        }
                    }
                    Estudiante e = tree.searchById(searchId);
                    System.out.println((e != null) ? e : "Estudiante no encontrado.");
                    break;

                case 4: // BUSCAR POR NOMBRE
                    System.out.print("Nombre a buscar: "); 
                    String searchName = sc.nextLine();
                    List<Estudiante> found = tree.searchByName(searchName);
                    if (found.isEmpty()) System.out.println("No se encontraron estudiantes.");
                    else found.forEach(System.out::println);
                    break;

                case 5: // ORDENAR
                    List<Estudiante> lista = tree.inOrder();
                    if (lista.isEmpty()) {
                        System.out.println("No hay estudiantes para ordenar.");
                        break;
                    }

                    int alg;
                    while (true) {
                        System.out.println("Elige algoritmo de ordenamiento:");
                        System.out.println("1. Inserción 2. Selección 3. Shell 4. QuickSort 5. MergeSort 6. HeapSort 7. Radix (solo ID)");
                        String s = sc.nextLine();
                        try { 
                            alg = Integer.parseInt(s); 
                            if (alg < 1 || alg > 7) throw new NumberFormatException();
                            break;
                        } catch (NumberFormatException ex) {
                            System.out.println("Debes ingresar un número entre 1 y 7.");
                        }
                    }

                    System.out.println("Campo para ordenar: id / nombre / promedio");
                    String campo = sc.nextLine().toLowerCase();

                    switch (alg) {
                        case 1: contexto.setStrategy(new InsercionStrategy()); break;
                        case 2: contexto.setStrategy(new SeleccionStrategy()); break;
                        case 3: contexto.setStrategy(new ShellStrategy()); break;
                        case 4: contexto.setStrategy(new QuickSortStrategy()); break;
                        case 5: contexto.setStrategy(new MergeSortStrategy()); break;
                        case 6: contexto.setStrategy(new HeapSortStrategy()); break;
                        case 7: 
                            if (!campo.equals("id")) { 
                                System.out.println("Radix solo aplica para ID."); 
                                continue; 
                            }
                            contexto.setStrategy(new RadixStrategy()); 
                            break;
                        default: 
                            System.out.println("Algoritmo no válido."); 
                            continue;
                    }

                    contexto.ordenar(lista, campo);
                    System.out.println("Lista ordenada:");
                    lista.forEach(System.out::println);
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
}
