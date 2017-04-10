import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CombinationsTest {

    @Test
    public void combinations() throws Exception {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer[]> results = new ArrayList<>();
        Combinations.combinations(integers.toArray(new Integer[0]), 0, 2, new Integer[2], results);
        assertEquals(10, results.size());
    }

    @Test
    public void combinations_simple() throws Exception {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer[]> collector = new ArrayList<>();
        Combinations.combinations(integers.toArray(new Integer[0]), 0, 5, new Integer[5], collector);
        assertEquals(1, collector.size());
    }


}