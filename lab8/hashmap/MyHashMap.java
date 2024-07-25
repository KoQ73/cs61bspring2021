package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    private int bucketSize = 16;
    private double loadFactor = 0.75;

    private int size = 0;

    private Set<K> keySets = new HashSet<>();

    protected static int initialSize = 16;

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(this.bucketSize);
    }

    public MyHashMap(int bucketSize) {
        this.bucketSize = bucketSize;
        buckets = createTable(this.bucketSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of bucketSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param bucketSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int bucketSize, double maxLoad) {
        this.bucketSize = bucketSize;
        this.loadFactor = maxLoad;
        buckets = createTable(this.bucketSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        // Decide which bucket to put
        int index = gethashCode(key);
        this.keySets.add(key);
        Node n = new Node(key, value);
        buckets[index].add(n);
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket(){
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        // Create an array of empty buckets
        Collection<Node>[] table = new Collection[tableSize];
        // Assign each bucket to a collection
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    // Get the index of the node to be put in the hash table based on its hashcode
    public int gethashCode(K key) {
        int h = key.hashCode();
        h = Math.floorMod(h, this.bucketSize);
        return h;
    }

    @Override
    public void clear() {
        this.buckets = createTable(MyHashMap.initialSize);
        this.bucketSize = MyHashMap.initialSize;
        this.keySets = new HashSet<>();
        this.size = 0;
    }

    public boolean containsKey(K key) {
        // Check the hashCode
        int h = gethashCode(key);
        // Check if the bucket is empty
        if (buckets[h] != null) {
            // Loop through the bucket
            for (Node n : buckets[h]) {
                // Check if the key is equal
                if (n.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    public V get(K key) {
        // Return null if the item doesn't exist
        Node n = getNode(key);
        if (n != null) {
            return n.value;
        }
        return null;
    }

    public Node getNode(K key) {
        // Return null if the item doesn't exist
        if (!containsKey(key)) {
            return null;
        }
        // Check the hashCode
        int h = gethashCode(key);
        // Search the item in the bucket
        for (Node n : buckets[h]) {
            if (n.key.equals(key)) {
                return n;
            }
        }
        return null;
    }

    public int size() {
        return this.size;
    }

    public void resize() {
        // Create a new table with twice the size
        Collection<Node>[] table = createTable(this.bucketSize * 2);
        ArrayList<Node> nodes = new ArrayList<>();
        // Copy the items from the old table
        for (K key: keySets) {
            Node n = getNode(key);
            nodes.add(n);
        }
        this.buckets = table;
        this.bucketSize *= 2;
        this.size = 0;
        // Insert it to the new one
        for (Node n : nodes) {
            this.put(n.key, n.value);
        }
    }

    public void put(K key, V value) {
        // Also check for resizing
        float load_factor = (float) (this.size + 1) /(float) this.bucketSize;    // Consider after adding the item
        if (load_factor > this.loadFactor) {
            // resize the bucket size
            resize();
        }
        // if the key doesn't exist make it a new node
        if (!containsKey(key)) {
            createNode(key, value);
            size += 1;
        }
        // if it does exist update the value
        else {
            Node n = getNode(key);
            n.value = value;
        }
        this.keySets.add(key);
    }

    public Set<K> keySet() {
        return this.keySets;
    }

    public V remove(K key) {
        return null;
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator() {
        return new HashMapIterator();
    }

    private class HashMapIterator implements Iterator<K> {
        private int size;

        private Set<K> keySet;
        public HashMapIterator() {
            this.size = size();
            keySet = new HashSet<>();
            keySet.addAll(keySet());
        }
        @Override
        public boolean hasNext() {
            return this.size != 0;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                return null;
            }
            K key = null;
            for (K k : keySet) {
                key = k;
                break;
            }
            keySet.remove(key);
            size -= 1;
            return key;
        }
    }
}
