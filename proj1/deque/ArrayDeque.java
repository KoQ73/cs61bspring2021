package deque;

import edu.princeton.cs.algs4.StdOut;

public class ArrayDeque<Item> {
    private Item[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int ARRAY_SIZE = 8;
    private static final int RFACTOR = 2;

    /**
     * Creates an empty ArrayDeque and nextFirst which will determine what position the addFirst method will use to input item same as nextLast for addLast.
     */
    public ArrayDeque() {
        items = (Item[]) new Object[ARRAY_SIZE];
        size = 0;
        nextFirst = ARRAY_SIZE / 2;
        nextLast = nextFirst + 1;
    }

    /**
     * Add the item at the nextFront position and revert it back to the end of the array if nextFirst already reached zero and resize the array if needed.
     * @param item Item to be added at the nextFront position.
     */
    public void addFirst(Item item) {
        if (size == items.length) {
            resize(items.length * RFACTOR);
        }
        // Moves to the left of the array
        // Check if it at the left end of the array and move it to the right end
        if (nextFirst < 0) {
            nextFirst = ARRAY_SIZE - 1;
        }
        items[nextFirst] = item;
        nextFirst -= 1;
        size += 1;
    }

    /**
     * Add the item at the nextLast position and revert it back to the front of the array if nextLast already reached the end and resize the array if needed.
     * @param item Item to be added at the nextFront position.
     */
    public void addLast(Item item) {
        if (size == items.length) {
            resize(items.length * RFACTOR);
        }
        // Moves to the right of the array
        // Check if it at the right end of the array and move it to the left end
        if (nextLast > items.length - 1) {
            nextLast = 0;
        }
        items[nextLast] = item;
        nextLast = nextLast + 1;
        size += 1;
    }

    public void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        int start = nextFirst + 1;
        int itemsToEnd = items.length - start;
        int leftItems = items.length - itemsToEnd;
        System.arraycopy(items, start, a, 0, itemsToEnd);
        System.arraycopy(items, 0, a, itemsToEnd, leftItems);
        nextLast = size;
        items = a;
        nextFirst = items.length - 1;
    }

    /**
     * Returns whether the array is empty or not.
     * @return If no item in the array, the size will be zero.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the array and not the size of the actual array.
     * @return Size.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        int walker = nextFirst + 1;
        // Always goes from left to right in any cases in the array
        for(int i = 1; i < size; i++) {
            // if it at the right end of the array move it back to the front
            if (walker > items.length - 1) {
                walker = 0;
            }
            System.out.print(items[walker] + " ");
            walker += 1;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return The item that has been removed from the front.
     */
    public Item removeFirst() {
        // if the array is empty just return null
        if (isEmpty()) {
            return null;
        }
        // Move the nextFirst to nextFirst + 1;
        nextFirst = nextFirst + 1;
        // If nextFirst is at the end of the array, move it to zero.
        if (nextFirst > items.length - 1) {
            nextFirst = 0;
        }
        // Remove the item
        Item r = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return r;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return The item that has been removed from the end.
     */
    public Item removeLast() {
        // if the array is empty just return null
        if (isEmpty()) {
            return null;
        }
        nextLast = nextLast - 1;
        if (nextLast < 0) {
            nextLast = items.length - 1;
        }
        Item r = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return r;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null.
     * @param index The index to get.
     * @return      The item at index in array items.
     */
    public Item get(int index) {
        if (index > size - 1 || index < 0 || isEmpty()) {
            return null;
        }
        int start = nextFirst + 1;
        int i = start + index;
        i = i % items.length;
        Item r = items[i];
        return r;
    }
}
