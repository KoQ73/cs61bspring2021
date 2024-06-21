package deque;

import java.util.Comparator;

public class MaxArrayDeque<Item> extends ArrayDeque<Item>{
    private Comparator<Item> c;
    public MaxArrayDeque(Comparator<Item> c) {
        this.c = c;
    }

    public Item max() {
        // If the array is empty, return null
        if (isEmpty()) {
            return null;
        }
        // Linear search the max item
        Item m = this.get(0);
        for (Item i : this) {
            if (c.compare(i, m) > 0) {
                m = i;
            }
        }
        return m;
    }

    public Item max(Comparator<Item> c) {
        if (isEmpty()) {
            return null;
        }
        Item maxItem = this.get(0);
        for (Item i : this) {
            if (c.compare(i, maxItem) > 0) {
                maxItem = i;
            }
        }
        return maxItem;
    }
}
