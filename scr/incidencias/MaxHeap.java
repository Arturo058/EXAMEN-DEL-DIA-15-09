package incidencias;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap {
    private List<Incidente> heap;
    private int size;

    public MaxHeap() {
        heap = new ArrayList<>();
        size = 0;
    }

    public void insert(Incidente incidente) {
        heap.add(incidente);
        size++;
        heapifyUp(size - 1);
    }

    public Incidente extractMax() {
        if (size == 0) return null;
        
        Incidente max = heap.get(0);
        heap.set(0, heap.get(size - 1));
        heap.remove(size - 1);
        size--;
        heapifyDown(0);
        return max;
    }

    public Incidente peek() {
        return (size > 0) ? heap.get(0) : null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void buildHeap(List<Incidente> incidentes) {
        heap = new ArrayList<>(incidentes);
        size = incidentes.size();
        for (int i = (size / 2) - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    public void changePriority(int id, String nuevaPrioridad) {
        for (int i = 0; i < size; i++) {
            if (heap.get(i).id == id) {
                heap.get(i).prioridad = nuevaPrioridad;
                heapifyUp(i);
                heapifyDown(i);
                return;
            }
        }
        System.out.println("Incidente con ID " + id + " no encontrado.");
    }

    public List<Incidente> getAll() {
        return new ArrayList<>(heap);
    }

    private void heapifyUp(int index) {
        int parent = (index - 1) / 2;
        while (index > 0 && heap.get(parent).getPrioridadNumero() < heap.get(index).getPrioridadNumero()) {
            swap(parent, index);
            index = parent;
            parent = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int largest = index;
        int left = 2 * index + 1;
        int right = 2 * index + 2;

        if (left < size && heap.get(left).getPrioridadNumero() > heap.get(largest).getPrioridadNumero()) {
            largest = left;
        }
        if (right < size && heap.get(right).getPrioridadNumero() > heap.get(largest).getPrioridadNumero()) {
            largest = right;
        }
        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    private void swap(int i, int j) {
        Incidente temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}
