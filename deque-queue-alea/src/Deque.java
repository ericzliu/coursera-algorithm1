import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private static class Node<Item> {

        private Item item;
        private Node<Item> prev;
        private Node<Item> next;

        public Node(Item item) {
            this.item = item;
            prev = null;
            next = null;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }

        public Node<Item> getNext() {
            return next;
        }

        public void setNext(Node<Item> next) {
            this.next = next;
        }

        public Node<Item> getPrev() {
            return prev;
        }

        public void setPrev(Node<Item> prev) {
            this.prev = prev;
        }

    }

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    private void validate(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
    }
    // add the item to the front
    public void addFirst(Item item) {
        validate(item);
        Node<Item> node = new Node<>(item);
        if (last == null) {
            last = node;
        }
        else {
            node.setNext(first);
            first.setPrev(node);
        }
        first = node;
        size += 1;
    }

    // add the item to the end
    public void addLast(Item item) {
        validate(item);
        Node<Item> node = new Node<>(item);
        if (last != null) {
            last.setNext(node);
            node.setPrev(last);
        }
        else {
            first = node;
        }
        last = node;
        size += 1;
    }

    private void checkRemove() {
        if (isEmpty())
        throw new NoSuchElementException();
    }
    // remove and return the item from the front
    public Item removeFirst() {
        checkRemove();
        Item item = first.getItem();
        first = first.getNext();
        if (first == null) {
            last = null;
        }
        else {
            first.setPrev(null);
        }
        size -= 1;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        checkRemove();
        Item item = last.getItem();
        last = last.getPrev();
        if (last == null) {
            first = null;
        }
        else {
            last.setNext(null);
        }
        size -= 1;
        return item;
    }

    private class ListIterator implements Iterator<Item> {

        private Node<Item> current = null;

        ListIterator(final Node<Item> init) {
            current = init;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Item item = current.getItem();
            current = current.getNext();
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    // unit testing (optional)
    public static void main(String[] args) {
    }


}
