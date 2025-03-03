package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<Integer>();
        BuggyAList<Integer> broken = new BuggyAList<Integer>();

        correct.addLast(5);
        correct.addLast(10);
        correct.addLast(15);

        broken.addLast(5);
        broken.addLast(10);
        broken.addLast(15);

        assertEquals(correct.size(), broken.size());

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<Integer>();
        BuggyAList<Integer> broken = new BuggyAList<Integer>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                broken.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int brokenSize = broken.size();
                assertEquals(size, brokenSize);
            }
            else if (operationNumber == 2) {
                int size = L.size();
                if (size > 0) {
                    int last = L.getLast();
                    int brokenLast = broken.getLast();
                    assertEquals(brokenLast, last);
                }
            }
            else if (operationNumber == 3) {
                int size = L.size();
                if (size > 0) {
                    int removed = L.removeLast();
                    int brokenRemoved = broken.removeLast();
                    assertEquals(brokenRemoved, removed);
                }
            }
        }
    }
}
