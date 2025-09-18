package org.itmo.fuzzing.lect1;

public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    static class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        boolean color;

        Node(T value, boolean color) {
            this.value = value;
            this.color = color;
        }
    }

    public boolean repOK() {
        assert rootHasNoParent();
        assert rootIsBlack();
        assert rootNodesHaveOnlyBlackChildren();
        assert treeIsAcyclic();
        assert parentsAreConsistent();
        return true;
    }

    private boolean rootHasNoParent() {
        return root == null || root.parent == null;
    }

    private boolean rootIsBlack() {
        if (root != null && root.parent == null) {
            assert root.color == BLACK : "Root must be black";
        }
        return true;
    }

    private boolean rootNodesHaveOnlyBlackChildren() {
        // Заглушка – здесь должна быть проверка
        return true;
    }

    private boolean treeIsAcyclic() {
        // Заглушка – здесь должна быть проверка
        return true;
    }

    private boolean parentsAreConsistent() {
        // Заглушка – здесь должна быть проверка
        return true;
    }

    public void addElement(T elem) {
        assert repOK();
        // TODO: логика добавления
        assert repOK();
    }

    public void deleteElement(T elem) {
        assert repOK();
        // TODO: логика удаления
        assert repOK();
    }
}

