package deque;

public class ArrayDequeTest {
    public static void main(String[] args) {
        ArrayDeque<String> a = new ArrayDeque<String>();
        a.addLast("a");
        a.addLast("b");
        a.addFirst("c");
        a.addLast("d");
        a.addLast("e");
        a.addFirst("f");
        a.addLast("g");
        a.addLast("h");
        a.printDeque();
        System.out.println(a.get(7));
        System.out.println(a.get(0));
        System.out.println(a.get(4));
        System.out.println(a.get(8));
        a.addLast("Z");
        a.printDeque();
        System.out.println(a.get(8));
        a.removeFirst();
        System.out.println(a.get(8));
        a.printDeque();
        a.removeLast();
        a.printDeque();
    }
}
