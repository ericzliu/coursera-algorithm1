import org.junit.Assert;
import org.junit.Test;

public class DequeTest {

    @Test
    public void test() {
        Deque<Integer> deck = new Deque<>();
        Assert.assertTrue(deck.isEmpty());
        deck.addFirst(1);
        Assert.assertFalse(deck.isEmpty());
        Integer first = deck.removeFirst();
        Assert.assertEquals(1, first.intValue());
        Assert.assertTrue(deck.isEmpty());
        deck.addLast(2);
        first = deck.removeFirst();
        Assert.assertEquals(2, first.intValue());
        deck.addFirst(1);
        deck.addLast(2);
        Integer a = deck.removeLast();
        Integer b = deck.removeLast();
        Assert.assertEquals(2, a.intValue());
        Assert.assertEquals(1, b.intValue());
    }
}