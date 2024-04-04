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
        Node oldFirst = sentinel.next;
        sentinel.next = new Node(i, oldFirst, sentinel);
        oldFirst.prev = sentinel.next;
        size += 1;
    }

    /**
     * Add the item to sentinel.prev because the last item will always ends there.
     * @param i item to be added
     */
    public void addLast(Item i) {
        Node oldLast = sentinel.prev;
        sentinel.prev = new Node(i, sentinel, oldLast);
        oldLast.next = sentinel.prev;
        size += 1;
    }

    /**
     * Check whether the deque is empty or not.
     * @return  True if the deque is empty or false otherwise.
     */
    public boolean isEmpty() {
       return sentinel.next == sentinel;
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
        Node walker = sentinel.next;
        while (walker != sentinel) {
            System.out.print(walker.item + " ");
            walker = walker.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return First item if it exists or null otherwise.
     */
    public Item removeFirst() {
        Node first = sentinel.next;
        sentinel.next = first.next;
        sentinel.next.prev = sentinel;
        if (size > 0) {
            size -= 1;
        }
        return first.item;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return Last item if it exists or null otherwise.
     */
    public Item removeLast() {
        Node last = sentinel.prev;
        sentinel.prev = last.prev;
        sentinel.prev.next = sentinel;
        if (size > 0) {
            size -= 1;
        }
        return last.item;
    }

    /**
     * Gets the item at the given index. If no such item exists, returns null.
     * @param index First item is sentinel.next and the 1 is the next item, and so forth.
     * @return The item at the index.
     */
    public Item get(int index) {
        if (index < 0) {
            return null;
        }
        Node walker = sentinel.next;
        int count = 0;
        while (walker != sentinel) {
            if (index == count) {
                return walker.item;
            }
            walker = walker.next;
            count++;
        }
        return null;
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

    public static void main(String[] args) {
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        l.addFirst(10);
        l.addLast(20);
        l.addFirst(1);
        l.addLast(30);
        l.addLast(40);
        System.out.println(l.size());
        l.printDeque();
        l.removeFirst();
        l.printDeque();
        l.removeLast();
        l.printDeque();
        System.out.println(l.get(0));
        System.out.println(l.get(1));
        System.out.println(l.get(2));
        System.out.println(l.get(3));
        System.out.println(l.getRecursive(0));
        System.out.println(l.getRecursive(1));
        System.out.println(l.getRecursive(2));
        System.out.println(l.getRecursive(3));
    }

}
