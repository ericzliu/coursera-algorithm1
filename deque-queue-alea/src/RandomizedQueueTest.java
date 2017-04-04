import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RandomizedQueueTest {

    @Test
    public void test_enqueue_dequeue() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        Assert.assertEquals(2, rq.size());
        rq.enqueue(3);
        rq.enqueue(4);
        Assert.assertEquals(4, rq.size());
        List<Integer> elems = new ArrayList<>();
        elems.add(rq.dequeue());
        elems.add(rq.dequeue());
        elems.add(rq.dequeue());
        elems.add(rq.dequeue());
        Assert.assertTrue(rq.isEmpty());
        elems.sort(Integer::compare);
        Assert.assertEquals(1, elems.get(0).intValue());
        Assert.assertEquals(2, elems.get(1).intValue());
        Assert.assertEquals(3, elems.get(2).intValue());
        Assert.assertEquals(4, elems.get(3).intValue());
        rq.enqueue(5);
        Assert.assertEquals(1, rq.size());
    }


    @Test
    public void test_iterator() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        List<Integer> elems = new ArrayList<>();
        Iterator<Integer> iterator = rq.iterator();
        while (iterator.hasNext()) {
            elems.add(iterator.next());
        }
        Assert.assertEquals(5, elems.size());
        Assert.assertFalse(isSorted(elems));
    }

    private boolean isSorted(List<Integer> items) {
        for (int i = 0; i < items.size() - 1; i += 1) {
            if (items.get(i) > items.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

}