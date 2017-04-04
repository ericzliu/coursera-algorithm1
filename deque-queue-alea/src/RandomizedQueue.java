import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int N = 1;
    private Item[] items;
    private int ind;
    private int size;
    // construct an empty randomized queue
    public RandomizedQueue()
    {
        items = (Item[])new Object[N];
        ind = 0;
        size = 0;
    }

    // is the queue empty?
    public boolean isEmpty()
    {
        return size == 0;
    }

    // return the number of copy on the queue
    public int size() {
        return size;
    }

    private void doubleArray() {
        Item[] tmp = (Item[])new Object[items.length * 2];
        for (int i = 0; i < items.length; i += 1) {
            tmp[i] = items[i];
        }
        items = tmp;
    }
    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (ind == items.length) {
            doubleArray();
        }
        items[ind] = item;
        ind += 1;
        size += 1;
    }

    private void assertNotEmpty() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
    }

    // remove and return a random item
    public Item dequeue() {
        assertNotEmpty();
        int uniform;
        Item item;
        while (true) {
            uniform = StdRandom.uniform(ind);
            item = items[uniform];
            if (item != null) {
                break;
            }
        }
        items[uniform] = items[ind - 1];
        size -= 1;
        ind -= 1;
        if (items.length >= 4 * size) {
            shrinkArray();
        }
        return item;
    }

    private void shrinkArray() {
        if (items.length < N) {
            return;
        }
        Item[] tmp = (Item[]) new Object[items.length / 2];
        int j = 0;
        for (int i = 0; i < ind; i += 1) {
            if (items[i] != null) {
                tmp[j] = items[i];
                j += 1;
            }
        }
        items = tmp;
        ind = j;
    }

    // return (but do not remove) a random item
    public Item sample() {
        assertNotEmpty();
        Item item;
        while (true) {
            item = items[StdRandom.uniform(ind)];
            if (item != null) {
                break;
            }
        }
        return item;
    }

    private class ArrayIterator implements Iterator<Item> {

        private Item[] copy;
        private int current;

        ArrayIterator(Item[] items, int size) {
            this.copy = (Item[]) new Object[size];
            int j = 0;
            for (int i = 0; i < items.length; i += 1) {
                if (items[i] != null) {
                    copy[j] = items[i];
                    j += 1;
                }
            }
            StdRandom.shuffle(copy);
            current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < copy.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = copy[current];
            current += 1;
            return item;
        }
    }

    // return an independent iterator over copy in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator(items, size);
    }
}
