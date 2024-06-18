package deque;

public class LinkedListDeque<Item> {
    /**
     * Node class that have the item and the rest which is null or the next node.
     */
    private class Node {
        public Item item;
        public Node next;
        public Node prev;

        /**
         * Constructor for Double Linked List
         * @param i item to be added
         * @param n next node
         * @param p previous node
         */
        public Node(Item i, Node n, Node p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    public Node sentinel;
    private int size;

    /**
     * Create an empty LinkedListDeque;
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Add the item to sentinel.next because the first item will always start there
     * and also have to update sentinel.prev every time you add an item.
     * @param i item to be added
     */
    public void addFirst(Item i) {
        // Remember the old s.next
        Node oldFirst = sentinel.next;
        // Add current item
        sentinel.next = new Node(i, oldFirst, sentinel);
        // Update the old s.next
        oldFirst.prev = sentinel.next;
        // Update size
        size += 1;
    }

    /**
     * Add the item to sentinel.prev because the last item will always ends there.
     * @param i item to be added
     */
    public void addLast(Item i) {
        // Remember old last
        Node oldLast = sentinel.prev;
        // Add current item
        sentinel.prev = new Node(i, sentinel, oldLast);
        // Update old last
        oldLast.next = sentinel.prev;
        // Update size
        size += 1;
    }

    /**
     * Check whether the deque is empty or not.
     * @return  True if the deque is empty or false otherwise.
     */
    public boolean isEmpty() {
       return sentinel.next == sentinel && sentinel.prev == sentinel;
    }

    /**
     * Returns the size of LinkedListDeque.
     * @return integer representing the size.
     */
    public int size() {
        return size;
    }

    /**
     *  Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        // a walker node to walk through the linked list
        Node walker = sentinel.next;
        // Goes from s.next to s.prev
        while (walker.prev != sentinel.prev) {
            System.out.print(walker.item + " ");
            walker = walker.next;
        }
        // Add a new line
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return First item if it exists or null otherwise.
     */
    public Item removeFirst() {
        // Remember the old first
        Node first = sentinel.next;
        // If there is no first item
        if (first == sentinel) {
            return null;
        }
        // Update the linked list
        sentinel.next = first.next;
        sentinel.next.prev = sentinel;
        // Update size
        size -= 1;
        return first.item;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return Last item if it exists or null otherwise.
     */
    public Item removeLast() {
        Node last = sentinel.prev;
        if (last == sentinel) {
            return null;
        }
        sentinel.prev = last.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return last.item;
    }

    /**
     * Gets the item at the given index. If no such item exists, returns null.
     * @param index First item is sentinel.next and the 1 is the next item, and so forth.
     * @return The item at the index.
     */
    public Item get(int index) {
        // index must not be negative
        if (index < 0) {
            return null;
        }
        // if index is out of range just return null
        if (index > size - 1) {
            return null;
        }
        // if the list is empty just return null
        if (isEmpty()) {
            return null;
        }
        // first and last item optimization
        if (index == 0) {
            return sentinel.next.item;
        }
        if (index == size - 1) {
            return sentinel.prev.item;
        }
        // for other items
        Node walker = sentinel;
        int count = index + 1;
        Item item = null;
        // Iterate till the item
        for (int i = 0; i < count; i++) {
            walker = walker.next;
        }
        item = walker.item;
        return item;
    }
    public Item getRecursive(int index) {
        if (index < 0) {
            return null;
        }
        return helperGetRecursive(sentinel.next, index);
    }

    /**
     * Helper method for getRecursive where the base case is if it reached the index and stops if the last item has been checked.
     * @param walker The node to walk through the entire LinkedListDeque.
     * @param index The index to look for.
     * @return Item at index or null.
     */
    public Item helperGetRecursive(Node walker, int index) {
        if (index == 0) {
            return walker.item;
        }
        else if (walker == sentinel) {
            return null;
        }
        else {
            return helperGetRecursive(walker.next, index - 1);
        }
    }

}
