package estudiantes;

import java.util.*;

public class AVLTree {
    private NodoAVL root;

    public void insert(Estudiante e) {
        root = insert(root, e);
    }

    private NodoAVL insert(NodoAVL node, Estudiante e) {
        if (node == null) return new NodoAVL(e);

        if (e.id < node.data.id) node.left = insert(node.left, e);
        else if (e.id > node.data.id) node.right = insert(node.right, e);
        else return node; // duplicado

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Rotaciones AVL
        if (balance > 1 && e.id < node.left.data.id) return rightRotate(node);
        if (balance < -1 && e.id > node.right.data.id) return leftRotate(node);
        if (balance > 1 && e.id > node.left.data.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && e.id < node.right.data.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void delete(int id) {
        root = delete(root, id);
    }

    private NodoAVL delete(NodoAVL node, int id) {
        if (node == null) return node;

        if (id < node.data.id) node.left = delete(node.left, id);
        else if (id > node.data.id) node.right = delete(node.right, id);
        else {
            if (node.left == null || node.right == null) {
                NodoAVL temp = (node.left != null) ? node.left : node.right;
                if (temp == null) return null;
                else node = temp;
            } else {
                NodoAVL temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = delete(node.right, temp.data.id);
            }
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Rotaciones AVL
        if (balance > 1 && getBalance(node.left) >= 0) return rightRotate(node);
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) return leftRotate(node);
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private NodoAVL minValueNode(NodoAVL node) {
        NodoAVL current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    public Estudiante searchById(int id) {
        NodoAVL node = root;
        while (node != null) {
            if (id == node.data.id) return node.data;
            node = (id < node.data.id) ? node.left : node.right;
        }
        return null;
    }

    public List<Estudiante> searchByName(String nombre) {
        List<Estudiante> res = new ArrayList<>();
        searchByName(root, nombre.toLowerCase(), res);
        return res;
    }

    private void searchByName(NodoAVL node, String nombre, List<Estudiante> res) {
        if (node == null) return;
        if (node.data.nombre.toLowerCase().contains(nombre)) res.add(node.data);
        searchByName(node.left, nombre, res);
        searchByName(node.right, nombre, res);
    }

    public List<Estudiante> inOrder() {
        List<Estudiante> res = new ArrayList<>();
        inOrder(root, res);
        return res;
    }

    private void inOrder(NodoAVL node, List<Estudiante> res) {
        if (node == null) return;
        inOrder(node.left, res);
        res.add(node.data);
        inOrder(node.right, res);
    }

    private int height(NodoAVL node) { return (node == null) ? 0 : node.height; }
    private int getBalance(NodoAVL node) { return (node == null) ? 0 : height(node.left) - height(node.right); }
    private NodoAVL rightRotate(NodoAVL y) {
        NodoAVL x = y.left;
        NodoAVL T2 = x.right;
        x.right = y; y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }
    private NodoAVL leftRotate(NodoAVL x) {
        NodoAVL y = x.right;
        NodoAVL T2 = y.left;
        y.left = x; x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }
}
