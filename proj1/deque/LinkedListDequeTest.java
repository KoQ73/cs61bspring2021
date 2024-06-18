package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();
    }

    @Test
    public void addFirstTest() {
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        assertTrue("A newly initialized LLDeque should be empty", l.isEmpty());
        assertEquals(0, l.size());

        // Adding a new item with addFirst
        l.addFirst(1);
        assertEquals(1, l.size());

        // Adding a new item with addFirst
        l.addFirst(2);
        assertEquals(2, l.size());
    }

    @Test
    public void addLastTest() {
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        assertTrue("A newly initialized LLDeque should be empty", l.isEmpty());
        assertEquals(0, l.size());

        // Adding a new item with addFirst
        l.addLast(1);
        assertEquals(1, l.size());

        // Adding a new item with addFirst
        l.addLast(2);
        assertEquals(2, l.size());

        l.addLast(3);
        assertEquals(3, l.size());
    }

    @Test
    public void isEmptyTest() {
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        assertTrue("A newly initialized LLDeque should be empty", l.isEmpty());
        assertEquals(0, l.size());
        assertEquals(true, l.isEmpty());
    }

    @Test
    public void printDequeTest() {
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        assertTrue("A newly initialized LLDeque should be empty", l.isEmpty());
        l.printDeque();

        l.addFirst(1);
        l.printDeque();

        l.addLast(2);
        l.printDeque();

        l.addFirst(0);
        l.printDeque();

        l.addLast(3);
        l.printDeque();
    }

    @Test
    public void removeFirstTest() {
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", l.isEmpty());
        assertEquals(0, l.size());

        // Try to remove first item in an empty list
        l.removeFirst();

        // Add some items
        l.addFirst(1);
        l.removeFirst();
        l.printDeque();
        assertEquals(0, l.size());

        l.addFirst(1);
        l.addLast(2);
        l.removeFirst();
        l.printDeque();
        assertEquals(1, l.size());

        l.addLast(3);
        l.addFirst(1);
        l.addLast(4);
        l.removeFirst();
        l.printDeque();
        assertEquals(3, l.size());
    }

    @Test
    public void removeLastTest() {
        LinkedListDeque<Integer> l = new LinkedListDeque<Integer>();
        // should be empty
        assertTrue("lld1 should be empty upon initialization", l.isEmpty());
        assertEquals(0, l.size());

        // Try to remove first item in an empty list
        l.removeLast();

        // Add some items
        l.addFirst(1);
        l.removeLast();
        l.printDeque();
        assertEquals(0, l.size());

        l.addFirst(1);
        l.addLast(2);
        l.removeLast();
        l.printDeque();
        assertEquals(1, l.size());

        l.addLast(2);
        l.addFirst(0);
        l.addLast(3);
        l.removeLast();
        l.printDeque();
        assertEquals(3, l.size());
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    public void getTest() {
        LinkedListDeque<String> l = new LinkedListDeque<String>();
        l.addLast("a");
        l.addLast("b");
        l.addLast("c");
        l.addLast("d");

        System.out.println(l.getRecursive(0));
        System.out.println(l.getRecursive(1));
        System.out.println(l.getRecursive(2));
        System.out.println(l.getRecursive(3));
        System.out.println(l.getRecursive(4));
        System.out.println(l.getRecursive(-1));

        System.out.println(l.get(0));
        System.out.println(l.get(1));
        System.out.println(l.get(2));
        System.out.println(l.get(3));
        System.out.println(l.get(4));
        System.out.println(l.get(-1));
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, lld1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }
    }
}
