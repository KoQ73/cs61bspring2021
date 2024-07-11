package bstmap;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B{
    BSTNode root;

    int size;

    private class BSTNode {
        private K key;
        private V value;

        private BSTNode left = null;

        private BSTNode right = null;

        public BSTNode() {
            throw new IllegalArgumentException("Map must contain key and value pair.");
        }

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setValue(V v) {
            value = v;
        }

        public V getValue() {
            return this.value;
        }
    }

    public BSTMap() {
        this.root = null;
        size = 0;
    }

    public BSTMap(K key, V value) {
        root = new BSTNode(key, value);
        size = 1;
    }

    private BSTNode find(BSTNode T, K sk) {
        if (T == null) {
            return null;
        }
        int cmp = sk.compareTo(T.key);
        if (cmp < 0) {
            return find(T.left, sk);
        }
        else if (cmp > 0) {
            return find(T.right, sk);
        }
        else {
            return T;
        }
    }

    private BSTNode insert(BSTNode T,K key, V value) {
        if (T == null) {
            size++;
            BSTNode n = new BSTNode(key, value);
            return n;
        }
        int cmp = key.compareTo(T.key);
        if (cmp < 0) {
            T.left = insert(T.left, key, value);
        }
        else if (cmp > 0) {
            T.right = insert(T.right, key, value);
        }
        return T;
    }

    private BSTNode findMin(BSTNode T) {
        while (T.left != null) {
            T = T.left;
        }
        return T;
    }

    private BSTNode delete(BSTNode T, K key) {
        if (T == null) {
            return null;
        }
        // Hibbard Deletion
        int cmp = key.compareTo(T.key);
        if (cmp < 0) {
            T.left = delete(T.left, key);
        }
        else if (cmp > 0) {
            T.right = delete(T.right, key);
        } else {
            // The toDelete node is a leaf
            if (T.left == null & T.right == null) {
                return null;
            }
            // Node has one child
            if (T.left == null) {
                return T.right;
            }
            if (T.right == null) {
                return T.left;
            }
            // Node has two children
            // Find the smallest value in the right subtree
            BSTNode successor = findMin(T.right);

            // Replace the current key node with successor
            T.key = successor.key;
            T.value = successor.value;

            // Delete the successor node
            T.right = delete(T.right, (K) successor.key);

            size--;
        }
        return T;
    }

    public BSTNode find(K sk) {
        return find(root, sk);
    }

    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    public BSTNode delete(K key) {
        return delete(root, key);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return find(root, (K)key) != null;
    }

    @Override
    public Object get(Object key) {
        BSTNode n = find(root, (K) key);
        if (n == null) {
            return null;
        }
        return n.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        insert((K)key, (V)value);
    }

    @Override
    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder(BSTNode n) {
        if (n == null) {
            return;
        }
        // Print left subtree
        printInOrder(n.left);
        // Print yourself
        System.out.println(n.key.toString() + " -> " + n.value.toString());
        // Print right subtree
        printInOrder(n.right);
    }
}
