package deque;

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<Item> implements Deque<Item>, Iterable<Item> {
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
    @Override
    public void addFirst(Item item) {
        if (size == items.length) {
            resize(items.length * RFACTOR);
        }
        // Moves to the left of the array
        items[nextFirst] = item;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
        size += 1;
    }

    /**
     * Add the item at the nextLast position and revert it back to the front of the array if nextLast already reached the end and resize the array if needed.
     * @param item Item to be added at the nextFront position.
     */
    @Override
    public void addLast(Item item) {
        if (size == items.length) {
            resize(items.length * RFACTOR);
        }
        // Moves to the right of the array
        items[nextLast] = item;
        nextLast = (nextLast + 1) % items.length;
        size += 1;
    }

    public void resize(int capacity) {
        Item[] newItems = (Item[]) new Object[capacity];
        int currentFirst = (nextFirst + 1) % items.length;
        for (int i = 0; i < size; i++) {
            newItems[i] = items[currentFirst];
            currentFirst = (currentFirst + 1) % items.length;
        }
        items = newItems;
        nextFirst = capacity - 1;
        nextLast = size;
    }

    /**
     * Returns the number of items in the array and not the size of the actual array.
     * @return Size.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
     */
    @Override
    public void printDeque() {
        if (isEmpty()) {
            return;
        }
        for (Item i : this) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * @return The item that has been removed from the front.
     */
    @Override
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = (nextFirst + 1) % items.length;
        Item item = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        if (size > 0 && size == items.length / 4 && items.length > ARRAY_SIZE) {
            resize(items.length / 2);
        }
        return item;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     * @return The item that has been removed from the end.
     */
    @Override
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = (nextLast - 1 + items.length) % items.length;
        Item item = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if (size > 0 && size == items.length / 4 && items.length > ARRAY_SIZE) {
            resize(items.length / 2);
        }
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null.
     * @param index The index to get.
     * @return      The item at index in array items.
     */
    @Override
    public Item get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        int currentIndex = (nextFirst + 1 + index) % items.length;
        return items[currentIndex];
    }

    public Iterator<Item> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<Item> {
        int index;
        int count;
        public ArrayDequeIterator() {
            index = nextFirst + 1;
            if (index > items.length - 1) {
                index = 0;
            }
            int count = 0;
        }

        public boolean hasNext() {
            if (index != nextLast || count != size) {
                return true;
            }
            return false;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            // extract the item to output
            Item i = items[index];
            // prepare for the next time we call next
            moveIndex();
            // Return item we got before
            return i;
        }

        private void moveIndex() {
            if (hasNext()) {
                index += 1;
                if (index > items.length - 1) {
                    index = 0;
                }
            }
            count += 1;
        }

        public void remove() {

        }
    }
}
