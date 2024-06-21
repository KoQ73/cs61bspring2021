package deque;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArrayDequeTest {
    @Test
    public void addFirstTest() {
        ArrayDeque<String> a = new ArrayDeque<String>();
        assertTrue("A newly initialized ADeque should be empty", a.isEmpty());
        assertEquals(0, a.size());

        // Adding a new item with addFirst
        a.addFirst("a");
        assertEquals(1, a.size());

        // Adding a new item with addFirst
        a.addFirst("b");
        assertEquals(2, a.size());

        // Adding a new item with addFirst
        a.addFirst("c");
        assertEquals(3, a.size());

        // Adding a new item with addFirst
        a.addFirst("d");
        assertEquals(4, a.size());

        // Adding a new item with addFirst
        a.addFirst("e");
        assertEquals(5, a.size());

        // Adding a new item with addFirst
        a.addFirst("f");
        assertEquals(6, a.size());
    }

    @Test
    public void addLastTest() {
        ArrayDeque<String> a = new ArrayDeque<String>();
        assertTrue("A newly initialized ADeque should be empty", a.isEmpty());
        assertEquals(0, a.size());

        // Adding a new item with addFirst
        a.addLast("a");
        assertEquals(1, a.size());

        // Adding a new item with addFirst
        a.addLast("b");
        assertEquals(2, a.size());

        // Adding a new item with addFirst
        a.addLast("c");
        assertEquals(3, a.size());

        // Adding a new item with addFirst
        a.addLast("d");
        assertEquals(4, a.size());

        // Adding a new item with addFirst
        a.addLast("e");
        assertEquals(5, a.size());

        // Adding a new item with addFirst
        a.addLast("f");
        assertEquals(6, a.size());
    }

    @Test
    public void printDequeTest() {
        ArrayDeque<String> a1 = new ArrayDeque<String>();

        // Print empty array
        a1.printDeque();

        // Print array that goes from the center to nextLast
        a1.addFirst("a");
        a1.addLast("b");
        a1.addLast("c");

        a1.printDeque();

        // Print a full array
        ArrayDeque<String> a2 = new ArrayDeque<String>();

        a2.addFirst("a");
        a2.addLast("b");
        a2.addFirst("C");
        a2.addLast("c");
        a2.addFirst("B");
        a2.addLast("d");
        a2.addFirst("A");
        a2.addLast("e");

        a2.printDeque();

        // Print where first is on the right side of the array
        ArrayDeque<String> a3 = new ArrayDeque<String>();

        a3.addLast("Z");
        a3.addFirst("i");
        a3.addFirst("h");
        a3.addFirst("g");
        a3.addFirst("f");
        a3.addFirst("e");
        a3.addFirst("d");
        a3.addFirst("c");

        a3.printDeque();
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque<String> a1 = new ArrayDeque<String>();

        // Removing empty array
        a1.removeFirst();
        assertEquals(0, a1.size());

        // Adding one item and removing
        a1.addFirst("a");
        a1.removeFirst();
        assertEquals(0, a1.size());

        // Case where the nextFirst is at the end of the array
        a1.addLast("b");
        a1.addLast("c");
        a1.addFirst("C");
        a1.addFirst("B");
        a1.addFirst("A");
        a1.addFirst("a");
        a1.addFirst("3");
        a1.addFirst("2");
        a1.removeFirst();
        a1.removeFirst();
    }

    @Test
    public void getTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<Integer>();

        a1.addLast(1);
        a1.addLast(2);
        a1.addLast(3);
        a1.addLast(4);
        a1.addLast(5);
        a1.addLast(6);
        a1.addLast(7);
        a1.addLast(8);

        System.out.print(a1.get(0));
        System.out.print(a1.get(1));
        System.out.print(a1.get(2));
        System.out.print(a1.get(3));
        System.out.print(a1.get(4));
        System.out.print(a1.get(5));
        System.out.print(a1.get(6));
        System.out.print(a1.get(7));
        System.out.print(a1.get(8));
        System.out.print(a1.get(-1));
    }

    @Test
    public void resizeTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<Integer>();

        a1.addLast(1);
        a1.addLast(2);
        a1.addLast(3);
        a1.addLast(4);
        a1.addLast(5);
        a1.addLast(6);
        a1.addLast(7);
        a1.addLast(8);
        a1.addLast(9);

        a1.addLast(10);
        a1.addLast(12);
        a1.addLast(13);
        a1.addLast(14);
        a1.addLast(15);
        a1.addLast(16);
        a1.addLast(17);
        a1.addLast(18);
        a1.addLast(19);
        a1.addLast(20);
        a1.addLast(21);
        a1.addLast(22);
        a1.addLast(23);
        a1.addLast(24);
        a1.addLast(25);
        a1.addLast(26);
        a1.addLast(27);
        a1.addLast(28);
        a1.addLast(29);

        a1.printDeque();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        a1.removeLast();
        System.out.println(a1.get(0));
        System.out.println(a1.get(6));
    }

    @Test
    public void iteratorTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<Integer>();

        a1.addLast(1);
        a1.addLast(2);
        a1.addLast(3);
        a1.addLast(4);
        a1.addLast(5);
        a1.addLast(6);
        a1.addLast(7);
        a1.addLast(8);
        a1.addLast(9);

        for (int i : a1) {
            System.out.println(i);
        }
    }
}
